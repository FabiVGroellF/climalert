package ar.edu.utn.ba.ddsi.climalert.domain.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CurrentWeatherDTO {

    @JsonProperty("temp_c")
    private Double tempC;

    @JsonProperty("humidity")
    private Integer humidity;

}
