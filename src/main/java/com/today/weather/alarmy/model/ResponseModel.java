package com.today.weather.alarmy.model;


import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseModel {

    private ResponseHeaderModel header;

    private ResponseBodyModel body;

}
