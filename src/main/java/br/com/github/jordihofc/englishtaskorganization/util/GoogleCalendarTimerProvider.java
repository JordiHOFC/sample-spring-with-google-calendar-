package br.com.github.jordihofc.englishtaskorganization.util;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.EventDateTime;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Date;
import java.time.LocalDateTime;

@Component
public class GoogleCalendarTimerProvider {

    public EventDateTime convertTo(LocalDateTime dateTime){
        Date date = Date.from(dateTime.atZone(ZoneId.of("America/Sao_Paulo")).toInstant());

        DateTime startDateTime = new DateTime(date);
        return new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("America/Sao_Paulo");

    }

}
