package ar.edu.utn.ba.ddsi.climalert.domain.models;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RegistroClima {
    private Double temperatura;
    private Integer humedad;
    private LocalDateTime fechaHora;

    public RegistroClima(Double temperatura, Integer humedad) {
        this.temperatura = temperatura;
        this.humedad = humedad;
        this.fechaHora = LocalDateTime.now(); // Guarda el momento exacto de la consulta
    }
}
