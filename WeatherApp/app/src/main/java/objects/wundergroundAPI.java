package objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class wundergroundAPI {
    private current_observation current_observation;

    public current_observation getCurrent_observation() {
        return current_observation;
    }
}
