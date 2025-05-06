package com.ssma.sgrh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ssma.sgrh.models.Turno;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Long> {

    // ✅ Obtener turnos con empleados correctamente relacionados
    @Query("SELECT t FROM Turno t LEFT JOIN FETCH t.empleado WHERE t.empleado.id = :empleadoId")
    List<Turno> findByEmpleado_Id(Long empleadoId);

    // ✅ Obtener todos los turnos con empleados ya cargados
    @Query("SELECT t FROM Turno t LEFT JOIN FETCH t.empleado")
    List<Turno> findAllWithEmpleado();
}
