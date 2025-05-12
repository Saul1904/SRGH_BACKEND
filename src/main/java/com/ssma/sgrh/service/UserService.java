package com.ssma.sgrh.service;
// Define el paquete donde se encuentra esta clase.

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssma.sgrh.models.User;
import com.ssma.sgrh.repository.UserRepository;
// Importa la interfaz UserRepository para interactuar con la base de datos.

@Service
// Marca esta clase como un servicio de Spring, lo que permite que sea detectada y gestionada por el contenedor de Spring.
public class UserService {

    @Autowired
    // Inyecta automáticamente una instancia de UserRepository en esta clase.
    private UserRepository userRepository;

    public User registerUser(User user) {
        // Método para registrar un nuevo usuario en la base de datos.
        return userRepository.save(user);
        // Guarda el usuario en la base de datos y devuelve el objeto guardado.
    }

    public User loginUser(String username, String password) {
        // Método para autenticar a un usuario con su nombre de usuario y contraseña.
        return userRepository.findByUsername(username)
            // Busca un usuario por su nombre de usuario.
            .filter(user -> user.getPassword().equals(password))
            // Verifica si la contraseña proporcionada coincide con la del usuario encontrado.
            .orElseThrow(() -> new RuntimeException("⚠️ Credenciales incorrectas."));
            // Lanza una excepción si las credenciales no son correctas.
    }

    public List<User> getAllUsers() {
        // Método para obtener una lista de todos los usuarios registrados.
        return userRepository.findAll();
        // Devuelve una lista con todos los usuarios de la base de datos.
    }

    public int getTotalEmpleados() {
        // Método para obtener el total de empleados registrados.
        return userRepository.countEmpleados();
        // Llama a un método personalizado del repositorio para contar los empleados.
    }
}
