package com.ssma.sgrh.repository;
// Define el paquete donde se encuentra esta clase, útil para organizar el código.

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ssma.sgrh.models.Turno;
// Importa la clase Turno, que representa la entidad que se gestionará en este repositorio.

@Repository
// Marca esta interfaz como un repositorio de Spring, lo que permite que Spring la detecte y gestione automáticamente.

public interface TurnoRepository extends JpaRepository<Turno, Long> {
// Define una interfaz que extiende JpaRepository, proporcionando métodos básicos para gestionar la entidad Turno.
// El primer parámetro (Turno) es la entidad, y el segundo (Long) es el tipo de la clave primaria.

    // ✅ Obtener turnos con empleados correctamente relacionados
    @Query("SELECT t FROM Turno t LEFT JOIN FETCH t.empleado WHERE t.empleado.id = :empleadoId")
    // Define una consulta personalizada para obtener turnos relacionados con un empleado específico.
    // Utiliza LEFT JOIN FETCH para cargar los datos del empleado junto con los turnos.
    List<Turno> findByEmpleado_Id(Long empleadoId);
    // Declara un método que devuelve una lista de turnos asociados a un empleado según su ID.

    // ✅ Obtener todos los turnos con empleados ya cargados
    @Query("SELECT t FROM Turno t LEFT JOIN FETCH t.empleado")
    // Define una consulta personalizada para obtener todos los turnos con los datos de los empleados ya cargados.
    List<Turno> findAllWithEmpleado();
    // Declara un método que devuelve una lista de todos los turnos con los empleados relacionados.
}
