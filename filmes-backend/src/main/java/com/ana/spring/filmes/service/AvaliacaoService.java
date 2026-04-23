package com.ana.spring.filmes.service;

import com.ana.spring.filmes.entities.Avaliacao;
import com.ana.spring.filmes.entities.Usuario;
import com.ana.spring.filmes.entities.Filme;
import com.ana.spring.filmes.repository.AvaliacaoRepository;
import com.ana.spring.filmes.repository.UsuarioRepository;
import com.ana.spring.filmes.repository.FilmeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final FilmeRepository filmeRepository;

    public Double calcularMedia(Integer filmeId) {

        List<Avaliacao> avaliacoes = avaliacaoRepository.findByFilmeId(filmeId);

        if (avaliacoes.isEmpty()) {
            return 0.0;
        }

        return avaliacoes.stream()
                .mapToInt(Avaliacao::getNota)
                .average()
                .orElse(0.0);
    }

    public List<Avaliacao> listarPorFilme(Integer filmeId) {
        return avaliacaoRepository.findByFilmeId(filmeId);
    }

    public Avaliacao avaliar(Integer usuarioId, Integer filmeId, Integer nota) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Filme filme = filmeRepository.findById(filmeId)
                .orElseThrow(() -> new RuntimeException("Filme não encontrado"));

        //  verifica se já existe avaliação
        Avaliacao avaliacaoExistente = avaliacaoRepository
                .findByUsuarioIdAndFilmeId(usuarioId, filmeId)
                .orElse(null);

        if (avaliacaoExistente != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Você já avaliou esse filme");        }

        //  cria nova avaliação
        Avaliacao nova = Avaliacao.builder()
                .usuario(usuario)
                .filme(filme)
                .nota(nota)
                .build();

        return avaliacaoRepository.save(nova);
    }

    public Avaliacao buscarPorId(Integer id) {
        return avaliacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Avaliação não encontrada"));

    }

    public Avaliacao atualizar(Integer id, Integer novaNota) {

        Avaliacao avaliacao = avaliacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Avaliação não encontrada"));

        avaliacao.setNota(novaNota);

        return avaliacaoRepository.save(avaliacao);
    }
    public void deletar(Integer id) {

        Avaliacao avaliacao = avaliacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Avaliação não encontrada"));

        avaliacaoRepository.delete(avaliacao);
    }

    public List<Avaliacao> listarTodas(){
        return avaliacaoRepository.findAll();
    }

}