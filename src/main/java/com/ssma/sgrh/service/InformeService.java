package com.ssma.sgrh.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssma.sgrh.models.Informe;
import com.ssma.sgrh.repository.InformeRepository;

@Service
public class InformeService {

    @Autowired
    private InformeRepository informeRepository;

    public List<Informe> obtenerTodos() {
        return informeRepository.findAll();
    }

    public Optional<Informe> obtenerPorId(Long id) {
        return informeRepository.findById(id);
    }

    public Informe guardar(Informe informe) {
        return informeRepository.save(informe);
    }

    public void eliminar(Long id) {
        informeRepository.deleteById(id);
    }
}
