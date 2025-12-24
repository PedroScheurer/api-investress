package com.pedroscheurer.investress.api.repository;

import com.pedroscheurer.investress.api.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByEmail(String email);

    boolean existsByEmailAndNome(String email, String nome);

    Optional<UserEntity> findByEmail(String email);
}
