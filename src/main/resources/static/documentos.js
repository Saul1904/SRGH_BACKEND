document.addEventListener("DOMContentLoaded", () => {
    const empleadoId = obtenerIdEmpleado();
    if (!empleadoId) {
        mostrarError("No se encontró el ID del empleado.");
        return;
    }

    cargarDocumentos(empleadoId);
    cargarEmpleadosEnFormulario();
    document.getElementById("form-subir-documento").addEventListener("submit", event => subirDocumento(event, empleadoId));
});

// =================== FUNCIONES PRINCIPALES ===================

// Obtener el ID del empleado desde el HTML o la URL
function obtenerIdEmpleado() {
    const params = new URLSearchParams(window.location.search);
    return params.get("id");
}

// Cargar documentos del empleado actual
function cargarDocumentos(empleadoId) {
    fetch(`http://localhost:8080/api/documentos/empleado/${empleadoId}`)
        .then(response => response.json())
        .then(data => actualizarTablaDocumentos(data))
        .catch(error => mostrarError("Error al cargar documentos."));
}

// Actualizar tabla de documentos
function actualizarTablaDocumentos(documentos) {
    const tablaDocumentos = document.getElementById("tabla-documentos");
    tablaDocumentos.innerHTML = "";

    documentos.forEach(documento => {
        const fila = crearFilaDocumento(documento);
        tablaDocumentos.appendChild(fila);
    });
}

// Crear fila de documento
function crearFilaDocumento(documento) {
    const fila = document.createElement("tr");
    fila.innerHTML = `
        <td>${documento.nombreDocumento}</td>
        <td>${documento.tipoDocumento}</td>
        <td>${documento.fechaSubida}</td>
        <td>${documento.estado}</td>
        <td>
            <button onclick="verDocumento('${documento.archivo}')">👁️ Ver</button>
            <button onclick="eliminarDocumento(${documento.id})">🗑️ Eliminar</button>
        </td>
    `;
    return fila;
}

// =================== CRUD DE DOCUMENTOS ===================

// Subir documento
function subirDocumento(event, empleadoId) {
    event.preventDefault();

    // Obtener los valores del formulario
    const nombreDocumento = document.getElementById("nombre-documento").value.trim();
    const tipoDocumento = document.getElementById("tipo-documento").value;
    const archivo = document.getElementById("archivo-documento").files[0];

    // Validación básica
    if (!nombreDocumento || !tipoDocumento || !archivo) {
        mostrarError("Por favor completa todos los campos antes de subir el documento.");
        return;
    }

    const formData = new FormData();
    formData.append("empleadoId", empleadoId);
    formData.append("nombreDocumento", nombreDocumento);
    formData.append("tipoDocumento", tipoDocumento);
    formData.append("archivo", archivo);

    fetch("http://localhost:8080/api/documentos", {
        method: "POST",
        body: formData
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Error al subir el documento.");
        }
        return response.json();
    })
    .then(() => {
        cargarDocumentos(empleadoId); // Recarga la lista de documentos
        mostrarNotificacion("Documento subido correctamente.");
        // Limpiar formulario si quieres:
        document.getElementById("nombre-documento").value = "";
        document.getElementById("tipo-documento").value = "";
        document.getElementById("archivo-documento").value = "";
    })
    .catch(error => {
        console.error(error);
        mostrarError("No se pudo subir el documento. Verifica que el empleado exista y que todos los datos estén correctos.");
    });
}


// Eliminar documento
function eliminarDocumento(id) {
    if (!confirm("¿Estás seguro de que quieres eliminar este documento?")) return;

    fetch(`http://localhost:8080/api/documentos/${id}`, {
        method: "DELETE"
    })
        .then(() => {
            const empleadoId = obtenerIdEmpleado();
            cargarDocumentos(empleadoId);
            mostrarNotificacion("Documento eliminado correctamente.");
        })
        .catch(error => mostrarError("Error al eliminar documento."));
}

// Ver documento
function verDocumento(rutaArchivo) {
    window.open(rutaArchivo, "_blank");
}

// =================== FUNCIONES AUXILIARES ===================

// Mostrar notificación
function mostrarNotificacion(mensaje) {
    alert(mensaje);
}

// Mostrar error
function mostrarError(mensaje) {
    console.error(mensaje);
    alert(mensaje);
}