package com.ssma.sgrh.models;
// Define el paquete donde se encuentra la clase Turno.

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
// Importa las anotaciones necesarias para la persistencia con JPA y las utilidades de Lombok.

@Entity
// Marca esta clase como una entidad JPA que se mapeará a una tabla en la base de datos.
@Table(name = "Turnos")
// Especifica el nombre de la tabla en la base de datos asociada a esta entidad.
@Getter
@Setter
// Genera automáticamente los métodos getter y setter para todos los campos de la clase.
@NoArgsConstructor
// Genera un constructor sin argumentos.
@AllArgsConstructor
// Genera un constructor con argumentos para todos los campos de la clase.
public class Turno {

    @Id
    // Marca este campo como la clave primaria de la entidad.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Indica que el valor de la clave primaria será generado automáticamente por la base de datos.
    private Long id;
    // Campo que representa el identificador único del turno.

    @ManyToOne
    // Define una relación de muchos a uno con otra entidad (Empleado).
    @JoinColumn(name = "empleado_id", nullable = false)
    // Especifica la columna en la base de datos que almacena la relación con la entidad Empleado.
    private Empleado empleado;
    // Campo que representa el empleado asociado al turno.

    @Column(name = "fecha", nullable = false)
    // Especifica la columna en la base de datos para almacenar la fecha del turno y que no puede ser nula.
    private java.time.LocalDate fecha;
    // Campo que representa la fecha del turno.

    @Column(name = "horario_inicio", nullable = false)
    // Especifica la columna en la base de datos para almacenar el horario de inicio del turno y que no puede ser nula.
    private java.time.LocalTime horarioInicio;
    // Campo que representa la hora de inicio del turno.

    @Column(name = "horario_fin", nullable = false)
    // Especifica la columna en la base de datos para almacenar el horario de fin del turno y que no puede ser nula.
    private java.time.LocalTime horarioFin;
    // Campo que representa la hora de fin del turno.
}
