package com.sistema_agendamento_recursos.senai.repository;

import com.sistema_agendamento_recursos.senai.entities.RecursoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecursoRepository extends JpaRepository<RecursoEntity, Long> {

    Optional<RecursoEntity> findBy();

}
