package com.pedroscheurer.investress.api.services;

import com.pedroscheurer.investress.api.dtos.response.InvestimentoQueryTipo;
import com.pedroscheurer.investress.api.entities.InvestimentoEntity;
import com.pedroscheurer.investress.api.entities.TypeInvestimento;
import com.pedroscheurer.investress.api.entities.UserEntity;
import com.pedroscheurer.investress.api.repository.InvestimentoRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class InvestimentoService {

    private final InvestimentoRepository repository;

    public InvestimentoService(InvestimentoRepository repository) {
        this.repository = repository;
    }


    @Transactional
    public InvestimentoEntity save(InvestimentoEntity investimento) {
        UserEntity user = (UserEntity) Objects.
                requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal();

        investimento.setUser(user);
        BigDecimal retornoInvestimento = investimento.getValorAtual().subtract(investimento.getValorInvestido());
        investimento.setRetornoInvestimento(retornoInvestimento);

        return repository.save(investimento);
    }

    public Page<InvestimentoEntity> listarTodos(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 15);
        return repository.findAll(pageable);
    }

    public Optional<InvestimentoEntity> listarPorId(Long id){

        return repository.findById(id);
    }

    public Page<InvestimentoEntity> listarPorNome(int pageNumber, String nome) {
        Pageable pageable = PageRequest.of(pageNumber, 15);
        return repository.findByNomeContainsIgnoreCase(nome, pageable);
    }

    public List<InvestimentoQueryTipo> listarAgrupadoPorTipo(){
        return repository.findAgrupadoByType();
    }

    public List<InvestimentoEntity> listarPorTipo(TypeInvestimento typeInvestimento){
        UserEntity user = (UserEntity) Objects.
                requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal();

        return repository.findByTypeAndUser(typeInvestimento, user);
    }

    public void excluirInvestimento(Long id){
        repository.deleteById(id);
    }


}
