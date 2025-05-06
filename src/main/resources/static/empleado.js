document.addEventListener("DOMContentLoaded", () => {
    const params = new URLSearchParams(window.location.search);
    const id = params.get("id");

    // Validación mejorada del ID
    if (!id || isNaN(id)) { 
        alert("❌ ID de empleado inválido o no encontrado en la URL.");
        return;
    }

    console.log("✅ ID de empleado detectado:", id);

    // Llamar a la función para cargar los datos del empleado
    cargarDatosEmpleado(id);
});

// Función para cargar datos del empleado
function cargarDatosEmpleado(idEmpleado) {
    if (!idEmpleado) {
        console.error("❌ No se ha proporcionado un ID de empleado válido.");
        return;
    }

    // Cargar datos del empleado
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
            document.getElementById('fecha').textContent = empleado.fechaContratacion || "No disponible";
        })
        .catch(error => {
            console.error("❌ Error al cargar los datos del empleado:", error);
        });

    // Cargar documentos
    fetch(`http://localhost:8080/api/documentos/empleado/${idEmpleado}`)
        .then(res => res.json())
        .then(documentos => {
            const tabla = document.getElementById('tabla-documentos');
            tabla.innerHTML = ''; // Limpiar tabla antes de agregar nuevos datos
            documentos.forEach(doc => {
                tabla.innerHTML += `
                    <tr>
                        <td>${doc.nombreDocumento || "Sin nombre"}</td>
                        <td>${doc.tipoDocumento || "No definido"}</td>
                        <td>${doc.fechaSubida || "Sin fecha"}</td>
                        <td>${doc.estado || "Desconocido"}</td>
                        <td><a href="${doc.urlDocumento}" target="_blank">Ver PDF</a></td>
                    </tr>
                `;
            });
        })
        .catch(error => {
            console.error("❌ Error al cargar documentos:", error);
        });
}

// Función para subir documentos
document.getElementById('form-subir-doc').addEventListener('submit', e => {
    e.preventDefault();
    const form = e.target;
    const formData = new FormData();
    const params = new URLSearchParams(window.location.search);
    const id = params.get("id");

    if (!id) {
        alert("❌ No se encontró el ID del empleado.");
        return;
    }

    formData.append('id_empleado', id);
    formData.append('nombre_documento', form.nombre.value);
    formData.append('tipo_documento', form.tipo.value);
    formData.append('fecha_subida', new Date().toISOString().split('T')[0]); 
    formData.append('archivo', form.archivo.files[0]);

    fetch('http://localhost:8080/api/documentos', {
        method: 'POST',
        body: formData
    })
    .then(res => {
        if (res.ok) {
            alert('✅ Documento subido correctamente.');
            form.reset();
            location.reload();
        } else {
            alert('❌ Error al subir el documento.');
        }
    })
    .catch(error => {
        console.error("❌ Error al subir el documento:", error);
    });
});

// Función para habilitar el modal
function habilitarEdicion() {
    document.getElementById('modal-editar').style.display = 'flex';
}

function cerrarModal() {
    document.getElementById('modal-editar').style.display = 'none';
}

function mostrarTab(tabId) {
    document.querySelectorAll('.tab').forEach(tab => tab.classList.remove('active'));
    document.querySelectorAll('.contenido-tab').forEach(tab => tab.classList.remove('active'));

    const tabSeleccionado = document.querySelector(`.tab[data-tab="${tabId}"]`);
    if (tabSeleccionado) {
        tabSeleccionado.classList.add('active');
        document.getElementById(tabId).classList.add('active');
    } else {
        console.warn("Tab no encontrado:", tabId);
    }
}
