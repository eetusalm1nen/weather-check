package org.example.weatherapp;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import org.json.JSONObject;
import org.json.JSONArray;


public class WeatherApp {

        private static final String API_KEY = "8d16a0b061538d391013d2f75e0ef69a";
        private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";

        public static void main(String[] args) {
            String city = "Kuopio";
            // ajetaan getData kaupunki parametrina.
            getData(city);
        }

        public static void getData(String city){
            String url = BASE_URL + "?q=" + city + "&appid=" + API_KEY + "&units=metric";
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            try {
                // Lähetetään https pyyntö ja saadaan vastaus JSON muodossa.
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                // Tallennetaan JSON muotoinen vastaus muuttujaan.
                String jsonResponse = response.body();
                // Parsitaan JSON eli tehdään siitä luettavaa.
                parseData(jsonResponse);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        public static void parseData(String jsonResponse) {
            // Parsitaan JSON.
            JSONObject obj = new JSONObject(jsonResponse);

            String cityName = obj.getString("name");

            // Lämpötila (Celsius).
            JSONObject main = obj.getJSONObject("main");
            double temp = main.getDouble("temp");
            double feelsLike = main.getDouble("feels_like");

            // Sääkuvaus.
            JSONArray weatherArray = obj.getJSONArray("weather");
            String description = weatherArray.getJSONObject(0).getString("description");

            // Tuulen nopeus.
            JSONObject wind = obj.getJSONObject("wind");
            double windSpeed = wind.getDouble("speed");

            // Tulostetaan säätilatiedot.
            System.out.println("Sää " + cityName + ":");
            System.out.println("Lämpötila: " + temp + "°C");
            System.out.println("Tuntuu kuin: " + feelsLike + "°C");
            System.out.println("Kuvaus: " + description);
            System.out.println("Tuulen nopeus: " + windSpeed + " m/s");
        }
    }
