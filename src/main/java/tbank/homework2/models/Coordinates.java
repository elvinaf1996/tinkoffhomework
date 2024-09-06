package tbank.homework2.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Coordinates implements JsonModel {

    @JsonProperty("lat")
    private Double lat;

    @JsonProperty("lon")
    private Double lon;
}
