package com.pedroscheurer.investress.api.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_investimentos")
public class InvestimentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String nome;

    @Column(name = "valor_investido", nullable = false)
    private BigDecimal valorInvestido;

    @Column(name = "valor_atual", nullable = false)
    private BigDecimal valorAtual;

    @Column(name = "retorno_investimento", nullable = false)
    private BigDecimal retornoInvestimento;

    @Enumerated(EnumType.STRING)
    private TypeInvestimento type;

    @JoinColumn
    @ManyToOne
    private UserEntity user;

    public BigDecimal getRetornoInvestimento() {
        return retornoInvestimento;
    }

    public void setRetornoInvestimento(BigDecimal retornoInvestimento) {
        this.retornoInvestimento = retornoInvestimento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TypeInvestimento getType() {
        return type;
    }

    public void setType(TypeInvestimento type) {
        this.type = type;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public BigDecimal getValorAtual() {
        return valorAtual;
    }

    public void setValorAtual(BigDecimal valorAtual) {
        this.valorAtual = valorAtual;
    }

    public BigDecimal getValorInvestido() {
        return valorInvestido;
    }

    public void setValorInvestido(BigDecimal valorInvestido) {
        this.valorInvestido = valorInvestido;
    }
}
