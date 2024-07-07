package com.today.weather.alarmy.service;

import com.today.weather.alarmy.common.WeatherResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "weatherDataProxy", url = "${external.aibos-worker.url}")
public class WeatherProxy {

    @GetMapping(
            value="/"
    )
    public Object getWeatherInfo(@RequestParam String serviceKey,
                                 @RequestParam String base_date,
                                 @RequestParam String base_time,
                                 @RequestParam int nx,
                                 @RequestParam int ny){

        return new Object();
    }
}
