// Define el paquete donde se encuentra esta clase
package com.ssma.sgrh.repository;

// Importa la interfaz JpaRepository de Spring Data JPA, que proporciona operaciones CRUD
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssma.sgrh.models.Empleado;

// Anota la interfaz como un repositorio de Spring, haciéndola candidata para el escaneo de componentes de Spring
@Repository
// Define la interfaz EmpleadoRepository, que extiende JpaRepository para proporcionar operaciones CRUD
// JpaRepository<Empleado, Long> significa que gestiona entidades de tipo Empleado con una clave primaria de tipo Long
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
}
