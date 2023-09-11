package br.com.github.jordihofc.englishtaskorganization.service;

import br.com.github.jordihofc.englishtaskorganization.util.GoogleCalendarTimerProvider;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.Arrays;

@Service
public class CalendarService {
    private final Calendar calendar;
    private final GoogleCalendarTimerProvider timerProvider;

    public CalendarService(Calendar calendar, GoogleCalendarTimerProvider timerProvider) {
        this.calendar = calendar;
        this.timerProvider = timerProvider;
    }
    public void criar(){

    }
    public void createEvent() throws GeneralSecurityException, IOException {

        Event event = new Event()
                .setSummary("Teste Inserir Evento")
                .setLocation("MEU COMPUTADOR")
                .setDescription("Toi brincando aqui");

        EventDateTime start = timerProvider.convertTo(LocalDateTime.now().plusDays(1));
        event.setStart(start);

        EventDateTime end = timerProvider.convertTo(LocalDateTime.now().plusDays(2));
        event.setEnd(end);



        EventAttendee[] attendees = new EventAttendee[] {
                new EventAttendee().setEmail("jordyhenrique7@gmail.com"),
                new EventAttendee().setEmail("jordih.silva@gmail.com"),
        };
        event.setAttendees(Arrays.asList(attendees));

        EventReminder[] reminderOverrides = new EventReminder[] {
                new EventReminder().setMethod("email").setMinutes(24 * 60),
                new EventReminder().setMethod("popup").setMinutes(10),
        };
        Event.Reminders reminders = new Event.Reminders()
                .setUseDefault(false)
                .setOverrides(Arrays.asList(reminderOverrides));
        event.setReminders(reminders);

        String calendarId = "primary";
        event = calendar.events().insert(calendarId, event).execute();
        System.out.printf("Event created: %s\n", event.getHtmlLink());

    }
    public void getAllEvents() throws IOException, GeneralSecurityException {

        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = calendar.events().list("primary")
                .setMaxResults(20)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();

        events.getItems().forEach(event-> {
            String message = String.format("Titulo: %s \nCriador: %s",event.getSummary(),event.getOrganizer());
            System.out.println(message);
        });
    }





}
