package io.github.devcarlosgti.sbootexp_security.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String login;
    private String senha;
    private String nome;

    @Transient //p ignorar mapeamento de jpa ou seja cria no db
    private List<String> permissoes;
}
