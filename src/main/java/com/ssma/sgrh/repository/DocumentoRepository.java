package com.ssma.sgrh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssma.sgrh.models.Documento;

public interface DocumentoRepository extends JpaRepository<Documento, Long> {
    List<Documento> findByEmpleadoId(Long empleadoId);
}
