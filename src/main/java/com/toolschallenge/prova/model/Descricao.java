package com.toolschallenge.prova.model;

import com.toolschallenge.prova.model.enuns.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "descricao")
public class Descricao {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    private BigDecimal valor;

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataHora;

    private String estabelecimento;

    private String nsu;

    @Column(name = "codigo_autorizacao")
    private String codAutorizacao;

    @Enumerated(EnumType.STRING)
    private Status status;

}