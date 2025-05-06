document.addEventListener("DOMContentLoaded", () => {
    const usuarioNombre = sessionStorage.getItem("usuarioNombre");
    console.log("Nombre recuperado:", usuarioNombre); // ✅ Depuración

    if (usuarioNombre) {
        document.getElementById("perfil-nombre").textContent = `¡Bienvenido, ${usuarioNombre}! 👋`;
    } else {
        document.getElementById("perfil-nombre").textContent = "⚠️ Nombre no encontrado";
    }
});
/*CARDS */
document.getElementById("cerrar-sesion").addEventListener("click", () => {
    sessionStorage.removeItem("usuarioNombre"); // ✅ Borrar nombre del usuario
    window.location.href = "login.html"; // ✅ Redirigir al login
});

document.addEventListener("DOMContentLoaded", () => {
    fetch("http://localhost:8080/api/dashboard")
        .then(response => response.json())
        .then(data => {
            document.getElementById("empleados-total").textContent = data.empleados;
            document.getElementById("nominas-total").textContent = data.nominas;
            document.getElementById("ausencias-total").textContent = data.ausencias;
        })
        .catch(error => console.error("Error al obtener datos del dashboard:", error));
});

document.getElementById("card-empleados").addEventListener("click", () => {
    window.location.href = "empleados.html";
});

document.getElementById("card-nominas").addEventListener("click", () => {
    window.location.href = "nominas.html";
});

document.getElementById("card-ausencias").addEventListener("click", () => {
    window.location.href = "ausencias.html";
});


// Gráfica de Ausencias por Mes
let graficaExistente = null;  // Variable global para almacenar la instancia

function cargarGrafica() {
    fetch('http://localhost:8080/api/ausencias/por-mes')
        .then(response => response.json())
        .then(data => {
            console.log('Datos recibidos:', data);

            const getNombreMes = (n) => {
                const meses = ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio',
                               'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'];
                return meses[n - 1];
            };

            const meses = data.map(item => getNombreMes(item.mes));
            const totales = data.map(item => item.total);

            const ctx = document.getElementById('graficaAusencias').getContext('2d');

            // ⚠️ Si ya existe una gráfica, destrúyela antes de crear una nueva
            if (graficaExistente) {
                graficaExistente.destroy();
            }

            graficaExistente = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: meses,
                    datasets: [{
                        label: 'Ausencias por mes',
                        data: totales,
                        backgroundColor: 'rgba(54, 162, 235, 0.5)',
                        borderColor: 'rgba(54, 162, 235, 1)',
                        borderWidth: 2
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,  // Para controlar el tamaño tú mismo si deseas
                    scales: {
                        y: {
                            beginAtZero: true
                        }
                    },
                    plugins: {
                        legend: {
                            position: 'top'
                        },
                        title: {
                            display: true,
                            text: 'Resumen de Ausencias Mensuales'
                        }
                    }
                }
            });
        })
        .catch(error => {
            console.error('Error al obtener los datos de ausencias:', error);
        });
}

window.addEventListener('DOMContentLoaded', cargarGrafica);

// =================== CARGAR ESTADO DE ÁNIMO ===================
function guardarSeleccion(seleccion) {
    let lista = document.getElementById("resultados");
    let nuevoElemento = document.createElement("li");
    nuevoElemento.textContent = seleccion;
    lista.appendChild(nuevoElemento);
}

