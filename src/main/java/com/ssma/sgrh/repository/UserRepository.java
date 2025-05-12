// Define el paquete donde se encuentra esta clase
package com.ssma.sgrh.repository;

// Importa la clase Optional para manejar valores que pueden ser nulos
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ssma.sgrh.models.User;

// Marca esta interfaz como un repositorio de Spring, lo que permite inyectarla en otras partes del código
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Define un método para buscar un usuario por su nombre de usuario
    Optional<User> findByUsername(String username);

    // Define una consulta personalizada para contar el número de empleados en la tabla User
    @Query("SELECT COUNT(u) FROM User u")
    int countEmpleados();
}
