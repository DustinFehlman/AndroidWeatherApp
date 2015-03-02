package objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class display_location {

   private String city;

    public String getCity() {
        return city;
    }
}
