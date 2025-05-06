// =================== INICIALIZACIÓN ===================
document.addEventListener("DOMContentLoaded", () => {
    cargarEmpleados();
    document.getElementById("form-agregar").addEventListener("submit", guardarEmpleado);
    document.getElementById("form-editar").addEventListener("submit", actualizarEmpleado);
    document.getElementById("buscar-empleado").addEventListener("input", filtrarEmpleados);
    document.getElementById("filtro-departamento").addEventListener("change", filtrarEmpleados);
});


// =================== CARGAR EMPLEADOS ===================
function cargarEmpleados() {
    fetch('http://localhost:8080/api/empleados')
        .then(response => response.json())
        .then(empleados => {
            const contenedor = document.getElementById('tarjetas-empleados');
            contenedor.innerHTML = ''; // Limpia el contenedor

            empleados.forEach(empleado => {
                const tarjeta = document.createElement('div');
                tarjeta.classList.add('tarjeta-empleado');

                tarjeta.innerHTML = `
                    <img id="img-${empleado.id}" src="${empleado.imagenUrl || 'img/default-user.png'}"  
                    alt="Foto de ${empleado.nombre} ${empleado.apellido}" class="imagen-empleado">

                    <h3>${empleado.nombre}</h3>
                    <h3>${empleado.apellido}</h3>
                    <p><strong>📧Email:</strong> ${empleado.email}</p>
                    <p><strong>📱Teléfono:</strong> ${empleado.telefono}</p>
                    <p><strong>💰Sueldo:</strong> $${empleado.sueldo}</p>
                    <p><strong>🏢Departamento:</strong> ${empleado.departamento}</p>
                    <p><strong>📅 Fecha de Ingreso:</strong> ${empleado.fechaContratacion}</p>

                    <div class="imagen-uploader">
                        <label for="file-${empleado.id}" class="btn-subir-imagen">
                            📷 Agregar imagen
                        </label>
                        <input type="file" id="file-${empleado.id}" accept="image/*" style="display: none;" onchange="mostrarVistaPrevia(this, ${empleado.id})">
                    </div>

                    <div class="acciones">
                        <button onclick="editarEmpleado(${empleado.id})">Editar</button>
                        <button onclick="eliminarEmpleado(${empleado.id})" class="btn-eliminar">Eliminar</button>
                    </div>
                `;

                // 🔹 Agregar el evento para redirigir al hacer clic en la tarjeta
                tarjeta.onclick = () => {
                    window.location.href = `empleado.html?id=${empleado.id}`;
                };

                contenedor.appendChild(tarjeta);
            });
        })
        .catch(error => console.error('Error al cargar empleados:', error));
}
// Llama la función al cargar la página
document.addEventListener('DOMContentLoaded', cargarEmpleados);

// =================== MOSTRAR VISTA PREVIA DE IMAGEN ===================
document.addEventListener("DOMContentLoaded", () => {
    document.querySelectorAll("img").forEach(img => {
        const empleadoId = img.id.split("-")[1]; // Asumiendo que el ID es "img-123"
        const imagenGuardada = localStorage.getItem(`img-${empleadoId}`);
        if (imagenGuardada) {
            img.src = imagenGuardada;
        }
    });
});

function mostrarVistaPrevia(input, empleadoId) {
    const archivo = input.files[0];
    if (!archivo) return;

    const lector = new FileReader();
    lector.onload = function (e) {
        const imagen = document.querySelector(`#img-${empleadoId}`);
        if (imagen) {
            imagen.src = e.target.result;

            // Guarda la vista previa temporal en localStorage
            localStorage.setItem(`img-${empleadoId}`, e.target.result);
        }
    };
    lector.readAsDataURL(archivo);
}

// =================== SUBIR IMAGEN ===================
const subirImagen = async (empleadoId, archivo) => {
    const formData = new FormData();
    formData.append("imagen", archivo);

    try {
        const response = await fetch(`/api/empleados/${empleadoId}/imagen`, {
            method: "POST",
            body: formData,
        });

        if (response.ok) {
            const data = await response.json(); // Suponiendo que el servidor responde con la URL de la imagen
            const imagen = document.querySelector(`#img-${empleadoId}`);
            if (imagen) {
                imagen.src = data.url; // Asigna la nueva URL obtenida del servidor
            }
            console.log("Imagen subida con éxito.");
        } else {
            console.log("Error al subir la imagen.");
        }
    } catch (error) {
        console.error("Error:", error);
    }
};


// =================== GUARDAR EMPLEADO ===================
function guardarEmpleado(event) {
    event.preventDefault();

    const empleado = {
        nombre: document.getElementById("nombre").value,
        apellido: document.getElementById("apellido").value,
        email: document.getElementById("email").value,
        telefono: document.getElementById("telefono").value,
        sueldo: parseFloat(document.getElementById("sueldo").value),
        fechaContratacion: document.getElementById("fecha_contratacion").value,
        departamento: document.getElementById("departamento").value
    };

    fetch("http://localhost:8080/api/empleados", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(empleado)
    })
    .then(response => {
        if (response.ok) {
            cargarEmpleados();
            cerrarModal("modal-agregar");
            mostrarNotificacion("Empleado agregado correctamente");
        } else {
            console.error("Error al guardar empleado.");
        }
    })
    .catch(error => console.error("Error:", error));
}

// =================== EDITAR EMPLEADO ===================
function editarEmpleado(id) {
    fetch(`http://localhost:8080/api/empleados/${id}`)
        .then(response => response.json())
        .then(empleado => {
            document.getElementById("editar-id").value = id;
            document.getElementById("editar-nombre").value = empleado.nombre;
            document.getElementById("editar-apellido").value = empleado.apellido;
            document.getElementById("editar-email").value = empleado.email;
            document.getElementById("editar-telefono").value = empleado.telefono;
            document.getElementById("editar-sueldo").value = empleado.sueldo;
            document.getElementById("editar-fecha_contratacion").value = empleado.fechaContratacion;
            document.getElementById("editar-departamento").value = empleado.departamento;

            mostrarModal("modal-editar");
        })
        .catch(error => console.error("Error al obtener empleado:", error));
}

function actualizarEmpleado(event) {
    event.preventDefault();

    const id = document.getElementById("editar-id").value;
    const empleado = {
        nombre: document.getElementById("editar-nombre").value,
        apellido: document.getElementById("editar-apellido").value,
        email: document.getElementById("editar-email").value,
        telefono: document.getElementById("editar-telefono").value,
        sueldo: parseFloat(document.getElementById("editar-sueldo").value),
        fechaContratacion: document.getElementById("editar-fecha_contratacion").value,
        departamento: document.getElementById("editar-departamento").value
    };

    fetch(`http://localhost:8080/api/empleados/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(empleado)
    })
    .then(response => {
        if (response.ok) {
            cargarEmpleados();
            cerrarModal("modal-editar");
            mostrarNotificacion("Empleado actualizado correctamente");
        } else {
            console.error("Error al actualizar empleado.");
        }
    })
    .catch(error => console.error("Error:", error));
}

// =================== ELIMINAR EMPLEADO ===================
function eliminarEmpleado(id) {
    if (confirm("¿Seguro que quieres eliminar este empleado?")) {
        fetch(`http://localhost:8080/api/empleados/${id}`, {
            method: "DELETE"
        })
        .then(response => {
            if (response.ok) {
                cargarEmpleados();
                mostrarNotificacion("Empleado eliminado");
            } else {
                console.error("Error al eliminar empleado.");
            }
        })
        .catch(error => console.error("Error:", error));
    }
}

// =================== MODALES ===================
function mostrarModal(id) {
    document.getElementById(id).style.display = "flex";
}

function cerrarModal(id) {
    document.getElementById(id).style.display = "none";
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

// =================== BUSCADOR Y FILTRO ===================
function filtrarEmpleados() {
    const textoBusqueda = document.getElementById("buscar-empleado").value.toLowerCase();
    const departamento = document.getElementById("filtro-departamento").value;

    const filas = document.querySelectorAll("#tabla-empleados tr");

    filas.forEach(fila => {
        const nombre = fila.children[1].textContent.toLowerCase();
        const email = fila.children[2].textContent.toLowerCase();
        const depto = fila.children[6].textContent;

        const coincideTexto = nombre.includes(textoBusqueda) || email.includes(textoBusqueda);
        const coincideDepto = departamento === "" || depto === departamento;

        fila.style.display = (coincideTexto && coincideDepto) ? "" : "none";
    });
}




