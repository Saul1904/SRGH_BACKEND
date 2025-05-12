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
// Importa las anotaciones necesarias para la persistencia y las utilidades de Lombok.

@Entity
// Marca esta clase como una entidad JPA que se mapeará a una tabla en la base de datos.
@Table(name = "Ausencias")
// Especifica el nombre de la tabla en la base de datos asociada a esta entidad.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// Genera automáticamente los métodos getter, setter, un constructor sin argumentos y un constructor con todos los argumentos usando Lombok.
public class Ausencia {

    @Id
    // Indica que este campo es la clave primaria de la entidad.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Especifica que el valor de la clave primaria será generado automáticamente por la base de datos.
    private Long id;
    // Campo que representa el identificador único de la ausencia.

    @ManyToOne
    // Define una relación de muchos-a-uno con otra entidad.
    @JoinColumn(name = "empleado_id", nullable = false)
    // Especifica la columna de la base de datos que almacena la clave foránea hacia la entidad Empleado.
    private Empleado empleado;
    // Campo que representa al empleado asociado con esta ausencia.

    public Long getEmpleadoId() {
        // Método para obtener el ID del empleado asociado, devolviendo null si no hay empleado.
        return empleado != null ? empleado.getId() : null;
    }

    public void setEmpleado(Empleado empleado) {
        // Método para establecer el empleado asociado con esta ausencia.
        this.empleado = empleado;
    }

    @Column(name = "fecha_inicio")
    // Especifica la columna en la base de datos que almacena la fecha de inicio de la ausencia.
    private java.time.LocalDate fechaInicio;
    // Campo que representa la fecha de inicio de la ausencia.

    @Column(name = "fecha_fin")
    // Especifica la columna en la base de datos que almacena la fecha de fin de la ausencia.
    private java.time.LocalDate fechaFin;
    // Campo que representa la fecha de fin de la ausencia.

    private String tipoAusencia;
    // Campo que representa el tipo de ausencia (por ejemplo, enfermedad, vacaciones, etc.).
}
