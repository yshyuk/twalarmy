package com.today.weather.alarmy.Utility;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.format.annotation.DateTimeFormat;

public class Utility {

    public static OffsetDateTime getNow() {
        return OffsetDateTime.now();
    }

}
