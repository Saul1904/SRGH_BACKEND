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
@Table(name = "Ausencias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ausencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "empleado_id", nullable = false)
    private Empleado empleado;

    public Long getEmpleadoId() {
        return empleado != null ? empleado.getId() : null;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    @Column(name = "fecha_inicio")
    private java.time.LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private java.time.LocalDate fechaFin;

    private String tipoAusencia;
}
