package com.today.weather.alarmy;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.today.weather.alarmy.model.ResponseModel;
import com.today.weather.alarmy.proxy.IWeatherForcast;
import com.today.weather.alarmy.service.AlarmyService;
import feign.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@SpringBootTest
public class ProxyTest {

    @Autowired
    private IWeatherForcast weatherForcast;

    @Autowired
    AlarmyService alarmyService;

    @Value(
            "${external.data.key}")
    String key;


    @Test
    void test() throws Exception {
        LocalDateTime local = LocalDateTime.now();
        String nowDate = local.format(DateTimeFormatter.ofPattern("YYYYMMdd"));
        String nowTime = local.format(DateTimeFormatter.ofPattern("HHMM"));

        ResponseModel model = weatherForcast.getUltraSrtNcst(key, "200", "1", "JSON", nowDate, nowTime, 63, 123);
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        String jsonString = gson.toJson(model);
        System.out.println(jsonString);
    }
}
