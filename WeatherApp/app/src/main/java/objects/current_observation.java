package objects;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class current_observation {


    private int temp_f;
    private String weather;
    private display_location display_location;
    private String observation_time;
    private String icon_url;
    private String icon;

    public String getIcon() {
        return icon;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public String getObservation_time() {
        return observation_time;
    }

    public display_location getDisplay_location() {
        return display_location;
    }


    public String getWeather() {
        return weather;
    }

    public int getTemp_f() {
        return temp_f;
    }




}
