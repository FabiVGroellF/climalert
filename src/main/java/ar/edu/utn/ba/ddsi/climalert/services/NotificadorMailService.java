package ar.edu.utn.ba.ddsi.climalert.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NotificadorMailService {

    private final JavaMailSender mailSender;

    // Se inyecta la dependencia por constructor
    public NotificadorMailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarAlertaCritica(Double temperatura, Integer humedad) {
        SimpleMailMessage mensaje = new SimpleMailMessage();

        mensaje.setFrom("sistema@climalert.com");
        // Se configura los 3 destinatarios exigidos por el enunciado
        mensaje.setTo("admin@clima.com", "emergencias@clima.com", "meteorologia@clima.com");
        mensaje.setSubject("¡ALERTA CRÍTICA METEOROLÓGICA!");
        mensaje.setText("Se han detectado condiciones climáticas peligrosas.\n\n" +
            "Detalle meteorológico actual:\n" +
            "- Temperatura: " + temperatura + "°C\n" +
            "- Humedad: " + humedad + "%");

        mailSender.send(mensaje);
        log.info("Notificación por correo enviada exitosamente a los administradores.");
    }
}
