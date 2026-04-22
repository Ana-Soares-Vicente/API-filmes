package com.ana.spring.filmes.controller;

import com.ana.spring.filmes.dto.AvaliacaoDto;
import com.ana.spring.filmes.entities.Avaliacao;
import com.ana.spring.filmes.service.AvaliacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avaliacoes")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor

    public class AvaliacaoController {

        private final AvaliacaoService service;

    @PostMapping
    public Avaliacao avaliar(@RequestBody @Valid AvaliacaoDto dto) {
        return service.avaliar(dto.usuarioId(), dto.filmeId(), dto.nota());
    }

        @GetMapping("/media/{filmeId}")
        public Double media (@PathVariable Integer filmeId) {
            return service.calcularMedia(filmeId);
        }

    @GetMapping
    public List<Avaliacao> listar() {
        return service.listarTodas();
    }

    @GetMapping("/filme/{filmeId}")
    public List<Avaliacao> listarPorFilme(@PathVariable Integer filmeId) {
        return service.listarPorFilme(filmeId);
    }

    @GetMapping("/{id}")
    public Avaliacao buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        service.deletar(id);
    }
    
    @PutMapping("/{id}")
    public Avaliacao atualizar(
            @PathVariable Integer id,
            @RequestParam Integer nota
    ) {
        return service.atualizar(id, nota);
    }
    }