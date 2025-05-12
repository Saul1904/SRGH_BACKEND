package com.ssma.sgrh.service;
// Define el paquete donde se encuentra esta clase.

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssma.sgrh.models.Documento;
import com.ssma.sgrh.repository.DocumentoRepository;
// Importa las clases del modelo y repositorio necesarias para esta clase.

@Service
// Marca esta clase como un servicio de Spring, lo que permite que sea gestionada como un componente de la aplicación.
public class DocumentoService {

    @Autowired
    // Inyecta automáticamente una instancia de DocumentoRepository en esta clase.
    private DocumentoRepository documentoRepository;

    public List<Documento> obtenerDocumentosPorEmpleado(Long empleadoId) {
        // Método para obtener una lista de documentos asociados a un empleado específico.
        return documentoRepository.findByEmpleadoId(empleadoId);
        // Llama al método del repositorio para buscar documentos por el ID del empleado.
    }

    public Documento guardarDocumento(Documento documento) {
        // Método para guardar un documento en la base de datos.
        documento.setFechaSubida(LocalDateTime.now());
        // Establece la fecha actual como la fecha de subida del documento.
        documento.setEstado("Activo");
        // Establece el estado del documento como "Activo".
        return documentoRepository.save(documento);
        // Guarda el documento en la base de datos y lo retorna.
    }

    public void eliminarDocumento(Long id) {
        // Método para eliminar un documento de la base de datos por su ID.
        documentoRepository.deleteById(id);
        // Llama al repositorio para eliminar el documento con el ID especificado.
    }
}