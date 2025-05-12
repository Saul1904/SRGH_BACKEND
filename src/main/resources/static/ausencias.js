document.addEventListener("DOMContentLoaded", () => {
    const empleadoId = obtenerIdEmpleado();
    if (!empleadoId) {
        mostrarError("No se encontró el ID del empleado.");
        return;
    }

    cargarAusencias(empleadoId);
    cargarEmpleadosEnFormulario();
    document.getElementById("form-agregar-ausencia").addEventListener("submit", event => agregarAusencia(event, empleadoId));
    document.getElementById("form-editar-ausencia").addEventListener("submit", event => actualizarAusencia(event, empleadoId));
    document.getElementById("buscar-ausencia").addEventListener("input", filtrarAusencias);
    document.getElementById("filtro-tipo").addEventListener("change", filtrarAusencias);
});

// =================== FUNCIONES PRINCIPALES ===================

// Obtener el ID del empleado desde el HTML o la URL
function obtenerIdEmpleado() {
    const params = new URLSearchParams(window.location.search);
    return params.get("id");
}

// Cargar ausencias del empleado actual
function cargarAusencias(empleadoId) {
    fetch(`http://localhost:8080/api/ausencias/empleado/${empleadoId}`)
        .then(response => response.json())
        .then(data => actualizarTablaAusencias(data))
        .catch(error => mostrarError("Error al cargar ausencias."));
}

// Actualizar tabla de ausencias
function actualizarTablaAusencias(ausencias) {
    const tablaAusencias = document.getElementById("tabla-ausencias");
    tablaAusencias.innerHTML = "";

    ausencias.forEach(ausencia => {
        const fila = crearFilaAusencia(ausencia);
        tablaAusencias.appendChild(fila);
    });
}

// Crear fila de ausencia
function crearFilaAusencia(ausencia) {
    const empleadoNombre = ausencia.empleado
        ? `${ausencia.empleado.nombre} ${ausencia.empleado.apellido}`
        : "No asignado";

    const fechaInicio = formatearFecha(ausencia.fechaInicio);
    const fechaFin = formatearFecha(ausencia.fechaFin);

    const fila = document.createElement("tr");
    fila.innerHTML = `
        <td>${ausencia.id}</td>
        <td>${empleadoNombre}</td>
        <td>${fechaInicio}</td>
        <td>${fechaFin}</td>
        <td>${ausencia.tipoAusencia || "Sin definir"}</td>
        <td>
            <button onclick="editarAusencia(${ausencia.id})">✏️ Editar</button>
            <button onclick="eliminarAusencia(${ausencia.id})">🗑️ Eliminar</button>
        </td>
    `;
    return fila;
}

// =================== CRUD DE AUSENCIAS ===================

// Agregar ausencia
function agregarAusencia(event, empleadoId) {
    event.preventDefault();

    const ausencia = {
        empleado: { id: empleadoId },
        fechaInicio: document.getElementById("fecha-inicio").value.trim(),
        fechaFin: document.getElementById("fecha-fin").value.trim(),
        tipoAusencia: document.getElementById("tipo-ausencia").value.trim()
    };

    fetch("http://localhost:8080/api/ausencias", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(ausencia)
    })
        .then(() => {
            cargarAusencias(empleadoId);
            cerrarModal("modal-agregar-ausencia");
            mostrarNotificacion("Ausencia registrada correctamente.");
        })
        .catch(error => mostrarError("Error al registrar ausencia."));
}

// Editar ausencia
function editarAusencia(id) {
    fetch(`http://localhost:8080/api/ausencias/${id}`)
        .then(response => response.json())
        .then(ausencia => {
            document.getElementById("editar-id").value = ausencia.id;
            document.getElementById("editar-empleado-id").value = ausencia.empleado ? ausencia.empleado.id : "";
            document.getElementById("editar-fecha-inicio").value = ausencia.fechaInicio || "";
            document.getElementById("editar-fecha-fin").value = ausencia.fechaFin || "";
            document.getElementById("editar-tipo-ausencia").value = ausencia.tipoAusencia || "";

            mostrarModal("modal-editar-ausencia");
        })
        .catch(error => mostrarError("Error al cargar ausencia para edición."));
}

// Actualizar ausencia
function actualizarAusencia(event, empleadoId) {
    event.preventDefault();

    const id = document.getElementById("editar-id").value;
    const ausencia = {
        empleado: { id: empleadoId },
        fechaInicio: document.getElementById("editar-fecha-inicio").value.trim(),
        fechaFin: document.getElementById("editar-fecha-fin").value.trim(),
        tipoAusencia: document.getElementById("editar-tipo-ausencia").value.trim()
    };

    fetch(`http://localhost:8080/api/ausencias/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(ausencia)
    })
        .then(() => {
            cargarAusencias(empleadoId);
            cerrarModal("modal-editar-ausencia");
            mostrarNotificacion("Ausencia actualizada correctamente.");
        })
        .catch(error => mostrarError("Error al actualizar ausencia."));
}

// Eliminar ausencia
function eliminarAusencia(id) {
    if (!confirm("¿Estás seguro de que quieres eliminar esta ausencia?")) return;

    fetch(`http://localhost:8080/api/ausencias/${id}`, { method: "DELETE" })
        .then(() => {
            const empleadoId = obtenerIdEmpleado();
            cargarAusencias(empleadoId);
            mostrarNotificacion("Ausencia eliminada correctamente.");
        })
        .catch(error => mostrarError("Error al eliminar ausencia."));
}

// =================== FUNCIONES AUXILIARES ===================


function filtrarAusencias() {
    console.log("Filtrando ausencias...");
    // Aquí va la lógica para filtrar las ausencias
}
// Formatear fechas
function formatearFecha(fecha) {
    return fecha
        ? new Date(fecha).toLocaleDateString("es-MX", {
              year: "numeric",
              month: "2-digit",
              day: "2-digit"
          })
        : "Sin definir";
}


// Mostrar y cerrar modales


function mostrarModalAgregarAusencia() {
    mostrarModal("modal-agregar-ausencia");
}

// Asegúrate de que la función esté disponible globalmente
window.mostrarModalAgregarAusencia = mostrarModalAgregarAusencia;

function mostrarModal(modalId) {
    const modal = document.getElementById(modalId);
    modal.style.display = "flex";

    setTimeout(() => {
        modal.classList.add("mostrar");
    }, 10);
}

function cerrarModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) {
        modal.classList.remove("mostrar");
        setTimeout(() => {
            modal.style.display = "none";
        }, 300);
    }
}

// Mostrar notificación
function mostrarNotificacion(mensaje) {
    alert(mensaje);
}