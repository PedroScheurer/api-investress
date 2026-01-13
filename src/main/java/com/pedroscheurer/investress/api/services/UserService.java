package com.pedroscheurer.investress.api.services;

import com.pedroscheurer.investress.api.entities.UserEntity;
import com.pedroscheurer.investress.api.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder encoder, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.encoder = encoder;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity save(UserEntity user) throws IllegalArgumentException{

        if (user.getPassword() != null && !validarSenha(user.getPassword())) {
            throw new IllegalArgumentException("Password inválido, deve contar 6 digitos, uma letra maiuscula e uma minuscula");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (repository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Usuário já cadastrado");
        }

        return repository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com este email"));
    }

    public boolean validarSenha(String senha) throws IllegalArgumentException{
        return senha.matches("^(?=.*[a-z])(?=.*[A-Z]).{6,}$");
    }
}
