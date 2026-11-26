package com.allpatekplatform.alerts.infrastructure.weather;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;
final class ForecastClientFixture {
    record Client(OpenMeteoWeatherForecastService service, MockRestServiceServer server) {}
    static Client client() { return client(50); }
        static Client client(int batchSize) {
        var builder = RestClient.builder();
        var server = MockRestServiceServer.bindTo(builder).build();
        return new Client(new OpenMeteoWeatherForecastService(builder.build(), "https://weather.test/v1/forecast", batchSize), server);
    }
}
