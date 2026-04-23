package com.ana.spring.filmes.service;

import com.ana.spring.filmes.dto.UsuarioDto;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.ana.spring.filmes.entities.Usuario;
import com.ana.spring.filmes.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public void cadastrarUsuario(UsuarioDto dto){

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        Usuario usuario = Usuario.builder()
                .nome(dto.nome())
                .email(dto.email())
                .senha(encoder.encode(dto.senha()))
                .build();

        usuarioRepository.save(usuario);
    }

    public Usuario login(String email, String senha){

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        if(!encoder.matches(senha, usuario.getSenha())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Senha inválida");
        }

        return usuario;
    }

    public List<Usuario> listarUsuarios(){
        return usuarioRepository.findAll();
    }
}
