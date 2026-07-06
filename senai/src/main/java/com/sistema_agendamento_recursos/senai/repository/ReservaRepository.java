package com.sistema_agendamento_recursos.senai.repository;

import com.sistema_agendamento_recursos.senai.entities.RecursoEntity;
import com.sistema_agendamento_recursos.senai.entities.ReservaEntity;
import com.sistema_agendamento_recursos.senai.entities.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ReservaRepository extends JpaRepository<ReservaEntity , Long> {

//   boolean existyByColaborador (UsuarioEntity usuario);

  // boolean existyByRecursos (RecursoEntity recurso);

}
