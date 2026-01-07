package com.pedroscheurer.investress.api.repository;

import com.pedroscheurer.investress.api.dtos.InvestimentoQueryTipo;
import com.pedroscheurer.investress.api.entities.InvestimentoEntity;
import com.pedroscheurer.investress.api.entities.TypeInvestimento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvestimentoRepository extends JpaRepository<InvestimentoEntity, Long> {

    Page<InvestimentoEntity> findByNomeContainsIgnoreCase(String nome, Pageable pageable);

    List<InvestimentoEntity> findByType(TypeInvestimento typeInvestimento);

    @Query("""
            SELECT new com.pedroscheurer.investress.api.dtos.InvestimentoQueryTipo(
                    i.type, COUNT(i), SUM(i.valorAtual), SUM(i.valorInvestido))
            from InvestimentoEntity i GROUP BY i.type
            """)
    List<InvestimentoQueryTipo> findAgrupadoByType();
}
