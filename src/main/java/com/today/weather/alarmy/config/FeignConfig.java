package com.today.weather.alarmy.config;


import com.google.gson.Gson;
import com.today.weather.alarmy.model.ResponseModel;
import feign.Util;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.context.annotation.Bean;


public class FeignConfig {


    @Bean
    Decoder decoder() {
        return (response, type) -> {
            String bodyStr = Util.toString(response.body().asReader(Util.UTF_8));
            return new Gson().fromJson(bodyStr, ResponseModel.class);
        };
    }
}
