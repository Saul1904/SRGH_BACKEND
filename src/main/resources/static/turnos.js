document.addEventListener("DOMContentLoaded", () => {
    const empleadoId = obtenerIdEmpleado();
    if (!empleadoId) {
        mostrarError("No se encontró el ID del empleado.");
        return;
    }

    cargarTurnos(empleadoId);
    cargarEmpleadosEnFormulario();
    document.getElementById("form-agregar").addEventListener("submit", event => agregarTurno(event, empleadoId));
    document.getElementById("form-editar").addEventListener("submit", event => actualizarTurno(event, empleadoId));
});

// =================== FUNCIONES PRINCIPALES ===================

// Obtener el ID del empleado desde el HTML o la URL
function obtenerIdEmpleado() {
    const params = new URLSearchParams(window.location.search);
    return params.get("id");
}

// Cargar turnos del empleado actual
function cargarTurnos(empleadoId) {
    fetch(`http://localhost:8080/api/turnos/empleado/${empleadoId}`)
        .then(response => response.json())
        .then(data => actualizarTablaTurnos(data))
        .catch(error => mostrarError("Error al cargar turnos."));
}

// Actualizar tabla de turnos
function actualizarTablaTurnos(turnos) {
    const tablaTurnos = document.getElementById("tabla-turnos");
    tablaTurnos.innerHTML = "";

    turnos.forEach(turno => {
        const fila = crearFilaTurno(turno);
        tablaTurnos.appendChild(fila);
    });
}

// Crear fila de turno
function crearFilaTurno(turno) {
    const empleadoNombre = turno.empleado
        ? `${turno.empleado.nombre} ${turno.empleado.apellido}`
        : "No asignado";

    const fila = document.createElement("tr");
    fila.innerHTML = `
        <td>${turno.id}</td>
        <td>${empleadoNombre}</td>
        <td>${turno.fecha}</td>
        <td>${turno.horarioInicio}</td>
        <td>${turno.horarioFin}</td>
        <td>
            <button onclick="editarTurno(${turno.id})">✏️ Editar</button>
            <button onclick="eliminarTurno(${turno.id})">🗑️ Eliminar</button>
        </td>
    `;
    return fila;
}

// =================== CRUD DE TURNOS ===================

// Agregar turno
function agregarTurno(event, empleadoId) {
    event.preventDefault();

    const turno = {
        empleado: { id: document.getElementById("empleado-id").value },
        fecha: document.getElementById("fecha").value.trim(),
        horarioInicio: document.getElementById("horario-inicio").value.trim(),
        horarioFin: document.getElementById("horario-fin").value.trim()
    };

    fetch("http://localhost:8080/api/turnos", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(turno)
    })
        .then(() => {
            cargarTurnos(empleadoId);
            cerrarModal("modal-agregar");
            mostrarNotificacion("Turno registrado correctamente.");
        })
        .catch(error => mostrarError("Error al registrar turno."));
}

// Editar turno
function editarTurno(id) {
    fetch(`http://localhost:8080/api/turnos/${id}`)
        .then(response => response.json())
        .then(turno => {
            document.getElementById("editar-id").value = turno.id;
            document.getElementById("editar-empleado-id").value = turno.empleado ? turno.empleado.id : "";
            document.getElementById("editar-fecha").value = turno.fecha;
            document.getElementById("editar-horario-inicio").value = turno.horarioInicio;
            document.getElementById("editar-horario-fin").value = turno.horarioFin;

            mostrarModal("modal-editar");
        })
        .catch(error => mostrarError("Error al cargar turno para edición."));
}

// Actualizar turno
function actualizarTurno(event, empleadoId) {
    event.preventDefault();

    const id = document.getElementById("editar-id").value;
    const turno = {
        empleado: { id: document.getElementById("editar-empleado-id").value },
        fecha: document.getElementById("editar-fecha").value.trim(),
        horarioInicio: document.getElementById("editar-horario-inicio").value.trim(),
        horarioFin: document.getElementById("editar-horario-fin").value.trim()
    };

    fetch(`http://localhost:8080/api/turnos/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(turno)
    })
        .then(() => {
            cargarTurnos(empleadoId);
            cerrarModal("modal-editar");
            mostrarNotificacion("Turno actualizado correctamente.");
        })
        .catch(error => mostrarError("Error al actualizar turno."));
}

// Eliminar turno
function eliminarTurno(id) {
    if (!confirm("¿Estás seguro de que quieres eliminar este turno?")) return;

    fetch(`http://localhost:8080/api/turnos/${id}`, {
        method: "DELETE"
    })
        .then(() => {
            const empleadoId = obtenerIdEmpleado();
            cargarTurnos(empleadoId);
            mostrarNotificacion("Turno eliminado correctamente.");
        })
        .catch(error => mostrarError("Error al eliminar turno."));
}

// Mostrar y cerrar modales

function mostrarModalAgregarNomina() {
    mostrarModal("modal-agregar-turno");
}

// Asegúrate de que la función esté disponible globalmente
window.mostrarModalAgregarNomina = mostrarModalAgregarNomina;



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

// Mostrar error
function mostrarError(mensaje) {
    console.error(mensaje);
    alert(mensaje);
}