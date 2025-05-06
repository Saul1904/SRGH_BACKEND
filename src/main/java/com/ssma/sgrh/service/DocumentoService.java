package com.ssma.sgrh.service;

import java.util.List;
import java.util.Optional;

import com.ssma.sgrh.models.Documento;

public interface DocumentoService {
    Documento guardar(Documento documento);
    List<Documento> obtenerTodos();
    Optional<Documento> obtenerPorId(Long id);
    List<Documento> obtenerPorEmpleadoId(Long empleadoId);
    void eliminar(Long id);
}
