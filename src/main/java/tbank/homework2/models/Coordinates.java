package tbank.homework2.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

@ToString
public class Coordinates implements JsonModel {

    @JsonProperty("lat")
    private Double lat;

    @JsonProperty("lon")
    private Double lon;
}
