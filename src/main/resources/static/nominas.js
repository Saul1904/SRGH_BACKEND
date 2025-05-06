document.addEventListener("DOMContentLoaded", () => {
    cargarNominas();
    cargarEmpleadosEnFormulario();
    document.getElementById("form-agregar").addEventListener("submit", agregarNomina);
    document.getElementById("form-editar").addEventListener("submit", actualizarNomina);
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

// ✅ Cargar nóminas desde el backend
function cargarNominas() {
    fetch("http://localhost:8080/api/nominas")
        .then(response => response.json())
        .then(data => {
            const tablaNominas = document.getElementById("tabla-nominas");
            tablaNominas.innerHTML = "";

            data.forEach(nomina => {
                const empleadoNombre = nomina.empleado 
                    ? `${nomina.empleado.nombre} ${nomina.empleado.apellido}` 
                    : "No asignado";

                const fechaPago = nomina.fechaPago ? new Date(nomina.fechaPago).toLocaleDateString("es-MX") : "Sin definir";

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
                tablaNominas.appendChild(fila);
            });
        })
        .catch(error => console.error("Error al cargar nóminas:", error));
}

// ✅ Agregar nómina
function agregarNomina(event) {
    event.preventDefault();

    const empleadoId = document.getElementById("empleado-id").value;
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
        cargarNominas();
        cerrarModal("modal-agregar");
        mostrarNotificacion("Nomina agregado correctamente");
    })
    .catch(error => console.error("Error al agregar nómina:", error));
}

// ✅ Editar nómina
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

            mostrarModalEditar();
        })
        .catch(error => console.error("Error al cargar nómina para edición:", error));
}

// ✅ Actualizar nómina
function actualizarNomina(event) {
    event.preventDefault();

    const id = document.getElementById("editar-id").value;
    const nomina = {
        empleado: { id: document.getElementById("editar-empleado-id").value },
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
    .then(response => {
        if (response.ok) {
            cargarNominas();
            cerrarModal("modal-editar");
            mostrarNotificacion("Nomina actualizado correctamente");
        } else {
            console.error("Error al actualizar nómina.");
        }
    })
    .catch(error => console.error("Error:", error));
}

// ✅ Eliminar nómina
function eliminarNomina(id) {
    if (!confirm("¿Estás seguro de que quieres eliminar esta nómina?")) return;

    fetch(`http://localhost:8080/api/nominas/${id}`, {
        method: "DELETE",
    })
    .then(response => {
        if (response.ok) {
            cargarNominas();
            mostrarNotificacion("Nomina eliminado correctamente");
        } else {
            return response.text().then(text => {
                console.error("Error al eliminar la nómina:", text);
            });
        }
    })
    .catch(error => console.error("Error:", error));
}

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

//BUSQUEDA NOMINAS
// ✅ Busqueda de nóminas
document.getElementById("buscar-nomina").addEventListener("input", filtrarNominas);
document.getElementById("filtro-fecha").addEventListener("change", filtrarNominas);

function filtrarNominas() {
    const textoBusqueda = document.getElementById("buscar-nomina").value.toLowerCase();
    const fechaSeleccionada = document.getElementById("filtro-fecha").value;

    const filas = document.querySelectorAll("#tabla-nominas tr");

    filas.forEach(fila => {
        const empleado = fila.children[1].textContent.toLowerCase();
        const fechaPago = fila.children[2].textContent.toLowerCase();
        const sueldoBase = fila.children[3].textContent.toLowerCase();
        const pagoNeto = fila.children[5].textContent.toLowerCase();

        const coincideTexto = empleado.includes(textoBusqueda) || sueldoBase.includes(textoBusqueda) || pagoNeto.includes(textoBusqueda);
        const coincideFecha = fechaSeleccionada === "" || fechaPago.includes(fechaSeleccionada);

        fila.style.display = coincideTexto && coincideFecha ? "" : "none";
    });
}

