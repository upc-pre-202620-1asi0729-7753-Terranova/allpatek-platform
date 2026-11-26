package com.allpatekplatform.alerts.interfaces.rest;

import com.allpatekplatform.shared.application.result.ApplicationError;
import com.allpatekplatform.shared.interfaces.rest.transform.ProblemDetailFromApplicationErrorAssembler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class WeatherErrorLocalizationTest {
    @AfterEach
    void resetLocale() {
        LocaleContextHolder.resetLocaleContext();
    }

    @Test
    void returnsSpecificWeatherErrorsInSpanishAndEnglish() {
        var unavailable = new ApplicationError("WEATHER_UNAVAILABLE", "Unavailable");
        var invalid = new ApplicationError("WEATHER_BAD_GATEWAY", "Invalid response");
        LocaleContextHolder.setLocale(Locale.forLanguageTag("es"));
        assertEquals("Los datos del clima no están disponibles temporalmente",
                ProblemDetailFromApplicationErrorAssembler.toProblemDetailFromApplicationError(unavailable).getDetail());
        assertEquals("El proveedor del clima devolvió una respuesta inválida",
                ProblemDetailFromApplicationErrorAssembler.toProblemDetailFromApplicationError(invalid).getDetail());
        LocaleContextHolder.setLocale(Locale.ENGLISH);
        assertEquals("Weather data is temporarily unavailable",
                ProblemDetailFromApplicationErrorAssembler.toProblemDetailFromApplicationError(unavailable).getDetail());
        assertEquals("The weather provider returned an invalid response",
                ProblemDetailFromApplicationErrorAssembler.toProblemDetailFromApplicationError(invalid).getDetail());
    }
}
