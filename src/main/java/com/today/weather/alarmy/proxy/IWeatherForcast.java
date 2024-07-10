package com.today.weather.alarmy.proxy;


import com.today.weather.alarmy.config.FeignConfig;
import com.today.weather.alarmy.model.ResponseModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(
        value = "weatherForcast", url = "${external.data.url}", configuration = FeignConfig.class)
public interface IWeatherForcast {

    @GetMapping(value = "/getUltraSrtNcst", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseModel getUltraSrtNcst(@RequestParam(name = "key") String key,
                                  @RequestParam(name = "numOfRows") String rows,
                                  @RequestParam(name = "pageNo") String page,
                                  @RequestParam(name = "dataType") String dataType,
                                  @RequestParam(name = "base_date") String date,
                                  @RequestParam(name = "base_time") String time,
                                  @RequestParam(name = "nx") double nx,
                                  @RequestParam(name = "ny") double ny) throws Exception;
}
