document.addEventListener("DOMContentLoaded", () => {
    cargarInformes();
    document.getElementById("form-agregar").addEventListener("submit", agregarInforme);
    document.getElementById("form-editar").addEventListener("submit", actualizarInforme);
});

// ✅ Cargar informes
function cargarInformes() {
    fetch("http://localhost:8080/api/informes")
        .then(response => response.json())
        .then(data => {
            const tabla = document.getElementById("tabla-informes");
            tabla.innerHTML = "";

            data.forEach(informe => {
                const fecha = informe.fechaGeneracion
                    ? new Date(informe.fechaGeneracion).toLocaleString("es-MX")
                    : "Sin fecha";

                const fila = document.createElement("tr");
                fila.innerHTML = `
                    <td>${informe.id}</td>
                    <td>${informe.tipoInforme}</td>
                    <td>${fecha}</td>
                    <td>
                        <button onclick="editarInforme(${informe.id})">
                            <i class="fa-solid fa-pen"></i> Editar
                        </button>
                        <button onclick="eliminarInforme(${informe.id})">
                            <i class="fa-solid fa-trash"></i> Eliminar
                        </button>
                    </td>
                `;
                tabla.appendChild(fila);
            });
        })
        .catch(error => console.error("Error al cargar informes:", error));
}

// ✅ Agregar informe
function agregarInforme(event) {
    event.preventDefault();

    const informe = {
        tipoInforme: document.getElementById("tipo-informe").value.trim(),
        fechaGeneracion: document.getElementById("fecha-generacion").value
    };

    fetch("http://localhost:8080/api/informes", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(informe)
    })
    .then(response => response.json())
    .then(() => {
        cargarInformes();
        cerrarModal("modal-agregar");
        mostrarNotificacion("Informe agregado correctamente");
    })
    .catch(error => console.error("Error al agregar informe:", error));
}

// ✅ Editar informe
function editarInforme(id) {
    fetch(`http://localhost:8080/api/informes/${id}`)
        .then(response => response.json())
        .then(informe => {
            document.getElementById("editar-id").value = informe.id;
            document.getElementById("editar-tipo-informe").value = informe.tipoInforme || "";
            document.getElementById("editar-fecha-generacion").value = informe.fechaGeneracion 
                ? new Date(informe.fechaGeneracion).toISOString().slice(0, 16) 
                : "";

            mostrarModalEditar();
        })
        .catch(error => console.error("Error al cargar informe para edición:", error));
}

// ✅ Actualizar informe
function actualizarInforme(event) {
    event.preventDefault();

    const id = document.getElementById("editar-id").value;
    const informe = {
        tipoInforme: document.getElementById("editar-tipo-informe").value.trim(),
        fechaGeneracion: document.getElementById("editar-fecha-generacion").value
    };

    fetch(`http://localhost:8080/api/informes/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(informe)
    })
    .then(response => {
        if (response.ok) {
            cargarInformes();
            cerrarModal("modal-editar");
            mostrarNotificacion("Informe actualizado correctamente");
        } else {
            console.error("Error al actualizar informe.");
        }
    })
    .catch(error => console.error("Error:", error));
}

// ✅ Eliminar informe
function eliminarInforme(id) {
    if (!confirm("¿Estás seguro de que quieres eliminar este informe?")) return;

    fetch(`http://localhost:8080/api/informes/${id}`, {
        method: "DELETE"
    })
    .then(response => {
        if (response.ok) {
            cargarInformes();
            mostrarNotificacion("Eliminado correctamente");
        } else {
            return response.text().then(text => {
                console.error("Error al eliminar el informe:", text);
            });
        }
    })
    .catch(error => console.error("Error:", error));
}

// ✅ Modal manejo (reutilizado)
function mostrarModalEditar() {
    const modal = document.getElementById("modal-editar");
    modal.style.display = "flex";
    setTimeout(() => modal.classList.add("mostrar"), 10);
}

function mostrarModalAgregar() {
    const modal = document.getElementById("modal-agregar");
    modal.style.display = "flex";
    setTimeout(() => modal.classList.add("mostrar"), 10);
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


// BUSQUEDA INFORMES
document.getElementById("buscar-informe").addEventListener("input", filtrarInformes);
document.getElementById("filtro-tipo-informe").addEventListener("change", filtrarInformes);

function filtrarInformes() {
    const textoBusqueda = document.getElementById("buscar-informe").value.toLowerCase();
    const tipoSeleccionado = document.getElementById("filtro-tipo-informe").value;

    const filas = document.querySelectorAll("#tabla-informes tr");

    filas.forEach(fila => {
        const tipoInforme = fila.children[1].textContent.toLowerCase(); // suponiendo que el tipo está en la columna 2
        const fechaGeneracion = fila.children[2].textContent.toLowerCase(); // y la fecha en la columna 3

        const coincideTexto = tipoInforme.includes(textoBusqueda) || fechaGeneracion.includes(textoBusqueda);
        const coincideTipo = tipoSeleccionado === "" || tipoInforme === tipoSeleccionado.toLowerCase();

        fila.style.display = coincideTexto && coincideTipo ? "" : "none";
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