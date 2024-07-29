package com.today.weather.alarmy.config;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.today.weather.alarmy.model.ResponseModel;
import feign.Util;
import feign.codec.Decoder;
import org.springframework.context.annotation.Bean;


public class FeignConfig {


    @Bean
    Decoder decoder() {
        return (response, type) -> {
            String bodyStr = Util.toString(response.body().asReader(Util.UTF_8));
            JsonElement element =  JsonParser.parseString(bodyStr);
            JsonElement responseElement = element.getAsJsonObject().get("response");
        return new Gson().fromJson(responseElement, ResponseModel.class);
        };
    }
}
