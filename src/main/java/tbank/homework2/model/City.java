package tbank.homework2.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class City implements JsonModel {

    @JsonProperty("slug")
    private String slug;

    @JsonProperty("coords")
    private Coordinates coordinates;
}