package com.today.weather.alarmy;

import com.google.gson.*;
import com.today.weather.alarmy.model.ResponseBodyModel;
import com.today.weather.alarmy.model.ResponseModel;
import com.today.weather.alarmy.service.AlarmyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

//@SpringBootTest
class AlarmyApplicationTests {

    @Test
    void contextLoads() {

        String is = null;
        Integer it = Integer.parseInt(is);
        System.out.println(it);
    }


    @Test
    void convertToLambert() {
        // 기준 데이텀 설정 (WGS84)
        double a = 6378137.0; // 장축 길이 (m)
        double f = 1.0 / 298.257223563; // 편평률

        // 보조 변수 계산
        double phi = Math.toRadians(37.505816);
        double lambda = Math.toRadians(127.101343);
        double e2 = 2 * f - f * f;
        double n = f / (2 - f);
        double A = a * Math.cos(phi) * Math.sqrt(1 - e2 * Math.sin(phi) * Math.sin(phi)) / (1
            - e2 * Math.sin(phi) * Math.sin(phi));
        double alpha = (A * Math.cos(phi)) / (Math.sqrt(1 - e2 * Math.sin(phi) * Math.sin(phi)));
        double eta2 = e2 / (1 - e2);

        // Lambert 도법 좌표 계산
        double x = alpha * (lambda - Math.toRadians(2.337229167));
        double y = alpha * Math.log(Math.tan(Math.PI / 4 + phi / 2) * Math.pow(
            (1 - Math.sqrt(e2) * Math.sin(phi)) / (1 + Math.sqrt(e2) * Math.sin(phi)),
            Math.sqrt(e2) / 2));

        System.out.println("X = " + x);
        System.out.println("Y = " + y);
    }


    static class DoubleWrapper {

        public double value;

        public DoubleWrapper(double value) {
            this.value = value;
        }
    }

    @Test
    void connect() throws IOException {

        Date date2 = new Date();

        Timestamp time = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat date = new SimpleDateFormat("YYYYMMdd");
        date.format(time);
        LocalDateTime local = LocalDateTime.now();
        String nowString = local.format(DateTimeFormatter.ofPattern("YYYYMMdd"));

        StringBuilder urlBuilder = new StringBuilder(
            "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8")
            + "=Qs6ElrLr%2F%2FyGKLhIDuOdqq7KIBMukuCW4f6OxBNL931QBZqcomi1K6HckVeN0T5%2BGv6%2FyjHud4wKym0i30Vf3Q%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1",
            "UTF-8")); /*페이지번호*/
        urlBuilder.append(
            "&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("1000",
                "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append(
            "&" + URLEncoder.encode("dataType", "UTF-8") + "=" + URLEncoder.encode("JSON",
                "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
        urlBuilder.append(
            "&" + URLEncoder.encode("base_date", "UTF-8") + "=" + URLEncoder.encode(nowString,
                "UTF-8")); /*‘21년 6월 28일 발표*/
        urlBuilder.append(
            "&" + URLEncoder.encode("base_time", "UTF-8") + "=" + URLEncoder.encode("0600",
                "UTF-8")); /*06시 발표(정시단위) */
        urlBuilder.append("&" + URLEncoder.encode("nx", "UTF-8") + "=" + URLEncoder.encode("55",
            "UTF-8")); /*예보지점의 X 좌표값*/
        urlBuilder.append("&" + URLEncoder.encode("ny", "UTF-8") + "=" + URLEncoder.encode("127",
            "UTF-8")); /*예보지점의 Y 좌표값*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        Object obj = conn.getInputStream();
        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }

        JsonParser pars = new JsonParser();
        pars.parseString(sb.toString());
        JsonElement item = ((JsonObject) JsonParser.parseString((sb.toString()))).get("response");

        ResponseModel body = new Gson().fromJson(item.toString(), ResponseModel.class);

        //TODO : 객체 다루는거 끝남. 이제 해야 할건, 객체를 가지고 가공해서 보여주기

        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());
    }

    @Test
    void functionLatLngToLambert() {

        double lat = 37.414124f;
        double lng = 126.625892f;

        LamcParameter map = new LamcParameter();
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
        result = mapConv(lng, lat,  map);


    }

    private static final int NX = 149; // X축 격자점 수
    private static final int NY = 253; // Y축 격자점 수

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

    private static float[] mapConv(double a, double b, LamcParameter map) {
        float[] result = new float[2];
            float[] xy = lamcproj(a, b,  map);
            result[0] = Math.round(xy[0] + 1.5f);
            result[1] = Math.round(xy[1] + 1.5f);
        return result;
    }

    private static float[] lamcproj(double a, double b, LamcParameter map) {
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

    @Test
    void proxyTest(){

    }
}


