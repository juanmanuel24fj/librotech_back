package org.example.repository;

import org.example.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findAllByFechaDevolucionBefore(LocalDate fecha);
    List<Reserva> findAllByUsuarioId(Long usuarioId);
    boolean existsByUsuarioId(Long usuarioId);


}
