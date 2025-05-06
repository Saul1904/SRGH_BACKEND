package com.ssma.sgrh.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssma.sgrh.models.Documento;
import com.ssma.sgrh.repository.DocumentoRepository;

@Service
public class DocumentoServiceImpl implements DocumentoService {

    @Autowired
    private DocumentoRepository documentoRepository;

    @Override
    public Documento guardar(Documento documento) {
        return documentoRepository.save(documento);
    }

    @Override
    public List<Documento> obtenerTodos() {
        return documentoRepository.findAll();
    }

    @Override
    public Optional<Documento> obtenerPorId(Long id) {
        return documentoRepository.findById(id);
    }

    @Override
    public List<Documento> obtenerPorEmpleadoId(Long empleadoId) {
        return documentoRepository.findByEmpleadoId(empleadoId);
    }

    @Override
    public void eliminar(Long id) {
        documentoRepository.deleteById(id);
    }
}
