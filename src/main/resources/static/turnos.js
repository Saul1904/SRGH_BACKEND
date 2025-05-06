document.addEventListener("DOMContentLoaded", () => {
    cargarTurnos();
    cargarEmpleadosEnFormulario();
    document.getElementById("form-agregar").addEventListener("submit", agregarTurno);
    document.getElementById("form-editar").addEventListener("submit", actualizarTurno);
});

// ✅ Cargar turnos desde el backend
function cargarTurnos() {
    fetch("http://localhost:8080/api/turnos")
        .then(response => response.json())
        .then(data => {
            const tablaTurnos = document.getElementById("tabla-turnos");
            tablaTurnos.innerHTML = "";

            data.forEach(turno => {
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
                tablaTurnos.appendChild(fila);
            });
        })
        .catch(error => console.error("Error al cargar turnos:", error));
}

// ✅ Agregar turno
function agregarTurno(event) {
    event.preventDefault();

    const empleadoId = document.getElementById("empleado-id").value;
    const turno = {
        empleado: { id: empleadoId }, 
        fecha: document.getElementById("fecha").value.trim(),
        horarioInicio: document.getElementById("horario-inicio").value.trim(),
        horarioFin: document.getElementById("horario-fin").value.trim()
    };

    fetch("http://localhost:8080/api/turnos", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(turno)
    })
    .then(response => response.json())
    .then(() => {
        cargarTurnos();
        cerrarModal("modal-agregar");
        mostrarNotificacion("Turno agregado correctamente");
    })
    .catch(error => console.error("Error al agregar turno:", error));
}

// ✅ Editar turno
function editarTurno(id) {
    fetch(`http://localhost:8080/api/turnos/${id}`)
        .then(response => response.json())
        .then(turno => {
            document.getElementById("editar-id").value = turno.id;
            document.getElementById("editar-empleado-id").value = turno.empleado ? turno.empleado.id : "";
            document.getElementById("editar-fecha").value = turno.fecha;
            document.getElementById("editar-horario-inicio").value = turno.horarioInicio;
            document.getElementById("editar-horario-fin").value = turno.horarioFin;

            mostrarModalEditar();
        })
        .catch(error => console.error("Error al cargar turno para edición:", error));
}

// ✅ Actualizar turno
function actualizarTurno(event) {
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
    .then(response => {
        if (response.ok) {
            cargarTurnos();
            cerrarModal("modal-editar");
            mostrarNotificacion("Turno actualizado correctamente");
        } else {
            console.error("Error al actualizar turno.");
        }
    })
    .catch(error => console.error("Error:", error));
}

// ✅ Cargar empleados en el formulario
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

// ✅ Eliminar turno
function eliminarTurno(id) {
    if (!confirm("¿Estás seguro de que quieres eliminar este turno?")) return;

    fetch(`http://localhost:8080/api/turnos/${id}`, {
        method: "DELETE",
    })
    .then(response => {
        if (response.ok) {
            cargarTurnos();
            mostrarNotificacion("Turno eliminado correctamente");
        } else {
            return response.text().then(text => {
                console.error("Error al eliminar el turno:", text);
            });
        }
    })
    .catch(error => console.error("Error:", error));
}

//MODAL TURNO//
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

// ✅ Busqueda de turnos
document.getElementById("buscar-turno").addEventListener("input", filtrarTurnos);
document.getElementById("filtro-fecha").addEventListener("change", filtrarTurnos);

function filtrarTurnos() {
    const textoBusqueda = document.getElementById("buscar-turno").value.toLowerCase();
    const fechaSeleccionada = document.getElementById("filtro-fecha").value;

    const filas = document.querySelectorAll("#tabla-turnos tr");

    filas.forEach(fila => {
        const empleado = fila.children[1].textContent.toLowerCase();
        const fecha = fila.children[2].textContent.toLowerCase();
        const horarioInicio = fila.children[3].textContent.toLowerCase();
        const horarioFin = fila.children[4].textContent.toLowerCase();

        const coincideTexto = empleado.includes(textoBusqueda) || horarioInicio.includes(textoBusqueda) || horarioFin.includes(textoBusqueda);
        const coincideFecha = fechaSeleccionada === "" || fecha.includes(fechaSeleccionada);

        fila.style.display = coincideTexto && coincideFecha ? "" : "none";
    });
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