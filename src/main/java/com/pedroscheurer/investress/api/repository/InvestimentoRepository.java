package com.pedroscheurer.investress.api.repository;

import com.pedroscheurer.investress.api.entities.InvestimentoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvestimentoRepository extends JpaRepository<InvestimentoEntity, Long> {

    Page<InvestimentoEntity> findByNomeContainsIgnoreCase(String nome, Pageable pageable);
}
