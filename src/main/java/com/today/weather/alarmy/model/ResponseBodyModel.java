package com.today.weather.alarmy.model;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ResponseBodyModel {

    private ResponseItemsModel items;

    private String dateType;
}
