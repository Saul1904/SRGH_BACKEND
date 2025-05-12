document.addEventListener("DOMContentLoaded", () => {
    const empleadoId = obtenerIdEmpleado();
    if (!empleadoId) {
        mostrarError("No se encontró el ID del empleado.");
        return;
    }

    cargarNominas(empleadoId);
    cargarEmpleadosEnFormulario();
    document.getElementById("form-agregar-nomina").addEventListener("submit", event => agregarNomina(event, empleadoId));
    document.getElementById("form-editar-nomina").addEventListener("submit", event => actualizarNomina(event, empleadoId));
});

// =================== FUNCIONES PRINCIPALES ===================

function cargarEmpleadosEnFormulario() {
    fetch("http://localhost:8080/api/empleados")
        .then(response => response.json())
        .then(data => {
            const selectAgregar = document.getElementById("empleado-id");
            const selectEditar = document.getElementById("editar-empleado-id");

            selectAgregar.innerHTML = "";
            selectEditar.innerHTML = "";

            data.forEach(empleado => {
                const option = `<option value="${empleado.id}">${empleado.nombre} ${empleado.apellido}</option>`;
                selectAgregar.innerHTML += option;
                selectEditar.innerHTML += option;
            });
        })
        .catch(error => mostrarError("Error al cargar empleados."));
}
// Obtener el ID del empleado desde el HTML o la URL
function obtenerIdEmpleado() {
    const empleadoDetalle = document.getElementById("empleado-detalle");
    if (empleadoDetalle) {
        return empleadoDetalle.getAttribute("data-id");
    }

    const params = new URLSearchParams(window.location.search);
    return params.get("id");
}

// Cargar nóminas del empleado actual
function cargarNominas(empleadoId) {
    fetch(`http://localhost:8080/api/nominas/empleado/${empleadoId}`)
        .then(response => response.json())
        .then(data => actualizarTablaNominas(data))
        .catch(error => mostrarError("Error al cargar nóminas."));
}

// Actualizar tabla de nóminas
function actualizarTablaNominas(nominas) {
    const tablaNominas = document.getElementById("tabla-nominas");
    tablaNominas.innerHTML = "";

    nominas.forEach(nomina => {
        const fila = crearFilaNomina(nomina);
        tablaNominas.appendChild(fila);
    });
}

// Crear fila de nómina
function crearFilaNomina(nomina) {
    const empleadoNombre = nomina.empleado
        ? `${nomina.empleado.nombre} ${nomina.empleado.apellido}`
        : "No asignado";

    const fechaPago = formatearFecha(nomina.fechaPago);

    const fila = document.createElement("tr");
    fila.innerHTML = `
        <td>${nomina.id}</td>
        <td>${empleadoNombre}</td>
        <td>${fechaPago}</td>
        <td>${nomina.sueldoBase}</td>
        <td>${nomina.deducciones}</td>
        <td>${nomina.pagoNeto}</td>
        <td>
            <button onclick="editarNomina(${nomina.id})">✏️ Editar</button>
            <button onclick="eliminarNomina(${nomina.id})">🗑️ Eliminar</button>
        </td>
    `;
    return fila;
}

// =================== CRUD DE NÓMINAS ===================

// Agregar nómina
function agregarNomina(event, empleadoId) {
    event.preventDefault();

    const nomina = {
        empleado: { id: empleadoId },
        fechaPago: document.getElementById("fecha-pago").value.trim(),
        sueldoBase: document.getElementById("sueldo-base").value.trim(),
        deducciones: document.getElementById("deducciones").value.trim(),
        pagoNeto: document.getElementById("pago-neto").value.trim()
    };

    fetch("http://localhost:8080/api/nominas", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(nomina)
    })
        .then(response => response.json())
        .then(() => {
            cargarNominas(empleadoId);
            cerrarModal("modal-agregar-nomina");
            mostrarNotificacion("Nómina agregada correctamente.");
        })
        .catch(error => mostrarError("Error al agregar nómina."));
}

// Editar nómina
function editarNomina(id) {
    fetch(`http://localhost:8080/api/nominas/${id}`)
        .then(response => response.json())
        .then(nomina => {
            document.getElementById("editar-id").value = nomina.id;
            document.getElementById("editar-empleado-id").value = nomina.empleado ? nomina.empleado.id : "";
            document.getElementById("editar-fecha-pago").value = nomina.fechaPago;
            document.getElementById("editar-sueldo-base").value = nomina.sueldoBase;
            document.getElementById("editar-deducciones").value = nomina.deducciones;
            document.getElementById("editar-pago-neto").value = nomina.pagoNeto;

            mostrarModal("modal-editar-nomina");
        })
        .catch(error => mostrarError("Error al cargar nómina para edición."));
}

// Actualizar nómina
function actualizarNomina(event, empleadoId) {
    event.preventDefault();

    const id = document.getElementById("editar-id").value;
    const nomina = {
        empleado: { id: empleadoId },
        fechaPago: document.getElementById("editar-fecha-pago").value.trim(),
        sueldoBase: document.getElementById("editar-sueldo-base").value.trim(),
        deducciones: document.getElementById("editar-deducciones").value.trim(),
        pagoNeto: document.getElementById("editar-pago-neto").value.trim()
    };

    fetch(`http://localhost:8080/api/nominas/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(nomina)
    })
        .then(() => {
            cargarNominas(empleadoId);
            cerrarModal("modal-editar-nomina");
            mostrarNotificacion("Nómina actualizada correctamente.");
        })
        .catch(error => mostrarError("Error al actualizar nómina."));
}

// Eliminar nómina
function eliminarNomina(id) {
    if (!confirm("¿Estás seguro de que quieres eliminar esta nómina?")) return;

    fetch(`http://localhost:8080/api/nominas/${id}`, {
        method: "DELETE"
    })
        .then(() => {
            const empleadoId = obtenerIdEmpleado();
            cargarNominas(empleadoId);
            mostrarNotificacion("Nómina eliminada correctamente.");
        })
        .catch(error => mostrarError("Error al eliminar nómina."));
}

// =================== FUNCIONES AUXILIARES ===================

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

function mostrarModalAgregarNomina() {
    mostrarModal("modal-agregar-nomina");
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