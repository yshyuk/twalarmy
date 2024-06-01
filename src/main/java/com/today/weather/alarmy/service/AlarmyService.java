package com.today.weather.alarmy.service;


import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AlarmyService {

    protected final Logger    logger = LoggerFactory.getLogger(this.getClass());

    @Value("${external.data.key}")
    public String serviceKey;

    public WeatherDto getWeatherInfo(){

    }


    public double[] convertToLambert(double latitude, double longitude) {
        // 기준 데이텀 설정 (WGS84)
        double a = 6378137.0; // 장축 길이 (m)
        double f = 1.0 / 298.257223563; // 편평률

        // 보조 변수 계산
        double phi = Math.toRadians(latitude);
        double lambda = Math.toRadians(longitude);
        double e2 = 2 * f - f * f;
        double n = f / (2 - f);
        double A = a * Math.cos(phi) * Math.sqrt(1 - e2 * Math.sin(phi) * Math.sin(phi)) / (1 - e2 * Math.sin(phi) * Math.sin(phi));
        double alpha = (A * Math.cos(phi)) / (Math.sqrt(1 - e2 * Math.sin(phi) * Math.sin(phi)));
        double eta2 = e2 / (1 - e2);

        // Lambert 도법 좌표 계산
        double x = alpha * (lambda - Math.toRadians(2.337229167));
        double y = alpha * Math.log(Math.tan(Math.PI / 4 + phi / 2) * Math.pow((1 - Math.sqrt(e2) * Math.sin(phi)) / (1 + Math.sqrt(e2) * Math.sin(phi)), Math.sqrt(e2) / 2));

        return new double[] { x, y };
    }


}
