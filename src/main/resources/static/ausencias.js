// =================== INICIALIZACIÓN ===================
document.addEventListener("DOMContentLoaded", () => {
    cargarAusencias();
    cargarEmpleadosEnFormulario();

    document.getElementById("form-agregar").addEventListener("submit", agregarAusencia);
    document.getElementById("form-editar").addEventListener("submit", actualizarAusencia);
    document.getElementById("buscar-ausencia").addEventListener("input", filtrarAusencias);
    document.getElementById("filtro-tipo").addEventListener("change", filtrarAusencias);
});


// =======================
// 🔹 FUNCIONES PRINCIPALES
// =======================

// 🔄 Cargar ausencias en tabla
function cargarAusencias() {
    fetch("http://localhost:8080/api/ausencias")
        .then(res => res.json())
        .then(data => {
            const tabla = document.getElementById("tabla-ausencias");
            tabla.innerHTML = "";

            data.forEach(a => {
                const fila = document.createElement("tr");
                fila.innerHTML = `
                    <td>${a.id}</td>
                    <td>${a.empleado ? `${a.empleado.nombre} ${a.empleado.apellido}` : "No asignado"}</td>
                    <td>${a.fechaInicio ? new Date(a.fechaInicio).toLocaleDateString("es-MX") : "Sin definir"}</td>
                    <td>${a.fechaFin ? new Date(a.fechaFin).toLocaleDateString("es-MX") : "Sin definir"}</td>
                    <td>${a.tipoAusencia || "Sin definir"}</td>
                    <td>
                        <button onclick="editarAusencia(${a.id})"><i class="fa-solid fa-pen"></i> Editar</button>
                        <button onclick="eliminarAusencia(${a.id})"><i class="fa-solid fa-trash"></i> Eliminar</button>
                    </td>
                `;
                tabla.appendChild(fila);
            });
        })
        .catch(err => console.error("Error al cargar ausencias:", err));
}

// ➕ Agregar nueva ausencia
function agregarAusencia(e) {
    e.preventDefault();

    const data = {
        empleado: { id: document.getElementById("empleado-id").value },
        fechaInicio: document.getElementById("fecha-inicio").value.trim(),
        fechaFin: document.getElementById("fecha-fin").value.trim(),
        tipoAusencia: document.getElementById("tipo-ausencia").value.trim()
    };

    fetch("http://localhost:8080/api/ausencias", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data)
    })
    .then(res => res.json())
    .then(() => {
        cargarAusencias();
        cerrarModal("modal-agregar");
        mostrarNotificacion("Ausencia agregado correctamente");
    })
    .catch(err => console.error("Error al agregar ausencia:", err));
}

// ✏️ Editar ausencia
function editarAusencia(id) {
    fetch(`http://localhost:8080/api/ausencias/${id}`)
        .then(res => res.json())
        .then(a => {
            document.getElementById("editar-id").value = a.id;
            document.getElementById("editar-empleado-id").value = a.empleado ? a.empleado.id : "";
            document.getElementById("editar-fecha-inicio").value = a.fechaInicio || "";
            document.getElementById("editar-fecha-fin").value = a.fechaFin || "";
            document.getElementById("editar-tipo-ausencia").value = a.tipoAusencia || "";

            mostrarModalEditar();
        })
        .catch(err => console.error("Error al cargar ausencia:", err));
}

// ✅ Actualizar ausencia
function actualizarAusencia(e) {
    e.preventDefault();

    const id = document.getElementById("editar-id").value;
    const data = {
        empleado: { id: document.getElementById("editar-empleado-id").value },
        fechaInicio: document.getElementById("editar-fecha-inicio").value.trim() || null,
        fechaFin: document.getElementById("editar-fecha-fin").value.trim() || null,
        tipoAusencia: document.getElementById("editar-tipo-ausencia").value.trim() || "Sin definir"
    };

    fetch(`http://localhost:8080/api/ausencias/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data)
    })
    .then(res => {
        if (res.ok) {
            cargarAusencias();
            cerrarModal("modal-editar");
            mostrarNotificacion("Ausencia actualizado correctamente");
        } else {
            console.error("Error al actualizar ausencia.");
        }
    })
    .catch(err => console.error("Error:", err));
}

// ❌ Eliminar ausencia
function eliminarAusencia(id) {
    if (!confirm("¿Estás seguro de que quieres eliminar esta ausencia?")) return;

    fetch(`http://localhost:8080/api/ausencias/${id}`, { method: "DELETE" })
        .then(res => {
            if (res.ok) {
                cargarAusencias();
                mostrarNotificacion("Ausencia eliminado");
            } else {
                res.text().then(text => console.error("Error al eliminar la ausencia:", text));
            }
        })
        .catch(err => console.error("Error:", err));
}


// =======================
// 🔹 MODALES
// =======================

function mostrarModalAgregar() {
    const modal = document.getElementById("modal-agregar");
    modal.style.display = "flex";
    setTimeout(() => modal.classList.add("mostrar"), 10);
}

function mostrarModalEditar() {
    const modal = document.getElementById("modal-editar");
    modal.style.display = "flex";
    setTimeout(() => modal.classList.add("mostrar"), 10);
}

function cerrarModal(idModal) {
    const modal = document.getElementById(idModal);
    modal.classList.remove("mostrar");
    setTimeout(() => modal.style.display = "none", 300);
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

// =======================
// 🔹 CARGA DE EMPLEADOS
// =======================

function cargarEmpleadosEnFormulario() {
    fetch("http://localhost:8080/api/empleados")
        .then(res => res.json())
        .then(empleados => {
            const selectAgregar = document.getElementById("empleado-id");
            const selectEditar = document.getElementById("editar-empleado-id");

            selectAgregar.innerHTML = "";
            selectEditar.innerHTML = "";

            empleados.forEach(e => {
                const option = `<option value="${e.id}">${e.nombre} ${e.apellido}</option>`;
                selectAgregar.innerHTML += option;
                selectEditar.innerHTML += option;
            });
        })
        .catch(err => console.error("Error al cargar empleados:", err));
}




// =======================
// 🔹 FILTRADO
// =======================

function filtrarAusencias() {
    const texto = document.getElementById("buscar-ausencia").value.toLowerCase();
    const tipo = document.getElementById("filtro-tipo").value;
    const filas = document.querySelectorAll("#tabla-ausencias tr");

    filas.forEach(fila => {
        const empleado = fila.children[1].textContent.toLowerCase();
        const fechaInicio = fila.children[2].textContent.toLowerCase();
        const fechaFin = fila.children[3].textContent.toLowerCase();
        const tipoActual = fila.children[4].textContent;

        const coincideTexto = empleado.includes(texto) || fechaInicio.includes(texto) || fechaFin.includes(texto);
        const coincideTipo = tipo === "" || tipo === tipoActual;

        fila.style.display = coincideTexto && coincideTipo ? "" : "none";
    });
}
