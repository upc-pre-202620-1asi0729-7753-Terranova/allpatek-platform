package com.allpatekplatform.alerts.infrastructure.weather;

import com.allpatekplatform.alerts.application.internal.outboundservices.WeatherForecastService;
import com.allpatekplatform.alerts.domain.model.aggregates.ClimateReading;
import com.allpatekplatform.alerts.domain.model.valueobjects.ParcelCoordinates;
import com.allpatekplatform.alerts.domain.model.valueobjects.WeatherSnapshot;
import com.allpatekplatform.shared.application.result.ApplicationError;
import com.allpatekplatform.shared.application.result.Result;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OpenMeteoWeatherForecastService implements WeatherForecastService {

    private static final String CURRENT =
            "temperature_2m,apparent_temperature,relative_humidity_2m,precipitation,wind_speed_10m,cloud_cover,weather_code";
    private static final String DAILY =
            "temperature_2m_max,temperature_2m_min,precipitation_sum,wind_speed_10m_max";

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final RestClient restClient;
    private final String baseUrl;

    public OpenMeteoWeatherForecastService(@Qualifier("openMeteoRestClient") RestClient restClient,
            @Value("${weather.open-meteo.base-url}") String baseUrl) {
        this.restClient = restClient;
        this.baseUrl = baseUrl;
    }

    @Override
    public Result<List<ClimateReading>, ApplicationError> forecast(List<ParcelCoordinates> places) {
        if (places == null || places.isEmpty()) {
            return Result.success(List.of());
        }
        try {
            URI uri = UriComponentsBuilder.fromUriString(baseUrl)
                    .queryParam("latitude", join(places, ParcelCoordinates::latitude))
                    .queryParam("longitude", join(places, ParcelCoordinates::longitude))
                    .queryParam("current", CURRENT)
                    .queryParam("daily", DAILY)
                    .queryParam("forecast_days", "2")
                    .queryParam("timezone", "auto")
                    .build(true)
                    .toUri();
            String json = restClient.get()
                    .uri(uri)
                    .retrieve()
                    .body(String.class);
            JsonNode payload = json == null || json.isBlank() ? null : MAPPER.readTree(json);
            List<JsonNode> blocks = OpenMeteoResponseParser.blocks(payload, places.size());
            List<ClimateReading> readings = new ArrayList<>();
            for (int index = 0; index < places.size(); index++) {
                readings.add(OpenMeteoResponseParser.toReading(places.get(index), blocks.get(index)));
            }
            return Result.success(List.copyOf(readings));
        } catch (RestClientResponseException exception) {
            int status = exception.getStatusCode().value();
            return failure(status == 429 || status >= 500 ? "WEATHER_UNAVAILABLE" : "WEATHER_BAD_GATEWAY");
        } catch (ResourceAccessException exception) {
            return failure(isTimeout(exception) ? "WEATHER_TIMEOUT" : "WEATHER_UNAVAILABLE");
        } catch (JsonProcessingException | IllegalArgumentException exception) {
            return failure("WEATHER_BAD_GATEWAY");
        } catch (RestClientException exception) {
            return failure("WEATHER_UNAVAILABLE");
        }
    }

    private static Result<List<ClimateReading>, ApplicationError> failure(String code) {
        return Result.failure(new ApplicationError(code, "Weather data is temporarily unavailable."));
    }

    private static boolean isTimeout(Throwable exception) {
        for (Throwable cause = exception; cause != null; cause = cause.getCause()) {
            if (cause instanceof java.net.SocketTimeoutException || cause instanceof java.net.http.HttpTimeoutException)
                return true;
        }
        return false;
    }

    private static String join(List<ParcelCoordinates> places, java.util.function.ToDoubleFunction<ParcelCoordinates> value) {
        return places.stream()
                .map(place -> Double.toString(value.applyAsDouble(place)))
                .collect(Collectors.joining(","));
    }

}
