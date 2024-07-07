package com.today.weather.alarmy.model;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseBodyModel {

    private ResponseItemsModel items;

    private String dateType;
}
