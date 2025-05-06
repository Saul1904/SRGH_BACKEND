document.addEventListener("DOMContentLoaded", () => {
    cargarDocumentos();
    cargarEmpleadosEnFormulario();
    document.getElementById("form-agregar").addEventListener("submit", agregarDocumento);
    document.getElementById("form-editar").addEventListener("submit", actualizarDocumento);
});



function cargarEmpleadosEnFormulario() {
    fetch("http://localhost:8080/api/empleados")
        .then(response => response.json())
        .then(data => {
            const selectAgregar = document.getElementById("empleado-id");
            const selectEditar = document.getElementById("editar-empleado-id");

            if (!selectAgregar || !selectEditar) {
                console.error("Error: No se encontraron los elementos 'empleado-id' o 'editar-empleado-id'.");
                return;
            }

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

function cargarDocumentos() {
    fetch("http://localhost:8080/api/documentos")
        .then(response => response.json())
        .then(data => {
            console.log("Documentos obtenidos después del POST:", data); // ✅ Verifica en consola

            const tablaDocumentos = document.getElementById("tabla-documentos");
            tablaDocumentos.innerHTML = "";

            if (!data || data.length === 0) {
                console.warn("⚠️ No hay documentos registrados en la API.");
                tablaDocumentos.innerHTML = "<tr><td colspan='5'>No hay documentos disponibles.</td></tr>";
                return;
            }

            data.forEach(documento => {
                const empleadoNombre = documento.empleado 
                    ? `${documento.empleado.nombre} ${documento.empleado.apellido}` 
                    : "No asignado";

                const fechaSubida = documento.fechaSubida 
                    ? new Date(documento.fechaSubida).toLocaleDateString("es-MX") 
                    : "Sin definir";

                const fila = document.createElement("tr");
                fila.innerHTML = `
                    <td>${documento.id}</td>
                    <td>${empleadoNombre}</td>
                    <td>${documento.tipoDocumento}</td>
                    <td>${fechaSubida}</td>
                    <td>
                        <button onclick="editarDocumento(${documento.id})">✏️ Editar</button>
                        <button onclick="eliminarDocumento(${documento.id})">🗑️ Eliminar</button>
                    </td>
                `;
                tablaDocumentos.appendChild(fila);
            });
        })
        .catch(error => console.error("Error al cargar documentos:", error));
}



// ✅ Agregar documento
function agregarDocumento(event) {
    event.preventDefault(); // ✅ Evita que el formulario recargue la página

    const formData = new FormData();
    formData.append("empleadoId", document.getElementById("empleado-id").value);
    formData.append("tipoDocumento", document.getElementById("tipo-documento").value.trim());
    formData.append("fechaSubida", document.getElementById("fecha-subida").value.trim());
    formData.append("archivo", document.getElementById("archivo").files[0]);

    fetch("http://localhost:8080/api/documentos", {
        method: "POST",
        body: formData
    })
    .then(() => {
        console.log("Documento subido, recargando lista..."); // ✅ Confirmación en consola
        cargarDocumentos(); // ✅ Recargar la tabla de documentos
        cerrarModal("modal-agregar"); // ✅ Cerrar modal
        mostrarNotificacion("Documento agregado correctamente");
    })
    .catch(error => console.error("Error al agregar documento:", error));
}



// ✅ Editar documento
function editarDocumento(id) {
    fetch(`http://localhost:8080/api/documentos/${id}`)
        .then(response => response.json())
        .then(documento => {
            document.getElementById("editar-id").value = documento.id;
            document.getElementById("editar-empleado-id").value = documento.empleado ? documento.empleado.id : "";
            document.getElementById("editar-tipo-documento").value = documento.tipoDocumento;
            document.getElementById("editar-fecha-subida").value = documento.fechaSubida;

            mostrarModalEditar();
        })
        .catch(error => console.error("Error al cargar documento para edición:", error));
}

// ✅ Actualizar documento
function actualizarDocumento(event) {
    event.preventDefault();

    const id = document.getElementById("editar-id").value;
    const documento = {
        empleado: { id: document.getElementById("editar-empleado-id").value },
        tipoDocumento: document.getElementById("editar-tipo-documento").value.trim(),
        fechaSubida: document.getElementById("editar-fecha-subida").value.trim()
    };

    fetch(`http://localhost:8080/api/documentos/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(documento)
    })
    .then(response => {
        if (response.ok) {
            cargarDocumentos();
            cerrarModal("modal-editar");
            mostrarNotificacion("Documento actualizado correctamente");
        } else {
            console.error("Error al actualizar documento.");
        }
    })
    .catch(error => console.error("Error:", error));
}

// ✅ Eliminar documento
function eliminarDocumento(id) {
    if (!confirm("¿Estás seguro de que quieres eliminar este documento?")) return;

    fetch(`http://localhost:8080/api/documentos/${id}`, {
        method: "DELETE",
    })
    .then(response => {
        if (response.ok) {
            cargarDocumentos();
            mostrarNotificacion("Documento eliminado correctamente");
        } else {
            return response.text().then(text => {
                console.error("Error al eliminar el documento:", text);
            });
        }
    })
    .catch(error => console.error("Error:", error));
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

// ✅ Busqueda de documentos
document.getElementById("buscar-documento").addEventListener("input", filtrarDocumentos);
document.getElementById("filtro-documento").addEventListener("change", filtrarDocumentos);

function filtrarDocumentos() {
    const textoBusqueda = document.getElementById("buscar-documento").value.toLowerCase();
    const tipoSeleccionado = document.getElementById("filtro-documento").value;

    const filas = document.querySelectorAll("#tabla-documentos tr");

    filas.forEach(fila => {
        const empleado = fila.children[1].textContent.toLowerCase();
        const tipoDocumento = fila.children[2].textContent;
        const fechaSubida = fila.children[3].textContent.toLowerCase();

        const coincideTexto = empleado.includes(textoBusqueda) || fechaSubida.includes(textoBusqueda);
        const coincideTipo = tipoSeleccionado === "" || tipoDocumento === tipoSeleccionado;

        fila.style.display = coincideTexto && coincideTipo ? "" : "none";
    });
}

//MODAL PARA DOCUMENTOS
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