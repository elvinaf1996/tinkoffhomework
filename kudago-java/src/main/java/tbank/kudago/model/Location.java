package tbank.kudago.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Location extends Model {

    private String slug;
    private String name;
    private String timezone;
    private Coordinates coords;
    private String language;
    private String currency;

    public Location(String slug, String name) {
        this.slug = slug;
        this.name = name;
    }
}
