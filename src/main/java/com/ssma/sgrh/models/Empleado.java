package com.ssma.sgrh.models;
// Define el paquete donde se encuentra la clase, útil para organizar el código.

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
// Genera automáticamente los métodos setter para todos los campos.

@Entity
// Indica que esta clase es una entidad JPA que se mapeará a una tabla en la base de datos.

@Table(name = "Empleados")
// Especifica que esta entidad se mapeará a la tabla llamada "Empleados" en la base de datos.

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// Estas anotaciones de Lombok generan automáticamente los métodos getter, setter, un constructor sin argumentos y un constructor con todos los argumentos.

public class Empleado {
// Define la clase `Empleado`, que representa una entidad en la base de datos.

    @Id
    // Marca el campo `id` como la clave primaria de la entidad.

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Especifica que el valor de `id` será generado automáticamente por la base de datos usando una estrategia de identidad.

    private Long id;
    // Campo que representa el identificador único del empleado.

    private String nombre;
    // Campo que almacena el nombre del empleado.

    private String apellido;
    // Campo que almacena el apellido del empleado.

    private String email;
    // Campo que almacena el correo electrónico del empleado.

    private String telefono;
    // Campo que almacena el número de teléfono del empleado.

    private Double sueldo;
    // Campo que almacena el sueldo del empleado.
    @Column(name= "foto")
    // Personaliza el nombre de la columna en la base de datos para este campo.
    private String foto;
    // Campo que almacena la URL o ruta de la foto del empleado.

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    // Especifica el formato de fecha para serialización/deserialización JSON.

    @Column(name = "fecha_contratacion")
    // Personaliza el nombre de la columna en la base de datos para este campo.

    private java.time.LocalDate fechaContratacion;
    // Campo que almacena la fecha de contratación del empleado.

    private String departamento;
    // Campo que almacena el departamento al que pertenece el empleado.
}
