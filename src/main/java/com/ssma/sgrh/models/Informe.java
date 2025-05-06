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
@Table(name = "Informes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Informe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipoInforme;

    @Column(name = "fecha_generacion")
    private java.time.LocalDateTime fechaGeneracion;
}

