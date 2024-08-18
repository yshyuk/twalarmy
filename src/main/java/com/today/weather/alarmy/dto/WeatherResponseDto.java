package com.today.weather.alarmy.dto;


import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
public class WeatherResponseDto {

    private OffsetDateTime date;

    private String temperature;

    private String cloudStatus;

    private String precipitation;

    private String precipProbability;

    private String precipForm;

    private String fineDustConcentration;

    private String ultraFineDustConcentration;

    private String humidity;
}
