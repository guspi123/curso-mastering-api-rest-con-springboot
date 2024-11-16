package com.codmind.orderapi.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.codmind.orderapi.converters.UserConverter;
import com.codmind.orderapi.dtos.LoginRequestDTO;
import com.codmind.orderapi.dtos.LoginResponseDTO;
import com.codmind.orderapi.dtos.SignupRequestDTO;
import com.codmind.orderapi.dtos.UserDTO;
import com.codmind.orderapi.entity.User;
import com.codmind.orderapi.services.EmailService;
import com.codmind.orderapi.services.UserService;
import com.codmind.orderapi.utils.WrapperResponse;

@RestController
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserConverter userConverter;

    @Autowired
    private EmailService emailService;

    // Habilitar CORS globalmente para todas las solicitudes
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value="/signup")
    public ResponseEntity<WrapperResponse<UserDTO>> signup(@RequestBody SignupRequestDTO request){
        User user = userService.createUser(userConverter.signup(request));
        return new WrapperResponse<>(true, "success", userConverter.fromEntity(user))
                .createResponse();
    }
    
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value="/login")
    public ResponseEntity<WrapperResponse<LoginResponseDTO>> login(@RequestBody LoginRequestDTO request){
        LoginResponseDTO response =  userService.login(request);
        return new WrapperResponse<>(true, "success", response).createResponse();
    }

@CrossOrigin(origins = "http://localhost:4200")
@PostMapping("/forgot-password")
public ResponseEntity<Map<String, String>> forgotPassword(@RequestBody Map<String, String> request) {
    String email = request.get("email");

    // Validar el correo electrónico
    if (email == null || email.isEmpty()) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "La dirección de correo electrónico es obligatoria");
        return ResponseEntity.badRequest().body(response);
    }

    // Mostrar el email para depuración (puedes quitarlo en producción)
    System.out.println("Solicitud de restablecimiento de contraseña para el email: " + email);

    // Generar el token para restablecer la contraseña
    String token = userService.generateResetToken(email);

    // Manejo de error si el token no pudo ser generado
    if (token == null) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "No se pudo generar el token de restablecimiento para el correo proporcionado");
        return ResponseEntity.badRequest().body(response);
    }

    // Enviar el correo de restablecimiento de contraseña
    emailService.sendPasswordResetEmail(email, token);

    // Devolver respuesta exitosa en formato JSON
    Map<String, String> response = new HashMap<>();
    response.put("message", "Correo de restablecimiento enviado con éxito");
    response.put("token", token);
    return ResponseEntity.ok(response);
}

}
