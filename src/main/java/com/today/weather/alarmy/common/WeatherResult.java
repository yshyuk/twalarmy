package com.today.weather.alarmy.common;

public class WeatherResult {

    public int resultCode;

    public Object object;

    public WeatherResult(Object data){
        if(data != null)
            this.object = data;
    }
}
