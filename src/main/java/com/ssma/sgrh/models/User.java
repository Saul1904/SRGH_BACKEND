package com.ssma.sgrh.models;
// Define el paquete donde se encuentra esta clase, útil para organizar el código.

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
// Importa las anotaciones de JPA necesarias para mapear esta clase como una entidad de base de datos.

@Entity
// Marca esta clase como una entidad JPA, lo que significa que se mapeará a una tabla en la base de datos.
@Table(name = "users")
// Especifica el nombre de la tabla en la base de datos a la que se mapeará esta entidad.

public class User {
    @Id
    // Indica que este campo es la clave primaria de la tabla.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Especifica que el valor de este campo será generado automáticamente por la base de datos (auto-incremental).
    private Long id;
    // Representa el identificador único del usuario.

    private String username;
    // Almacena el nombre de usuario del usuario.

    private String password;
    // Almacena la contraseña del usuario.

    // Getter y Setter para 'id'
    public Long getId() {
        return id;
    }
    // Devuelve el valor del identificador único del usuario.

    public void setId(Long id) {
        this.id = id;
    }
    // Establece el valor del identificador único del usuario.

    // Getter y Setter para 'username'
    public String getUsername() {
        return username;
    }
    // Devuelve el nombre de usuario.

    public void setUsername(String username) {
        this.username = username;
    }
    // Establece el nombre de usuario.

    // Getter y Setter para 'password'
    public String getPassword() {
        return password;
    }
    // Devuelve la contraseña del usuario.

    public void setPassword(String password) {
        this.password = password;
    }
    // Establece la contraseña del usuario.
}
