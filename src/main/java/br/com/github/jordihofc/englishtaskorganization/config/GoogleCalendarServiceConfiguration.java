package br.com.github.jordihofc.englishtaskorganization.config;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.calendar.Calendar;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Configuration
public class GoogleCalendarServiceConfiguration {
    private final GoogleCalendarAuthentication authService;
    private final JsonFactory googleJsonFactory;
    @Value("google.client-oauth2.application-calendar-name:")
    private String applicationName;

    public GoogleCalendarServiceConfiguration(GoogleCalendarAuthentication authService, JsonFactory googleJsonFactory) {
        this.authService = authService;
        this.googleJsonFactory = googleJsonFactory;
    }

    @Bean
    public Calendar googleCalendar() throws GeneralSecurityException, IOException {
        NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Credential credentials = authService.getCredentials(HTTP_TRANSPORT, "jordi");
        return new Calendar.Builder(HTTP_TRANSPORT, googleJsonFactory, credentials)
                .setApplicationName(applicationName)
                .build();
    }
}
