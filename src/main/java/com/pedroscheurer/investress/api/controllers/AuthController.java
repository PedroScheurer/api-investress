package com.pedroscheurer.investress.api.controllers;

import com.pedroscheurer.investress.api.dtos.request.LoginDTO;
import com.pedroscheurer.investress.api.dtos.request.RegisterDTO;
import com.pedroscheurer.investress.api.dtos.response.UserResponseDTO;
import com.pedroscheurer.investress.api.entities.TypeUser;
import com.pedroscheurer.investress.api.entities.UserEntity;
import com.pedroscheurer.investress.api.services.UserService;
import com.pedroscheurer.investress.api.utils.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService service;
    private final AuthenticationConfiguration authConfig;
    private final JwtUtil jwtUtil;

    public AuthController(UserService service, AuthenticationConfiguration authConfig, JwtUtil jwtUtil) {
        this.service = service;
        this.authConfig = authConfig;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody RegisterDTO dto) throws Exception {
        UserEntity user = new UserEntity();
        BeanUtils.copyProperties(dto, user);
        user.setType(TypeUser.Common);

        service.save(user);

        return ResponseEntity.status(201).body(new UserResponseDTO(
                user.getId(),
                user.getNome(),
                user.getEmail(),
                user.getType()));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDTO dto) {
        authConfig
                .getAuthenticationManager()
                .authenticate(new UsernamePasswordAuthenticationToken(dto.email(), dto.password()));
        String jwt = jwtUtil.generateToken(dto.email());
        return ResponseEntity.ok(jwt);
    }
}
