package ar.edu.utn.ba.ddsi.climalert;

import ar.edu.utn.ba.ddsi.climalert.tasks.ClimaMonitorTask;
import ar.edu.utn.ba.ddsi.climalert.services.NotificadorMailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ClimalertApplicationTests {

    // Se inyecta la tarea programada
    @Autowired
    private ClimaMonitorTask climaMonitorTask;

    // Se inyecta el servicio de correos
    @Autowired
    private NotificadorMailService notificadorMailService;

    @Test
    void contextLoads() {
        assertNotNull(climaMonitorTask, "El bean ClimaMonitorTask debería haberse inyectado correctamente");
        assertNotNull(notificadorMailService, "El bean NotificadorMailService debería haberse inyectado correctamente");
    }

    @Test
    void pruebaDeIntegracionEnvioDeAlertaReal() {
        // 1. Se definen los valores de alerta crítica
        Double temperaturaExtrema = 39.5;
        Integer humedadExtrema = 80;

        // 2. Se fuerza la ejecución del servicio que despacha el mail
        notificadorMailService.enviarAlertaCritica(temperaturaExtrema, humedadExtrema);

        // 3. Se verifica si el método termina su ejecución sin lanzar ninguna excepción de conexión.
        // Se adjunta captura del mail en Mailtrap en el README como prueba de que el test pasó exitosamente.
    }

}
