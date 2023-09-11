package br.com.github.jordihofc.englishtaskorganization.controller;

import br.com.github.jordihofc.englishtaskorganization.util.GoogleCalendarTimerProvider;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class CadastrarMuitasAtividadesController {
    private final Calendar calendar;
    private final GoogleCalendarTimerProvider timerProvider;

    public CadastrarMuitasAtividadesController(Calendar calendar, GoogleCalendarTimerProvider timerProvider) {
        this.calendar = calendar;
        this.timerProvider = timerProvider;
    }

    @PostMapping("/atividades")
    public ResponseEntity<?> cadastrar(@RequestBody @Valid NovasAtividadesCalendarBatch request) throws IOException {

        List<Event> atividades = request.toModel(timerProvider);
        atividades.forEach(event -> {
            try {
                calendar.events().insert("primary", event).execute();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
