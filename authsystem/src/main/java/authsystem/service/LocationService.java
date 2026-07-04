package authsystem.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class LocationService {

    public String getLocation(String ipAddress) {

        // For localhost testing
        if (ipAddress.equals("127.0.0.1") ||
                ipAddress.equals("0:0:0:0:0:0:0:1")) {

            return "Localhost (Development Environment)";
        }

        try {

            String url = "http://ip-api.com/json/" + ipAddress;

            RestTemplate restTemplate = new RestTemplate();

            Map response =
                    restTemplate.getForObject(url, Map.class);

            if (response != null &&
                    "success".equals(response.get("status"))) {

                String city =
                        (String) response.get("city");

                String country =
                        (String) response.get("country");

                return city + ", " + country;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Unknown Location";
    }
}