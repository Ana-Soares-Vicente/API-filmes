package com.ana.spring.filmes.repository;

import com.ana.spring.filmes.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}
