package com.ssma.sgrh.controller;


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



@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        User registeredUser = userService.registerUser(user);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
    try {
        String username = credentials.get("username");
        String password = credentials.get("password");

        User user = userService.loginUser(username, password);
        return ResponseEntity.ok(Map.of("mensaje", "Inicio de sesión exitoso", "nombre", user.getUsername()));

    } catch (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
    }
}

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
    List<User> users = userService.getAllUsers();
    return ResponseEntity.ok(users);
}

@GetMapping("/api/dashboard")
public ResponseEntity<Map<String, Integer>> obtenerDashboard() {
    Map<String, Integer> estadisticas = Map.of(
        "empleados", userService.getTotalEmpleados()
    );
    return ResponseEntity.ok(estadisticas);
}

}

