package com.ssma.sgrh.controller;

// Importa las clases e interfaces necesarias
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssma.sgrh.models.User;
import com.ssma.sgrh.service.UserService;

// Marca esta clase como un controlador REST, lo que significa que manejará solicitudes HTTP
@RestController
// Especifica la URL base para todos los endpoints de este controlador
@RequestMapping("/users")
public class UserController {
    // Inyecta automáticamente una instancia de UserService en esta clase
    @Autowired
    private UserService userService;

    // Maneja solicitudes HTTP POST a "/users/register" para registrar usuarios
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        // Llama al servicio para registrar al usuario y devuelve una respuesta HTTP
        User registeredUser = userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }

    // Maneja solicitudes HTTP POST a "/users/login" para iniciar sesión
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        try {
            // Obtiene el nombre de usuario y la contraseña del cuerpo de la solicitud
            String username = credentials.get("username");
            String password = credentials.get("password");

            // Llama al servicio para autenticar al usuario
            User user = userService.loginUser(username, password);
            // Devuelve una respuesta exitosa con un mensaje y el nombre de usuario
            return ResponseEntity.ok(Map.of("mensaje", "Inicio de sesión exitoso", "nombre", user.getUsername()));

        } catch (RuntimeException e) {
            // Devuelve una respuesta de error si ocurre una excepción
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
        }
    }

    // Maneja solicitudes HTTP GET a "/users/all" para obtener todos los usuarios
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        // Llama al servicio para obtener la lista de usuarios
        List<User> users = userService.getAllUsers();
        // Devuelve la lista de usuarios en la respuesta
        return ResponseEntity.ok(users);
    }

    // Maneja solicitudes HTTP GET a "/users/api/dashboard" para obtener estadísticas del dashboard
    @GetMapping("/api/dashboard")
    public ResponseEntity<Map<String, Integer>> obtenerDashboard() {
        // Crea un mapa con estadísticas, como el total de empleados
        Map<String, Integer> estadisticas = Map.of(
            "empleados", userService.getTotalEmpleados()
        );
        // Devuelve las estadísticas en la respuesta
        return ResponseEntity.ok(estadisticas);
    }
}
