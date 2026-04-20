package com.ana.spring.filmes.service;

import com.ana.spring.filmes.dto.UsuarioDto;
import com.ana.spring.filmes.entities.Usuario;
import com.ana.spring.filmes.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public void cadastrarUsuario(UsuarioDto dto){
        Usuario usuario = Usuario.builder()
                .nome(dto.nome())
                .build();

        usuarioRepository.save(usuario);
    }

    public List<Usuario> listarUsuarios(){
        return usuarioRepository.findAll();
    }
}
