// Define el paquete al que pertenece esta clase.
package com.ssma.sgrh;

// Importa la clase SpringApplication, que se utiliza para iniciar la aplicación Spring Boot.
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Marca esta clase como una aplicación Spring Boot, habilitando la configuración automática, el escaneo de componentes y más.
@SpringBootApplication
public class SgrhApplication {

	// Método principal (entry point) que se ejecuta al iniciar la aplicación.
	public static void main(String[] args) {
		// Inicia la aplicación Spring Boot utilizando la clase SgrhApplication.
		SpringApplication.run(SgrhApplication.class, args);
	}

}
