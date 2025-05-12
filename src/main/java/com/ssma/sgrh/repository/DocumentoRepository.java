// Declara el paquete donde se encuentra esta clase
package com.ssma.sgrh.repository;

// Importa la interfaz List del framework de colecciones de Java
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssma.sgrh.models.Documento;

// Marca esta interfaz como un repositorio de Spring Data, permitiendo que Spring la detecte y la gestione
@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {
    // Declara un método de consulta personalizado para encontrar todas las entidades Documento por el campo empleadoId
    List<Documento> findByEmpleadoId(Long empleadoId);
}