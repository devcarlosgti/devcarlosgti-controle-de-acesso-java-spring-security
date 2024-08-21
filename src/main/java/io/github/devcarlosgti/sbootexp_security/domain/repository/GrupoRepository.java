package io.github.devcarlosgti.sbootexp_security.domain.repository;

import io.github.devcarlosgti.sbootexp_security.domain.entity.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GrupoRepository extends JpaRepository<Grupo, String> {

    //criar consulta
    Optional<Grupo> findByNome(String nome);
}
