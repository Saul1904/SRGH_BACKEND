package com.ssma.sgrh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository; // ✅ Asegura que este

import com.ssma.sgrh.models.Ausencia;

@Repository
public interface AusenciaRepository extends JpaRepository<Ausencia, Long> {
    
    // ✅ Asegura que las ausencias vengan con el empleado correctamente relacionado
    @Query("SELECT a FROM Ausencia a LEFT JOIN FETCH a.empleado WHERE a.empleado.id = :empleadoId")
    List<Ausencia> findByEmpleado_Id(Long empleadoId);

    // ✅ Obtener todas las ausencias con sus empleados ya cargados
    @Query("SELECT a FROM Ausencia a LEFT JOIN FETCH a.empleado")
    List<Ausencia> findAllWithEmpleado();
    
    @Query("SELECT COUNT(a) FROM Ausencia a")
    int countAusencias();


    
}

