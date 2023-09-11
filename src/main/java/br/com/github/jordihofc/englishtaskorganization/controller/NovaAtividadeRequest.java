package br.com.github.jordihofc.englishtaskorganization.controller;

import com.google.api.services.calendar.model.Event;
import jakarta.validation.constraints.NotBlank;

import static java.lang.String.*;

public class NovaAtividadeRequest {
    @NotBlank
    private String titulo;
    @NotBlank
    private String descricao;

    private String link;


    public Event toModel() {
        if (link != null) {
            descricao = format("%s acesse em: %s", descricao, link);
        }
        return new Event()
                .setSummary(titulo)
                .setDescription(descricao);
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getLink() {
        return link;
    }
}
