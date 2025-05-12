// Declara el paquete donde reside esta interfaz de repositorio
package com.ssma.sgrh.repository;

// Importa la interfaz List del framework de colecciones de Java
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ssma.sgrh.models.Nomina;

// Marca esta interfaz como un repositorio de Spring Data
@Repository
public interface NominaRepository extends JpaRepository<Nomina, Long> {
    // Declara un método para encontrar todos los registros de Nomina asociados con un empleado específico
    List<Nomina> findByEmpleadoId(Long empleadoId);

    // Declara una consulta personalizada para contar el número total de registros de Nomina en la base de datos
    @Query("SELECT COUNT(n) FROM Nomina n")
    int countNominas();
}