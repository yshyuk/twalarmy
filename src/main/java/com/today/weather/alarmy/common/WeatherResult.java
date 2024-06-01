package com.today.weather.alarmy.common;

public class WeatherResult {

    public int resultCode;

    public Object object;

    public WeatherResult(Object object){
        if(object != null)
            object = this.object;
    }
}
