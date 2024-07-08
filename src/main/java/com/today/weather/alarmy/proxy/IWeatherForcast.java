package com.today.weather.alarmy.proxy;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(
        value ="weatherForcast",
        url= "${external.data.url}"
)
public interface IWeatherForcast {

    @GetMapping(value="/getUltraSrtNcst")
    Object getUltraSrtNcst(@RequestParam(name = "key") String key,
    @RequestParam(name = "numOfRows") String rows,
                           @RequestParam(name = "pageNo") String page,
                           @RequestParam(name = "dataType") String dataType,
                           @RequestParam(name = "base_date") String date,
                           @RequestParam(name = "base_time") String time,
                           @RequestParam(name = "nx") double nx,
                           @RequestParam(name = "ny") double ny) throws Exception;
}
