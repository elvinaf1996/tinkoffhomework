package tbank.homework2.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class City {

    @JsonProperty("slug")
    private String slug;

    @JsonProperty("coords")
    private Coordinates coordinates;
}