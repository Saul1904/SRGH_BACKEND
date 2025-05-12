package com.ssma.sgrh.models;
// Define el paquete donde se encuentra la clase, útil para organizar el código.

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
// Importa las anotaciones de Lombok para generar automáticamente código como getters, setters y constructores.

@Entity
// Indica que esta clase es una entidad JPA y se mapeará a una tabla en la base de datos.

@Table(name = "Informes")
// Especifica el nombre de la tabla en la base de datos que corresponde a esta entidad.

@Getter
@Setter
// Genera automáticamente los métodos getter y setter para los atributos de la clase.

@NoArgsConstructor
// Genera un constructor sin argumentos.

@AllArgsConstructor
// Genera un constructor con argumentos para todos los atributos de la clase.

public class Informe {
    // Define la clase `Informe`, que representa una entidad en la base de datos.

    @Id
    // Marca este atributo como la clave primaria de la tabla.

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Especifica que el valor de la clave primaria será generado automáticamente por la base de datos.

    private Long id;
    // Define el atributo `id` como la clave primaria de tipo Long.

    private String tipoInforme;
    // Define el atributo `tipoInforme` para almacenar el tipo de informe.

    @Column(name = "fecha_generacion")
    // Mapea el atributo `fechaGeneracion` a la columna `fecha_generacion` en la tabla.

    private java.time.LocalDateTime fechaGeneracion;
    // Define el atributo `fechaGeneracion` para almacenar la fecha y hora de generación del informe.
}
