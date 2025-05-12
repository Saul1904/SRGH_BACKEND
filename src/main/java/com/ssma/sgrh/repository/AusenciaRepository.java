package com.ssma.sgrh.repository;
// Define el paquete donde se encuentra esta clase, útil para organizar el código.

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository; // ✅ Asegura que este

import com.ssma.sgrh.models.Ausencia;
// Importa la clase Ausencia, que representa la entidad gestionada por este repositorio.

@Repository
// Marca esta interfaz como un repositorio de Spring, lo que permite la inyección de dependencias.

public interface AusenciaRepository extends JpaRepository<Ausencia, Long> {
// Define la interfaz del repositorio para la entidad Ausencia, heredando métodos CRUD de JpaRepository.
// El primer parámetro es la entidad (Ausencia) y el segundo es el tipo de su clave primaria (Long).

    // ✅ Asegura que las ausencias vengan con el empleado correctamente relacionado
    @Query("SELECT a FROM Ausencia a LEFT JOIN FETCH a.empleado WHERE a.empleado.id = :empleadoId")
    // Define una consulta personalizada que obtiene las ausencias de un empleado específico,
    // cargando también los datos del empleado relacionado.
    List<Ausencia> findByEmpleadoId(Long empleadoId);
    // Método que ejecuta la consulta anterior, recibiendo el ID del empleado como parámetro.

    // ✅ Obtener todas las ausencias con sus empleados ya cargados
    @Query("SELECT a FROM Ausencia a LEFT JOIN FETCH a.empleado")
    // Define una consulta personalizada que obtiene todas las ausencias,
    // cargando también los datos de los empleados relacionados.
    List<Ausencia> findAllWithEmpleado();
    // Método que ejecuta la consulta anterior y devuelve la lista de ausencias con empleados.

    @Query("SELECT COUNT(a) FROM Ausencia a")
    // Define una consulta personalizada que cuenta el número total de ausencias en la base de datos.
    int countAusencias();
    // Método que ejecuta la consulta anterior y devuelve el conteo de ausencias.

}
