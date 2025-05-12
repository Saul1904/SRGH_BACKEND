package com.ssma.sgrh.models;

// Importa las clases necesarias para la entidad
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

// Marca esta clase como una entidad JPA, que se mapea a una tabla en la base de datos
@Entity
public class Documento {

    // Marca este campo como la clave primaria de la entidad
    @Id
    // Especifica que el valor de la clave primaria será generado automáticamente
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Define una relación de muchos a uno con la entidad Empleado
    @ManyToOne
    // Especifica la columna de clave foránea en la base de datos
    @JoinColumn(name = "empleado_id", nullable = false)
    private Empleado empleado;

    // Mapea este campo a una columna no nula en la base de datos
    @Column(nullable = false)
    private String nombreDocumento; // Nombre del documento

    // Mapea este campo a una columna no nula en la base de datos
    @Column(nullable = false)
    private String tipoDocumento; // Tipo del documento

    // Mapea este campo a una columna no nula en la base de datos
    @Column(nullable = false)
    private LocalDateTime fechaSubida; // Fecha y hora de subida del documento

    // Mapea este campo a una columna no nula en la base de datos
    @Column(nullable = false)
    private String estado; // Estado del documento (por ejemplo, activo, inactivo)

    // Mapea este campo a una columna no nula en la base de datos
    @Column(nullable = false)
    private String archivo; // Ruta o nombre del archivo asociado al documento

    // Getter para la clave primaria
    public Long getId() {
        return id;
    }

    // Setter para la clave primaria
    public void setId(Long id) {
        this.id = id;
    }

    // Getter para la entidad Empleado asociada
    public Empleado getEmpleado() {
        return empleado;
    }

    // Setter para la entidad Empleado asociada
    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    // Getter para el nombre del documento
    public String getNombreDocumento() {
        return nombreDocumento;
    }

    // Setter para el nombre del documento
    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }

    // Getter para el tipo del documento
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    // Setter para el tipo del documento
    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    // Getter para la fecha y hora de subida
    public LocalDateTime getFechaSubida() {
        return fechaSubida;
    }

    // Setter para la fecha y hora de subida
    public void setFechaSubida(LocalDateTime fechaSubida) {
        this.fechaSubida = fechaSubida;
    }

    // Getter para el estado del documento
    public String getEstado() {
        return estado;
    }

    // Setter para el estado del documento
    public void setEstado(String estado) {
        this.estado = estado;
    }

    // Getter para la ruta o nombre del archivo
    public String getArchivo() {
        return archivo;
    }

    // Setter para la ruta o nombre del archivo
    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }
}
