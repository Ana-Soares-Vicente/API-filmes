package com.ana.spring.filmes.service;

import com.ana.spring.filmes.entities.Filme;
import com.ana.spring.filmes.entities.Usuario;
import com.ana.spring.filmes.repository.FilmeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmeService {
    private final FilmeRepository filmeRepository;

    public void cadastrarFilme( Filme filmeDto){
        Filme filme = Filme.builder()
                .titulo(filmeDto.getTitulo())
                .build();

        filmeRepository.save(filme);
    }

    public List<Filme> listarFilmes(){
        return filmeRepository.findAll();
    }
}
