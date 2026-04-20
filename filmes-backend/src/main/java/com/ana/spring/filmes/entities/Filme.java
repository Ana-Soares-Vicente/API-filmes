package com.ana.spring.filmes.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "filmes")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder

public class Filme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String titulo;
}
