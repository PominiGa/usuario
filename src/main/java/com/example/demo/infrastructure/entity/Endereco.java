package com.example.demo.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "endereco")
@Builder
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rua")
    private String rua;

    @Column(name = "numero")
    private int numero;

    @Column(name = "Complemento", length = 10)
    private String complemento;

    @Column(name = "cidade")
    private String cidade;

    @Column(name = "estado_sigla", length = 2)
    private String estado;

    @Column(name = "cep", length = 9)
    private String cep;
}