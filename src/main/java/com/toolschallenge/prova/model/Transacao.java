package com.toolschallenge.prova.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transacao")
public class Transacao {
    @Id
    @Column(unique = true,nullable = false)
    @NotNull(message = "id não pode ser nulo")
    private Long id;

    @Column(nullable = false)
    private String cartao;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "descricao_id")
    private Descricao descricao;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "forma_pagamento_id")
    private FormaPagamento formaPagamento;

}