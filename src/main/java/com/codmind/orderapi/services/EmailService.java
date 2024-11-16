package com.codmind.orderapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

public void sendPasswordResetEmail(String email, String token) {
    String resetUrl = "http://localhost:8090/api/v1/forgot-password?token=" + token;

    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(email);
    message.setSubject("Restablecimiento de contraseña");
    message.setText("Para restablecer tu contraseña, haz clic en el siguiente enlace: " + resetUrl);

    try {
        mailSender.send(message);
    } catch (MailAuthenticationException e) {
        // Manejo de excepción de autenticación
        System.err.println("Error de autenticación al enviar el correo: " + e.getMessage());
        e.printStackTrace();
    } catch (Exception e) {
        // Manejo de otras excepciones
        System.err.println("Error al enviar el correo: " + e.getMessage());
    }
}

    
}

