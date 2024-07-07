package com.today.weather.alarmy.controller;

import com.today.weather.alarmy.common.WeatherResult;
import com.today.weather.alarmy.service.AlarmyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping(value="/twalarmy", produces = MediaType.APPLICATION_JSON_VALUE)
public class AlarmyController {

    @Autowired
    AlarmyService alarmyService;

    @GetMapping(value = "getWeatherInfo")
    public WeatherResult getWeatherInfo(@RequestParam(required = true) double lat,
                                        @RequestParam(required = true) double lng){

        return new WeatherResult(alarmyService.getWeatherInfo(lat,lng));
    }

}
