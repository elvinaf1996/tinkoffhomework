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
public class City implements JsonModel {

    @JsonProperty("slug")
    private String slug;

    @JsonProperty("coords")
    private Coordinates coordinates;
}