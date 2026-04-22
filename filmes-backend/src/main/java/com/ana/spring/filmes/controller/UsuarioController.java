package com.ana.spring.filmes.controller;

import com.ana.spring.filmes.dto.UsuarioDto;
import com.ana.spring.filmes.entities.Usuario;
import com.ana.spring.filmes.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor

public class UsuarioController {
    private final UsuarioService usuarioService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void cadastrarUsuario(@RequestBody UsuarioDto dto){
        usuarioService.cadastrarUsuario(dto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Usuario> listarUsuarios(){
        return usuarioService.listarUsuarios();
    }
}
