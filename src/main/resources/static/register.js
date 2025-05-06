document.getElementById("registerButton").addEventListener("click", async () => {
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    const response = await fetch("http://localhost:8080/users/register", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ username, password })
    });

    if (response.ok) {
        alert("Usuario registrado exitosamente");
    } else {
        alert("Error al registrar usuario");
    }
});