package com.ana.spring.filmes.controller;

import com.ana.spring.filmes.dto.UsuarioDto;
import com.ana.spring.filmes.dto.LoginDto;
import com.ana.spring.filmes.entities.Usuario;
import com.ana.spring.filmes.service.UsuarioService;
import jakarta.validation.Valid;
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

    // 📌 CADASTRO
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void cadastrarUsuario(@Valid @RequestBody UsuarioDto dto) {
        usuarioService.cadastrarUsuario(dto);
    }

    // 📌 LISTAR
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Usuario> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    // 📌 LOGIN
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public Usuario login(@Valid @RequestBody LoginDto dto) {
        return usuarioService.login(dto.email(), dto.senha());
    }
}