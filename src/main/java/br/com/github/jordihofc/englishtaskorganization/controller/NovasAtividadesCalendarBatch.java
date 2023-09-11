package br.com.github.jordihofc.englishtaskorganization.controller;

import br.com.github.jordihofc.englishtaskorganization.util.GoogleCalendarTimerProvider;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventReminder;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class NovasAtividadesCalendarBatch {
    @NotNull
    @Valid
    private List<NovaAtividadeRequest> atividades;

    public List<NovaAtividadeRequest> getAtividades() {
        return atividades;
    }

    public List<Event> toModel(GoogleCalendarTimerProvider timerProvider) {
        AtomicReference<LocalDateTime> start = new AtomicReference<>(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(12, 20)));
        AtomicReference<LocalDateTime> end = new AtomicReference<>(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(12, 40)));

        return atividades.stream().map(atv -> {
            Event event = atv.toModel();
            event.setStart(timerProvider.convertTo(start.get()));
            event.setEnd(timerProvider.convertTo(end.get()));
            event.setAttendees(getAttende());
            event.setReminders(getReminders());

            start.set(start.get().plusDays(1));
            end.set(end.get().plusDays(1));
            return event;

        }).toList();
    }

    private List<EventAttendee> getAttende() {
        EventAttendee[] eventAttendees = {
                new EventAttendee().setEmail("jordyhenrique7@gmail.com"),
                new EventAttendee().setEmail("jordih.silva@gmail.com"),
        };
        return Arrays.asList(eventAttendees);
    }

    private Event.Reminders getReminders() {
        EventReminder[] reminderOverrides = new EventReminder[]{
                new EventReminder().setMethod("email").setMinutes(24 * 60),
                new EventReminder().setMethod("popup").setMinutes(10),
        };

        return new Event.Reminders()
                .setUseDefault(false)
                .setOverrides(Arrays.asList(reminderOverrides));
    }


}
