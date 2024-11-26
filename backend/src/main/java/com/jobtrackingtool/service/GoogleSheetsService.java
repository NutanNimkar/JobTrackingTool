package com.jobtrackingtool.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;

@Service
public class GoogleSheetsService {
    public Sheets getSheetsService() throws IOException {
        GoogleCredential credential = GoogleCredential.fromStream(new FileInputStream("src/main/resources/credentials.json"))
                .createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS));

        return new Sheets.Builder(
                credential.getTransport(),
                credential.getJsonFactory(),
                credential)
                .setApplicationName("JobTrackingTool")
                .build();
    }
}
