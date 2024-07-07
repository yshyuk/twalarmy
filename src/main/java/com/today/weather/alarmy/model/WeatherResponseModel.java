package com.today.weather.alarmy.model;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherResponseModel {


    @Expose
    private String weather;

    @Expose
    private int temperature;

    @Expose
    private String rain;


}
