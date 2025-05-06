package com.ssma.sgrh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssma.sgrh.models.Empleado;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
}

