// Define el paquete donde se encuentra esta clase, útil para organizar el código en módulos.
package com.ssma.sgrh.repository;

// Importa la interfaz JpaRepository de Spring Data JPA, que proporciona métodos para operaciones CRUD.
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssma.sgrh.models.Ausencia;
import com.ssma.sgrh.models.Empleado;
import com.ssma.sgrh.models.Nomina;

// Define la clase DashboardRepository, que contiene interfaces para manejar repositorios.
public class DashboardRepository {

    // Define una interfaz para manejar operaciones CRUD sobre la entidad Empleado.
    @Repository // Marca esta interfaz como un componente de acceso a datos.
    public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {}

    // Define una interfaz para manejar operaciones CRUD sobre la entidad Ausencia.
    @Repository // Marca esta interfaz como un componente de acceso a datos.
    public interface AusenciaRepository extends JpaRepository<Ausencia, Integer> {}

    // Define una interfaz para manejar operaciones CRUD sobre la entidad Nomina.
    @Repository // Marca esta interfaz como un componente de acceso a datos.
    public interface NominaRepository extends JpaRepository<Nomina, Integer> {} // Si ya manejas nóminas
}
