package com.ana.spring.filmes.controller;

import com.ana.spring.filmes.entities.Filme;
import com.ana.spring.filmes.entities.Usuario;
import com.ana.spring.filmes.service.FilmeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/filmes")
@CrossOrigin(origins = "*")
public class FilmeController {
    private final FilmeService filmeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void cadastrarFilme(@RequestBody Filme filmeDto){
        filmeService.cadastrarFilme(filmeDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Filme> listarFilmes(){
        return filmeService.listarFilmes();
    }
}
