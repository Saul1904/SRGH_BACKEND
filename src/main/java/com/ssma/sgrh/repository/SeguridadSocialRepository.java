// Define el paquete donde se encuentra esta clase
package com.ssma.sgrh.repository;

// Importa la interfaz JpaRepository de Spring Data JPA, que proporciona operaciones CRUD para la entidad
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssma.sgrh.models.SeguridadSocial;

// Anota la interfaz como un repositorio de Spring Data, haciéndola candidata para el escaneo de componentes de Spring
@Repository
// Define una interfaz que extiende JpaRepository, proporcionando operaciones CRUD para la entidad SeguridadSocial
// El primer parámetro genérico es el tipo de la entidad (SeguridadSocial), y el segundo es el tipo de su clave primaria (Long)
public interface SeguridadSocialRepository extends JpaRepository<SeguridadSocial, Long> {
}
