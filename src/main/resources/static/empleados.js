// =================== INICIALIZACIÓN ===================
document.addEventListener("DOMContentLoaded", () => {
    cargarEmpleados();

    const formAgregar = document.getElementById("form-agregar");
    if (formAgregar) formAgregar.addEventListener("submit", guardarEmpleado);

    const formEditar = document.getElementById("form-editar");
    if (formEditar) formEditar.addEventListener("submit", actualizarEmpleado);

    const inputBuscar = document.getElementById("buscar-empleado");
    if (inputBuscar) inputBuscar.addEventListener("input", filtrarEmpleados);

    const filtroDepartamento = document.getElementById("filtro-departamento");
    if (filtroDepartamento) filtroDepartamento.addEventListener("change", filtrarEmpleados);
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
                    <img id="img-${empleado.id}" src="${empleado.Foto || 'img/default-user.png'}"  
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
                tarjeta.addEventListener("click", () => {
                    window.location.href = `empleado.html?id=${empleado.id}`;
                });
                // Evitar redirección al hacer clic en el input de imagen
tarjeta.querySelector(`#file-${empleado.id}`).addEventListener("click", e => e.stopPropagation());

// Evitar redirección al hacer clic en el label de imagen
tarjeta.querySelector(`label[for="file-${empleado.id}"]`).addEventListener("click", e => e.stopPropagation());

// Evitar redirección al hacer clic en el botón "Editar"
tarjeta.querySelector("button:nth-of-type(1)").addEventListener("click", e => e.stopPropagation());

// Evitar redirección al hacer clic en el botón "Eliminar"
tarjeta.querySelector("button:nth-of-type(2)").addEventListener("click", e => e.stopPropagation());


                contenedor.appendChild(tarjeta);
            });
        })
        .catch(error => console.error('Error al cargar empleados:', error));
}
// Llama la función al cargar la página
document.addEventListener('DOMContentLoaded', cargarEmpleados);



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

// =================== IMAGEN ===================
function mostrarVistaPrevia(input, id) {
    const file = input.files[0];
    if (!file) return;

    const formData = new FormData();
    formData.append("file", file);

    fetch(`http://localhost:8080/api/empleados/${id}/imagen`, {
        method: "POST",
        body: formData
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Error al subir imagen");
        }
        return response.text();
    })
    .then(msg => {
        console.log(msg);

        // Refrescar imagen al instante
        const reader = new FileReader();
        reader.onload = function(e) {
            const img = document.getElementById(`img-${id}`);
            img.src = e.target.result;
        };
        reader.readAsDataURL(file);

        // Mostrar mensaje animado
        const mensaje = document.getElementById("mensaje-exito");
        mensaje.classList.add("mostrar");
        setTimeout(() => {
            mensaje.classList.remove("mostrar");
        }, 3000); // se oculta después de 3 segundos
    })
    .catch(error => {
        console.error("Error al subir la imagen:", error);
    });
}
