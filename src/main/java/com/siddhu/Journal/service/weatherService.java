package com.siddhu.Journal.service;

import com.siddhu.Journal.entity.apiResponce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class weatherService {

    @Autowired
    private RedisService redisService;

    private static final String apiKey = "fd9e83ef9920d65b103d1ed46d740a6a";

    private static final String url = "http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    @Autowired
    private RestTemplate restTemplate;

    public apiResponce getWeather(String city){
        apiResponce responce = redisService.get(city, apiResponce.class);

        if (responce != null) return responce;

        else {
            String finalApi = url.replace("API_KEY", apiKey).replace("CITY", city);
            apiResponce finalResponce = restTemplate.getForObject(finalApi, apiResponce.class);
            if(finalResponce != null){
                redisService.set(city, finalResponce, 10);
            }
            return finalResponce;
        }
    }
}
