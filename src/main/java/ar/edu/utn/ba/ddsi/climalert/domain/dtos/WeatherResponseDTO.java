package ar.edu.utn.ba.ddsi.climalert.domain.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WeatherResponseDTO {

    @JsonProperty("current")
    private CurrentWeatherDTO current;

}
