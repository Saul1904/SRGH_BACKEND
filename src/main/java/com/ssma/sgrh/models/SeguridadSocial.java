package com.ssma.sgrh.models;
// Define el paquete donde se encuentra esta clase, útil para organizar el código.

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
// Importa las anotaciones de Lombok para generar automáticamente constructores, getters y setters.

@Entity
// Marca esta clase como una entidad JPA, lo que significa que se mapeará a una tabla en la base de datos.
@Table(name = "Seguridad_Social")
// Especifica el nombre de la tabla en la base de datos que corresponde a esta entidad.
@Getter
@Setter
// Genera automáticamente los métodos getter y setter para todos los campos de la clase.
@NoArgsConstructor
// Genera un constructor sin argumentos.
@AllArgsConstructor
// Genera un constructor con todos los argumentos.
public class SeguridadSocial {

    @Id
    // Marca este campo como la clave primaria de la tabla.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Especifica que el valor de la clave primaria se generará automáticamente por la base de datos.
    private Long id;
    // Campo que representa el identificador único de la entidad.

    @OneToOne
    // Define una relación uno a uno con otra entidad.
    @JoinColumn(name = "empleado_id", nullable = false)
    // Especifica la columna en la tabla que actúa como clave foránea para la relación con la entidad Empleado.
    private Empleado empleado;
    // Campo que representa la relación con la entidad Empleado.

    @Column(unique = true)
    // Especifica que este campo debe ser único en la tabla.
    private String numeroSeguro;
    // Campo que almacena el número de seguro social.

    private String tipoSeguro;
    // Campo que almacena el tipo de seguro.

    private LocalDate fechaRegistro = LocalDate.now();
    // Campo que almacena la fecha de registro, inicializado con la fecha actual.

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }
    // Método getter para el campo fechaRegistro.

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    // Método setter para el campo fechaRegistro.

    private String estado;
    // Campo que almacena el estado del seguro social.

    public String getEstado() {
        return estado;
    }
    // Método getter para el campo estado.

    public void setEstado(String estado) {
        this.estado = estado;
    }
    // Método setter para el campo estado.
}
