package com.ana.spring.filmes.repository;

import com.ana.spring.filmes.entities.Filme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmeRepository extends JpaRepository<Filme, Integer> {
}
