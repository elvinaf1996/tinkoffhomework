package tbank.homework2.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Coordinates {

    @JsonProperty("lat")
    private double lat;

    @JsonProperty("lon")
    private double lon;
}
