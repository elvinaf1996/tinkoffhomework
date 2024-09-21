package tbank.homework5.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Location {

    private String slug;
    private String name;
    private String timezone;
//    private String Coor


//    {
//        "slug": "spb",
//            "name": "Санкт-Петербург",
//            "timezone": "GMT+03:00",
//            "coords": {
//        "lat": 59.939095,
//                "lon": 30.315868
//    },
//        "language": "ru",
//            "currency": "RUB"
//    }
}
