document.getElementById("form-login").addEventListener("submit", (event) => {
    event.preventDefault();

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    fetch("http://localhost:8080/users/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password })
    })
    .then(response => response.json())
    .then(data => {
        if (data.error) {
            alert("⚠️ Error: " + data.error);
        } else {
            console.log("Guardando nombre:", data.nombre); // ✅ Depuración
            sessionStorage.setItem("usuarioNombre", data.nombre);
            window.location.href = "index.html"; // ✅ Redirige a la página principal
        }
    })
    .catch(error => console.error("Error al iniciar sesión:", error));
});