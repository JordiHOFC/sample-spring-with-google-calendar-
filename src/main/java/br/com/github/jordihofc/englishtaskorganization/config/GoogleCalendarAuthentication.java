package br.com.github.jordihofc.englishtaskorganization.config;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

@Component
public class GoogleCalendarAuthentication {
    private final String TOKENS_DIRECTORY_PATH = "tokens";
    @Value("google.client-oauth2.application-calendar-name:")
    private String applicationName;
    private final List<String> SCOPES =
            Collections.singletonList(CalendarScopes.CALENDAR);

    @Value("classpath:/credentials.json")
    private Resource credentials;

    @Autowired
    private JsonFactory googleJsonFactory;

    public Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT, String userName)
            throws IOException {
        // Load client secrets.
        InputStream in = credentials.getInputStream();
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + credentials.getFilename());
        }
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(googleJsonFactory, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, googleJsonFactory, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();

        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize(userName);

        return credential;
    }


}
