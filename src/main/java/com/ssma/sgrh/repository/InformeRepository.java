package com.ssma.sgrh.repository;
// Define el paquete donde se encuentra esta clase, lo que ayuda a organizar el código.

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssma.sgrh.models.Informe;
// Importa la clase Informe, que representa la entidad que esta interfaz gestionará.

@Repository
// Marca esta interfaz como un repositorio de Spring, lo que permite que Spring la detecte y gestione automáticamente.

public interface InformeRepository extends JpaRepository<Informe, Long> {
// Declara una interfaz que extiende JpaRepository, proporcionando métodos CRUD para la entidad Informe.
// El primer parámetro (Informe) es la entidad que se gestionará, y el segundo (Long) es el tipo de su clave primaria.

  Informe findTopByOrderByFechaGeneracionDesc();
  // Declara un método que devuelve el último Informe basado en el campo "fechaGeneracion" en orden descendente.

  @Query("SELECT COUNT(i) FROM Informe i WHERE i.tipoInforme = :tipo")
  // Define una consulta personalizada en JPQL que cuenta el número de informes con un tipo específico.

  Long contarPorTipo(@Param("tipo") String tipo);
  // Declara un método que ejecuta la consulta personalizada anterior, tomando como parámetro el tipo de informe.
}
