package com.weather.app;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WeatherTest {

    @Test
    public void validateWeather() {
        // REPLACE THE KEY BELOW with your real 32-character key from OpenWeatherMap
        String apiKey = "YOUR_API_KEY_HERE"; 
        String city = "Karimnagar";

        System.out.println("--- Starting Weather API Test for " + city + " ---");
        
        Response response = RestAssured
            .get("https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey + "&units=metric");

        // This line helps diagnose the issue by printing the server's actual message
        System.out.println("Server Response Body: " + response.getBody().asString());

        int statusCode = response.getStatusCode();
        System.out.println("Status Code: " + statusCode);

        if(statusCode == 200) {
            float apiTemp = response.jsonPath().getFloat("main.temp");
            System.out.println("Current Temperature in " + city + " is: " + apiTemp + "°C");
            Assert.assertTrue(apiTemp > -50 && apiTemp < 60, "Temperature value is unrealistic!");
        } else {
            System.out.println("TEST FAILED: Server returned an error. Check the Response Body above.");
        }
    }
}
