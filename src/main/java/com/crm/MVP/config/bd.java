package com.crm.MVP.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.cloud.firestore.Firestore;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class bd {

    @Bean
    public Firestore firestore(
            @Value("${firebase.credentials.path:file:./src/main/java/com/crm/MVP/config/firebase/ServiceAccount.json}")
            Resource serviceAccount) throws IOException {
        if (FirebaseApp.getApps().isEmpty()) {
            if (!serviceAccount.exists()) {
                throw new IllegalStateException(
                        "Arquivo de credenciais do Firebase não encontrado: " + serviceAccount.getDescription());
            }

            try (InputStream credentials = serviceAccount.getInputStream()) {
                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(credentials))
                        .build();
                FirebaseApp.initializeApp(options);
            }
        }

        return FirestoreClient.getFirestore();
    }
}
