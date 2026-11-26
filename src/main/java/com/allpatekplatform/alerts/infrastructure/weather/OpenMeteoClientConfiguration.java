package com.allpatekplatform.alerts.infrastructure.weather;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import java.net.http.HttpClient;
import java.time.Duration;
@Configuration
public class OpenMeteoClientConfiguration {
    @Bean("openMeteoRestClient")
    public RestClient client(@Value("${weather.open-meteo.connect-timeout-ms:2000}") long connectMillis,
                             @Value("${weather.open-meteo.read-timeout-ms:5000}") long readMillis) {
        if (connectMillis <= 0 || readMillis <= 0) throw new IllegalArgumentException("Weather timeouts must be positive.");
        var http = HttpClient.newBuilder().connectTimeout(Duration.ofMillis(connectMillis)).build();
        var factory = new JdkClientHttpRequestFactory(http);
        factory.setReadTimeout(Duration.ofMillis(readMillis));
        return RestClient.builder().requestFactory(factory).build();
    }
}
