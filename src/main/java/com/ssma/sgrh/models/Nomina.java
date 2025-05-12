package com.ssma.sgrh.models;
// Define el paquete donde se encuentra esta clase.

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
// Indica que esta clase es una entidad JPA que se mapeará a una tabla en la base de datos.
@Table(name = "Nominas")
// Especifica el nombre de la tabla en la base de datos asociada a esta entidad.
@Getter
@Setter
// Genera automáticamente los métodos getter y setter para todos los campos de la clase.
@NoArgsConstructor
// Genera un constructor sin argumentos.
@AllArgsConstructor
// Genera un constructor con todos los argumentos de la clase.
public class Nomina {

    @Id
    // Marca este campo como la clave primaria de la entidad.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Especifica que el valor de este campo será generado automáticamente por la base de datos.
    private Long id;
    // Identificador único de la nómina.

    @ManyToOne
    // Define una relación de muchos a uno con otra entidad.
    @JoinColumn(name = "empleado_id", nullable = false)
    // Especifica la columna en la base de datos que almacena la clave foránea de la relación.
    private Empleado empleado;
    // Representa el empleado asociado a esta nómina.

    private Double sueldoBase;
    // Almacena el sueldo base del empleado.

    private Double deducciones;
    // Almacena las deducciones aplicadas al sueldo del empleado.

    private Double pagoNeto;
    // Almacena el pago neto que recibirá el empleado después de las deducciones.

    @Column(name = "fecha_pago")
    // Especifica el nombre de la columna en la base de datos para este campo.
    private java.time.LocalDate fechaPago;
    // Almacena la fecha en la que se realizó el pago.
}
