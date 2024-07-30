package com.today.weather.alarmy.model;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;


@Getter
@Setter
@Accessors(chain = true)
public class WeatherModel {

    private String key;

    private String weatherCode;

    private String weatherKey;

    private String weatherValue;

    private String nx;

    private String ny;

    private OffsetDateTime savedTs;


    void setKey(){
        this.key = String.format(String.format("%s%s",savedTs,weatherCode));
    }

    void setKey(String baseDate, String baseTime, String weatherCode){
        this.key = String.format("%s%s%s",baseDate,baseTime,weatherCode);
    }
}
