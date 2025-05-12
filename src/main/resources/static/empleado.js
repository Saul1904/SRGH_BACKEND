document.addEventListener("DOMContentLoaded", () => {
    const params = new URLSearchParams(window.location.search);
    const id = params.get("id");

    // Validación del ID del empleado
    if (!id || isNaN(id) || id.trim() === "") {
        console.warn("❌ ID de empleado no encontrado en la URL.");
        return;
    }
    console.log("✅ ID de empleado detectado:", id);

    // Cargar los datos del empleado
    cargarDatosEmpleado(id);
});

// Función para cargar datos del empleado
function cargarDatosEmpleado(idEmpleado) {
    if (!idEmpleado) {
        console.error("❌ No se ha proporcionado un ID de empleado válido.");
        return;
    }

    fetch(`http://localhost:8080/api/empleados/${idEmpleado}`)
        .then(res => {
            if (!res.ok) throw new Error("❌ Error en la respuesta del servidor.");
            return res.json();
        })
        .then(empleado => {
            document.getElementById('nombre').textContent = empleado.nombre || "No disponible";
            document.getElementById('apellido').textContent = empleado.apellido || "No disponible";
            document.getElementById('email').textContent = empleado.email || "No disponible";
            document.getElementById('telefono').textContent = empleado.telefono || "No disponible";
            document.getElementById('sueldo').textContent = `$${empleado.sueldo}` || "No disponible";
            document.getElementById('departamento').textContent = empleado.departamento || "No disponible";
            document.getElementById('fecha').textContent = formatearFecha(empleado.fechaContratacion);
        })
        .catch(error => {
            console.error("❌ Error al cargar los datos del empleado:", error);
        });
}

// Función para formatear fechas
function formatearFecha(fecha) {
    return fecha
        ? new Date(fecha).toLocaleDateString('es-ES', {
              year: 'numeric',
              month: '2-digit',
              day: '2-digit'
          })
        : "No disponible";
}

// Función genérica para actualizar datos
function actualizarDatos(url, data, callback) {
    fetch(url, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("❌ Error al guardar los cambios.");
            }
            return response.json();
        })
        .then(result => {
            console.log("✅ Datos actualizados con éxito:", result);
            callback(result);
        })
        .catch(error => {
            console.error("❌ Error al actualizar los datos:", error);
        });
}

// Función para mostrar el modal de edición de datos públicos
function mostrarModalEditarDatosPublicos() {
    const modal = document.getElementById('modal-editar-datos-publicos');

    // Cargar los datos actuales en los campos del formulario
    document.getElementById('editar-nombre').value = document.getElementById('nombre').textContent.trim();
    document.getElementById('editar-apellido').value = document.getElementById('apellido').textContent.trim();
    document.getElementById('editar-departamento').value = document.getElementById('departamento').textContent.trim();

    modal.style.display = 'block';
}

// Manejar el envío del formulario para guardar los cambios en datos públicos
document.getElementById('form-editar-datos-publicos').addEventListener('submit', function (e) {
    e.preventDefault();

    const idEmpleado = new URLSearchParams(window.location.search).get("id");
    if (!idEmpleado) {
        console.error("❌ No se encontró el ID del empleado.");
        return;
    }

    const data = {
        nombre: document.getElementById('editar-nombre').value.trim(),
        apellido: document.getElementById('editar-apellido').value.trim(),
        departamento: document.getElementById('editar-departamento').value.trim()
    };

    actualizarDatos(`http://localhost:8080/api/empleados/${idEmpleado}`, data, result => {
        document.getElementById('nombre').textContent = result.nombre || "No disponible";
        document.getElementById('apellido').textContent = result.apellido || "No disponible";
        document.getElementById('departamento').textContent = result.departamento || "No disponible";
        cerrarModal('modal-editar-datos-publicos');
    });
});

// Función para mostrar el modal de edición de datos personales
function mostrarModalEditarDatosPersonales() {
    const modal = document.getElementById('modal-editar-datos-personales');

    // Cargar los datos actuales en los campos del formulario
    document.getElementById('editar-email').value = document.getElementById('email').textContent.trim();
    document.getElementById('editar-telefono').value = document.getElementById('telefono').textContent.trim();
    document.getElementById('editar-sueldo').value = document.getElementById('sueldo').textContent.trim();
    document.getElementById('editar-fecha').value = document.getElementById('fecha').textContent.trim();

    modal.style.display = 'block';
}

// Manejar el envío del formulario para guardar los cambios en datos personales
document.getElementById('form-editar-datos-personales').addEventListener('submit', function (e) {
    e.preventDefault();

    const idEmpleado = new URLSearchParams(window.location.search).get("id");
    if (!idEmpleado) {
        console.error("❌ No se encontró el ID del empleado.");
        return;
    }

    const fechaInput = document.getElementById('editar-fecha').value.trim();
    const fechaFormateada = new Date(fechaInput).toISOString().split('T')[0];

    const data = {
        email: document.getElementById('editar-email').value.trim(),
        telefono: document.getElementById('editar-telefono').value.trim(),
        sueldo: document.getElementById('editar-sueldo').value.trim(),
        fechaContratacion: fechaFormateada
    };

    actualizarDatos(`http://localhost:8080/api/empleados/${idEmpleado}`, data, result => {
        document.getElementById('email').textContent = result.email || "No disponible";
        document.getElementById('telefono').textContent = result.telefono || "No disponible";
        document.getElementById('sueldo').textContent = `$${result.sueldo}` || "No disponible";
        document.getElementById('fecha').textContent = formatearFecha(result.fechaContratacion);
        cerrarModal('modal-editar-datos-personales');
    });
});

// Función para mostrar pestañas
function mostrarTab(tabId) {
    const tabs = document.querySelectorAll('.contenido-tab');
    tabs.forEach(tab => tab.classList.remove('active'));

    const tabSeleccionado = document.getElementById(tabId);
    if (tabSeleccionado) {
        tabSeleccionado.classList.add('active');
    }
}

// Función para cerrar el modal
function cerrarModal(modalId) {
    const modal = document.getElementById(modalId);
    modal.style.display = 'none';
}

function abrirModalAgregarImagen(event, idEmpleado) {
    event.stopPropagation(); // Evita que el clic en el botón active la redirección
    console.log(`Abrir modal para agregar imagen al empleado con ID: ${idEmpleado}`);
    // Aquí puedes abrir un modal o realizar la acción para agregar la imagen
}