package com.today.weather.alarmy.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
public class CodeModel {

    @JsonProperty
    private String code;
    @JsonProperty
    private String value;
}
