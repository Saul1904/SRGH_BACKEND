package com.ssma.sgrh.models;

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

@Entity
@Table(name = "Turnos") // ✅ Corrige el nombre de la tabla para evitar errores
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "empleado_id", nullable = false)
    private Empleado empleado;

    @Column(name = "fecha", nullable = false) 
    private java.time.LocalDate fecha;

    @Column(name = "horario_inicio", nullable = false) 
    private java.time.LocalTime horarioInicio;

    @Column(name = "horario_fin", nullable = false) 
    private java.time.LocalTime horarioFin;
}
