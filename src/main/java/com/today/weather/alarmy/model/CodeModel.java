package com.today.weather.alarmy.model;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
public class CodeModel {

    private String code;
    private String value;
}
