package com.siddhu.Journal.service;

import com.siddhu.Journal.entity.ApiResponce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Autowired
    private RedisService redisService;

    private static final String apiKey = "fd9e83ef9920d65b103d1ed46d740a6a";

    private static final String url = "http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    @Autowired
    private RestTemplate restTemplate;

    public ApiResponce getWeather(String city){
        ApiResponce responce = redisService.get(city, ApiResponce.class);

        if (responce != null) return responce;

        else {
            String finalApi = url.replace("API_KEY", apiKey).replace("CITY", city);
            ApiResponce finalResponce = restTemplate.getForObject(finalApi, ApiResponce.class);
            if(finalResponce != null){
                redisService.set(city, finalResponce, 10);
            }
            return finalResponce;
        }
    }
}
