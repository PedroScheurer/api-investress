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

    public UserEntity save(UserEntity user) throws Exception {
        if (user == null) {
            throw new IllegalArgumentException("User não pode ser nulo");
        }

        if (user.getNome() == null || user.getNome().isEmpty()) {
            throw new IllegalArgumentException("Nome inválido");
        }
        user.setNome(user.getNome().trim());

        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email inválido");
        }
        user.setEmail(user.getEmail().trim());

        if (user.getPassword() == null || user.getPassword().isEmpty()
                || user.getPassword().length() < 6) {
            throw new IllegalArgumentException("Password inválido");
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
}
