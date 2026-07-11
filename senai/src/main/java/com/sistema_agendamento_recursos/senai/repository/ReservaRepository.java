package com.sistema_agendamento_recursos.senai.repository;

import com.sistema_agendamento_recursos.senai.entities.ReservaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<ReservaEntity, Long> {

    boolean existsByUsuarioIdAndDataCancelamentoIsNull(Long usuarioId);

    boolean existsByRecursoIdAndDataCancelamentoIsNull(Long recursoId);

    List<ReservaEntity> findByDataCancelamentoIsNotNull();

    @Transactional
    @Modifying
    void deleteByUsuarioIdAndDataCancelamentoIsNotNull(Long usuarioId);

    @Transactional
    @Modifying
    void deleteByRecursoIdAndDataCancelamentoIsNotNull(Long recursoId);
}