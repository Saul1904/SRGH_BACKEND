package com.ssma.sgrh.models;

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


@Entity
@Table(name = "Empleados") // ✅ Corrige el nombre para mantener consistencia
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private Double sueldo;
    private String foto;

    @Column(name = "fecha_contratacion")
    private java.time.LocalDate fechaContratacion;

    private String departamento;

    

   
}
