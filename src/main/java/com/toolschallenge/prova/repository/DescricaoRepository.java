package com.toolschallenge.prova.repository;

import com.toolschallenge.prova.model.Descricao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DescricaoRepository extends JpaRepository<Descricao, UUID> {
}
