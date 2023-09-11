package br.com.github.jordihofc.englishtaskorganization.config;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreateGoogleJsonFactory {

    @Bean
    public JsonFactory googleJsonFactory(){
        return  GsonFactory.getDefaultInstance();
    }
}
