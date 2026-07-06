package ar.edu.utn.ba.ddsi.climalert.tasks;

import ar.edu.utn.ba.ddsi.climalert.domain.dtos.WeatherResponseDTO;
import ar.edu.utn.ba.ddsi.climalert.domain.models.RegistroClima;
import ar.edu.utn.ba.ddsi.climalert.services.NotificadorMailService;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
// Esta anotación crea automáticamente un objeto log que imprimirá por consola los logs de cada hilo

@Component
public class ClimaMonitorTask {

    private final RestTemplate restTemplate;
    private final List<RegistroClima> historialClima = new ArrayList<>();
    private final NotificadorMailService notificadorMail;

    // Se inyectan los valores de configuración definidos en el archivo application.yaml
    @Value("${weather.api.base-url}")
    private String baseUrl;

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.city}")
    private String city;

    public ClimaMonitorTask(RestTemplate restTemplate, NotificadorMailService notificadorMail) {
        this.restTemplate = restTemplate;
        this.notificadorMail = notificadorMail;
    }

    // Esta acción se ejecuta cada 5 minutos (300.000 milisegundos)
    @Scheduled(fixedRate = 300000)
    public void obtenerDatosClimaticos() {
        // 1. Se construye la URL completa con sus queries params
        String url = UriComponentsBuilder.fromUri(URI.create(baseUrl + "/current.json"))
            .queryParam("key", apiKey)
            .queryParam("q", city)
            .toUriString();

        try {
            // 2. Se hace GET a la API y se guarda la respuesta traducida en el DTO
            WeatherResponseDTO response = restTemplate.getForObject(url, WeatherResponseDTO.class);

            if (response != null && response.getCurrent() != null) {
                Double temp = response.getCurrent().getTempC();
                Integer hum = response.getCurrent().getHumidity();

                log.info("Datos climáticos obtenidos -> Temperatura: {}°C | Humedad: {}%", temp, hum);

                RegistroClima nuevoRegistro = new RegistroClima(temp, hum);
                historialClima.add(nuevoRegistro);

                log.info("Registro guardado en historial. Total de registros: {}", historialClima.size());
            }
        } catch (Exception e) {
            log.error("Error al consultar WeatherAPI: {}", e.getMessage());
        }
    }

    // Esta acción se ejecuta cada 1 minuto (60.000 milisegundos)
    @Scheduled(fixedRate = 60000)
    public void procesarAlertas() {
        if (historialClima.isEmpty()) {
            log.info("Evaluador de alertas: Aún no hay datos climáticos registrados.");
            return;
        }

        RegistroClima ultimoRegistro = historialClima.getLast();
        Double tempActual = ultimoRegistro.getTemperatura();
        Integer humActual = ultimoRegistro.getHumedad();

        if (tempActual > 35.0 && humActual > 60) {
            log.warn("¡ALERTA CRÍTICA METEOROLÓGICA! Condiciones peligrosas detectadas: {}°C y {}% de humedad.", tempActual, humActual);

            notificadorMail.enviarAlertaCritica(tempActual, humActual);
        } else {
            log.info("Evaluador de alertas: Condiciones normales ({}°C, {}% humedad). No se requiere acción.", tempActual, humActual);
        }
    }
}


