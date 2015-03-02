package resources;
import android.location.Address;
import android.location.Geocoder;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import 	android.content.Context;


import com.dustinfehlman.weatherapp.MainActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.WeakHashMap;

import objects.wundergroundAPI;


public class weatherService {


    public static ArrayList<String> getWeather(double latitude, double longitude, Context context) throws IOException {

        ArrayList <String> weatherArray = new ArrayList<>();



        Geocoder gcd = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = gcd.getFromLocation(latitude, longitude, 1);

        if (addresses != null && addresses.size() > 0) {
            String zipCode = addresses.get(0).getPostalCode();


            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            wundergroundAPI currentWeather = restTemplate.getForObject("http://api.wunderground.com/api/3b0b2e8c5e50f313/conditions/q/" + zipCode + ".json", wundergroundAPI.class);

            int temp = currentWeather.getCurrent_observation().getTemp_f();
            String cityName = currentWeather.getCurrent_observation().getDisplay_location().getCity();
            String timeUpdated = currentWeather.getCurrent_observation().getObservation_time();
            String icon_url = currentWeather.getCurrent_observation().getIcon_url();
            String weather = currentWeather.getCurrent_observation().getWeather();
            //Temp stored in index 0
            weatherArray.add(String.valueOf(temp) + "°");
            //City name stored in index 1
            weatherArray.add(cityName);
            //Time updated stored in index 2
            weatherArray.add(timeUpdated);
            //Weather icon index 3
            weatherArray.add(icon_url);
            //Weather text index 4
            weatherArray.add(weather);


        }
        else{
            weatherArray.add("Error");
            weatherArray.add("Error");
            weatherArray.add("Error");

        }





        return weatherArray;
    }


}


