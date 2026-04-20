package com.ana.spring.filmes.repository;

import com.ana.spring.filmes.entities.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import java.util.Optional;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Integer> {
    List<Avaliacao> findByFilmeId(Integer filmeId);
    Optional<Avaliacao> findByUsuarioIdAndFilmeId(Integer usuarioId, Integer filmeId);

}
