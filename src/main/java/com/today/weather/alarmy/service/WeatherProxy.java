package com.today.weather.alarmy.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "weatherDataProxy", url = "${external.aibos-worker.url}")
public class WeatherProxy {

    @GetMapping(
            value="/"
    )
    Object getWeatherInfo(@RequestParam String serviceKey, String base_date, String base_time, int nx, int ny, )
}
