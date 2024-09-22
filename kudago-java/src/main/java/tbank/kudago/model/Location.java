package tbank.kudago.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location extends Model {

    private String slug;
    private String name;
    private String timezone;
    private Coordinates coords;
    private String language;
    private String currency;
}
