package com.today.weather.alarmy;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.today.weather.alarmy.Utility.Utility;
import com.today.weather.alarmy.common.WeatherResult;
import com.today.weather.alarmy.dto.WeatherResponseDto;
import com.today.weather.alarmy.model.ResponseItemModel;
import com.today.weather.alarmy.model.ResponseItemsModel;
import com.today.weather.alarmy.model.ResponseModel;
import com.today.weather.alarmy.proxy.IWeatherForcast;
import com.today.weather.alarmy.service.AlarmyService;
import feign.Response;
import lombok.Builder;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoSettings;
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
        LocalDateTime local = LocalDateTime.now().minusHours(1);
        String nowDate = local.format(DateTimeFormatter.ofPattern("YYYYMMdd"));
        String nowTime = local.format(DateTimeFormatter.ofPattern("HHMM"));

        ResponseModel model = weatherForcast.getUltraSrtNcst(key, "200", "1", "JSON", nowDate,
            nowTime, 63, 123);
        ResponseItemsModel items = model.getBody().getItems();
        WeatherResponseDto result = convertCodeName(items);
        System.out.println(result.toString());
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        String jsonString = gson.toJson(model);
        System.out.println(jsonString);

    }


    //    @Test
    void functionLatLngToLambert() {
        double lat = 37.410040f;
        double lng = 127.129132f;

        AlarmyApplicationTests.LamcParameter map = new AlarmyApplicationTests.LamcParameter();
        map.Re = 6371.00877f; // 지도반경
        map.grid = 5.0f; // 격자간격 (km)
        map.slat1 = 30.0f; // 표준위도 1
        map.slat2 = 60.0f; // 표준위도 2
        map.olon = 126.0f; // 기준점 경도
        map.olat = 38.0f; // 기준점 위도
        map.xo = 210 / map.grid; // 기준점 X좌표
        map.yo = 675 / map.grid; // 기준점 Y좌표
        map.first = false;
        float[] result = new float[2];
        result = mapConv(lng, lat, map);

        System.out.println("nx : " + result[0]);
        System.out.println("ny : " + result[1]);
    }

    static class LamcParameter {

        float Re; // 사용할 지구반경 [km]
        float grid; // 격자간격 [km]
        float slat1; // 표준위도 [degree]
        float slat2; // 표준위도 [degree]
        float olon; // 기준점의 경도 [degree]
        float olat; // 기준점의 위도 [degree]
        float xo; // 기준점의 X좌표 [격자거리]
        float yo; // 기준점의 Y좌표 [격자거리]
        boolean first; // 시작여부 (false = 시작)
    }

    private static float[] mapConv(double a, double b, AlarmyApplicationTests.LamcParameter map) {
        float[] result = new float[2];
        float[] xy = lamcproj(a, b, map);
        result[0] = Math.round(xy[0] + 1.5f);
        result[1] = Math.round(xy[1] + 1.5f);
        return result;
    }

    private static float[] lamcproj(double a, double b, AlarmyApplicationTests.LamcParameter map) {
        double PI = Math.PI;
        double DEGRAD = PI / 180.0;
        double RADDEG = 180.0 / PI;
        double re, olon, olat, sn, sf, ro;
        double slat1, slat2, alon, alat, xn, yn, ra, theta;
        float[] result = new float[2];

        if (!map.first) {
            re = map.Re / map.grid;
            slat1 = map.slat1 * DEGRAD;
            slat2 = map.slat2 * DEGRAD;
            olon = map.olon * DEGRAD;
            olat = map.olat * DEGRAD;

            sn = Math.tan(PI * 0.25 + slat2 * 0.5) / Math.tan(PI * 0.25 + slat1 * 0.5);
            sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn);
            sf = Math.tan(PI * 0.25 + slat1 * 0.5);
            sf = Math.pow(sf, sn) * Math.cos(slat1) / sn;
            ro = Math.tan(PI * 0.25 + olat * 0.5);
            ro = re * sf / Math.pow(ro, sn);
            map.first = true;
        }

        re = map.Re / map.grid;
        slat1 = map.slat1 * DEGRAD;
        slat2 = map.slat2 * DEGRAD;
        olon = map.olon * DEGRAD;
        olat = map.olat * DEGRAD;

        sn = Math.tan(PI * 0.25 + slat2 * 0.5) / Math.tan(PI * 0.25 + slat1 * 0.5);
        sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn);
        sf = Math.tan(PI * 0.25 + slat1 * 0.5);
        sf = Math.pow(sf, sn) * Math.cos(slat1) / sn;
        ro = Math.tan(PI * 0.25 + olat * 0.5);
        ro = re * sf / Math.pow(ro, sn);

        ra = Math.tan(PI * 0.25 + b * DEGRAD * 0.5);
        ra = re * sf / Math.pow(ra, sn);
        theta = a * DEGRAD - olon;
        if (theta > PI) {
            theta -= 2.0 * PI;
        }
        if (theta < -PI) {
            theta += 2.0 * PI;
        }
        theta *= sn;
        result[0] = (float) (ra * Math.sin(theta) + map.xo);
        result[1] = (float) (ro - ra * Math.cos(theta) + map.yo);
        return result;
    }


    public WeatherResponseDto convertCodeName(ResponseItemsModel items) {

        WeatherResponseDto response = new WeatherResponseDto();
        response.setDate(Utility.getNow());
        for (ResponseItemModel item : items.getItem()) {
            switch (item.getCategory()) {
                //강수확률
                case "POP":
                    response.setPrecipProbability(item.getObsrValue());
                    break;
                //온도
                case "T1H":
                    response.setTemperature(item.getObsrValue());
                    break;
                    //하늘상태
                case "SKY":
                    response.setCloudStatus(item.getObsrValue());
                    break;
                //강수량
                case "PCP":
                    response.setPrecipitation(item.getObsrValue());
                    break;
                    //강수형태
                case "PTY":
                    response.setPrecipForm(item.getObsrValue());
                    break;
                    //미세먼지 농도
                    //초미세먼지 농도
                default:
                    break;
            }
        }
        return response;

    }
}
