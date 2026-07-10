package com.sistema_agendamento_recursos.senai.repository;

import com.sistema_agendamento_recursos.senai.entities.ReservaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepository extends JpaRepository<ReservaEntity, Long> {

    boolean existsByUsuarioId(Long usuarioId);

    boolean existsByRecursoId(Long recursoId);
}