document.addEventListener("DOMContentLoaded", () => {
    cargarSeguridadSocial();
    cargarEmpleadosEnFormulario();
    document.getElementById("form-agregar").addEventListener("submit", agregarBeneficio);
    document.getElementById("form-editar").addEventListener("submit", actualizarBeneficio);
});

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
        .catch(error => console.error("Error al cargar empleados:", error));
}

// ✅ Cargar beneficios de seguridad social desde el backend
function cargarSeguridadSocial() {
    fetch("http://localhost:8080/api/seguridad-social")
        .then(response => response.json())
        .then(data => {
            const tablaBeneficios = document.getElementById("tabla-seguridad-social");
            tablaBeneficios.innerHTML = "";

            data.forEach(beneficio => {
                const empleadoNombre = beneficio.empleado 
                    ? `${beneficio.empleado.nombre} ${beneficio.empleado.apellido}` 
                    : "No asignado";

                const fechaRegistro = beneficio.fechaRegistro ? new Date(beneficio.fechaRegistro).toLocaleDateString("es-MX") : "Sin definir";

                const fila = document.createElement("tr");
                fila.innerHTML = `
                    <td>${beneficio.id}</td>
                    <td>${empleadoNombre}</td>
                    <td>${beneficio.numeroSeguro}</td>
                    <td>${beneficio.tipoSeguro}</td>
                    <td>${fechaRegistro}</td>
                    <td>${beneficio.estado}</td>
                    <td>
                        <button onclick="editarBeneficio(${beneficio.id})">✏️ Editar</button>
                        <button onclick="eliminarBeneficio(${beneficio.id})">🗑️ Eliminar</button>
                    </td>
                `;
                tablaBeneficios.appendChild(fila);
            });
        })
        .catch(error => console.error("Error al cargar beneficios:", error));
}

// ✅ Agregar beneficio de seguridad social
function agregarBeneficio(event) {
    event.preventDefault();

    const empleadoId = document.getElementById("empleado-id").value;
    const beneficio = {
        empleado: { id: empleadoId }, 
        numeroSeguro: document.getElementById("numero-seguro").value.trim(),
        tipoSeguro: document.getElementById("tipo-seguro").value.trim(),
        fechaRegistro: document.getElementById("fecha-registro").value.trim(),
        estado: document.getElementById("estado").value.trim()
    };

    fetch("http://localhost:8080/api/seguridad-social", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(beneficio)
    })
    .then(response => response.json())
    .then(() => {
        cargarSeguridadSocial();
        cerrarModal("modal-agregar");
        mostrarNotificacion("Agregado correctamente");
    })
    .catch(error => console.error("Error al agregar beneficio:", error));
}

// ✅ Editar beneficio
function editarBeneficio(id) {
    fetch(`http://localhost:8080/api/seguridad-social/${id}`)
        .then(response => response.json())
        .then(beneficio => {
            document.getElementById("editar-id").value = beneficio.id;
            document.getElementById("editar-empleado-id").value = beneficio.empleado ? beneficio.empleado.id : "";
            document.getElementById("editar-numero-seguro").value = beneficio.numeroSeguro;
            document.getElementById("editar-tipo-seguro").value = beneficio.tipoSeguro;
            document.getElementById("editar-fecha-registro").value = beneficio.fechaRegistro;
            document.getElementById("editar-estado").value = beneficio.estado;

            mostrarModalEditar();
        })
        .catch(error => console.error("Error al cargar beneficio para edición:", error));
}

// ✅ Actualizar beneficio de seguridad social
function actualizarBeneficio(event) {
    event.preventDefault();

    const id = document.getElementById("editar-id").value;
    const beneficio = {
        empleado: { id: document.getElementById("editar-empleado-id").value },
        numeroSeguro: document.getElementById("editar-numero-seguro").value.trim(),
        tipoSeguro: document.getElementById("editar-tipo-seguro").value.trim(),
        fechaRegistro: document.getElementById("editar-fecha-registro").value.trim(),
        estado: document.getElementById("editar-estado").value.trim()
    };

    fetch(`http://localhost:8080/api/seguridad-social/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(beneficio)
    })
    .then(response => {
        if (response.ok) {
            cargarSeguridadSocial();
            cerrarModal("modal-editar");
            mostrarNotificacion("Actualizado correctamente");
        } else {
            console.error("Error al actualizar beneficio.");
        }
    })
    .catch(error => console.error("Error:", error));
}

// ✅ Eliminar beneficio de seguridad social
function eliminarBeneficio(id) {
    if (!confirm("¿Estás seguro de que quieres eliminar este beneficio?")) return;

    fetch(`http://localhost:8080/api/seguridad-social/${id}`, {
        method: "DELETE",
    })
    .then(response => {
        if (response.ok) {
            cargarSeguridadSocial();
            mostrarNotificacion("Eliminado correctamente");
        } else {
            return response.text().then(text => {
                console.error("Error al eliminar el beneficio:", text);
            });
        }
    })
    .catch(error => console.error("Error:", error));
}

//MODAL SEGURIDAD-SOCIAL
// MODAL
function mostrarModalAgregar() {
    const modal = document.getElementById("modal-agregar");
    modal.style.display = "flex";

    setTimeout(() => {
        modal.classList.add("mostrar");
    }, 10);
}

function cerrarModal(idModal) {
    const modal = document.getElementById(idModal);
    if (modal) {
        modal.classList.remove("mostrar");
        setTimeout(() => {
            modal.style.display = "none";
        }, 300);
    }
}

function mostrarModalEditar() {
    const modal = document.getElementById("modal-editar");
    modal.style.display = "flex";

    setTimeout(() => {
        modal.classList.add("mostrar");
    }, 10);
}

// =================== NOTIFICACIÓN ===================
function mostrarNotificacion(mensaje) {
    const notificacion = document.getElementById("notificacion");
    notificacion.textContent = mensaje;
    notificacion.style.display = "block";

    setTimeout(() => {
        notificacion.classList.add("mostrar");
    }, 10);

    setTimeout(() => {
        notificacion.classList.remove("mostrar");
        setTimeout(() => {
            notificacion.style.display = "none";
        }, 500);
    }, 3000);
}

//BUSQUEDA SEGURIDAD SOCIAL
// ✅ Busqueda de beneficios de seguridad social
document.getElementById("buscar-beneficio").addEventListener("input", filtrarBeneficios);
document.getElementById("filtro-seguro").addEventListener("change", filtrarBeneficios);

function filtrarBeneficios() {
    const textoBusqueda = document.getElementById("buscar-beneficio").value.toLowerCase();
    const tipoSeleccionado = document.getElementById("filtro-seguro").value;

    const filas = document.querySelectorAll("#tabla-seguridad-social tr");

    filas.forEach(fila => {
        const empleado = fila.children[1].textContent.toLowerCase();
        const numeroSeguro = fila.children[2].textContent.toLowerCase();
        const tipoSeguro = fila.children[3].textContent;
        const estado = fila.children[5].textContent.toLowerCase();

        const coincideTexto = empleado.includes(textoBusqueda) || numeroSeguro.includes(textoBusqueda) || estado.includes(textoBusqueda);
        const coincideTipo = tipoSeleccionado === "" || tipoSeguro === tipoSeleccionado;

        fila.style.display = coincideTexto && coincideTipo ? "" : "none";
    });
}