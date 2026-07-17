package com.crm.MVP.modules.User.Repository;

import com.crm.MVP.modules.User.Entity.User;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private static final String USERS_COLLECTION = "users";
    private static final String SEQUENCES_COLLECTION = "sequences";
    private static final String USER_SEQUENCE_DOCUMENT = "users";

    private final Firestore firestore;

    public UserRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    public User save(User user) {
        try {
            if (user.getId() != null) {
                firestore.collection(USERS_COLLECTION)
                        .document(user.getId().toString())
                        .set(toDocument(user))
                        .get();
                return user;
            }

            return firestore.runTransaction(transaction -> {
                DocumentReference sequence = firestore.collection(SEQUENCES_COLLECTION)
                        .document(USER_SEQUENCE_DOCUMENT);
                DocumentSnapshot snapshot = transaction.get(sequence).get();
                Long currentValue = snapshot.getLong("value");
                long nextValue = currentValue == null ? 1L : currentValue + 1L;

                user.setId(nextValue);
                transaction.set(sequence, Map.of("value", nextValue));
                transaction.set(firestore.collection(USERS_COLLECTION)
                        .document(Long.toString(nextValue)), toDocument(user));
                return user;
            }).get();
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Operação no Firebase interrompida.", exception);
        } catch (ExecutionException exception) {
            throw new IllegalStateException("Não foi possível salvar o usuário no Firebase.", exception);
        }
    }

    public List<User> findAll() {
        try {
            List<User> users = new ArrayList<>();
            for (DocumentSnapshot snapshot : firestore.collection(USERS_COLLECTION).get().get().getDocuments()) {
                users.add(fromDocument(snapshot));
            }
            return users;
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Operação no Firebase interrompida.", exception);
        } catch (ExecutionException exception) {
            throw new IllegalStateException("Não foi possível buscar os usuários no Firebase.", exception);
        }
    }

    public Optional<User> findById(Long id) {
        try {
            DocumentSnapshot snapshot = firestore.collection(USERS_COLLECTION)
                    .document(id.toString())
                    .get()
                    .get();
            return snapshot.exists() ? Optional.of(fromDocument(snapshot)) : Optional.empty();
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Operação no Firebase interrompida.", exception);
        } catch (ExecutionException exception) {
            throw new IllegalStateException("Não foi possível buscar o usuário no Firebase.", exception);
        }
    }

    public void deleteById(Long id) {
        try {
            firestore.collection(USERS_COLLECTION).document(id.toString()).delete().get();
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Operação no Firebase interrompida.", exception);
        } catch (ExecutionException exception) {
            throw new IllegalStateException("Não foi possível excluir o usuário no Firebase.", exception);
        }
    }

    private Map<String, Object> toDocument(User user) {
        Map<String, Object> document = new HashMap<>();
        document.put("id", user.getId());
        document.put("nome", user.getNome());
        document.put("email", user.getEmail());
        document.put("admin", user.isAdmin());
        document.put("idioma", user.getIdioma());
        document.put("fusoHorario", user.getFusoHorario());
        document.put("formatoDeData", user.getFormatoDeData());
        document.put("notificacoesEmail", user.isNotificacoesEmail());
        document.put("alertasWeb", user.isAlertasWeb());
        document.put("resumoSemanal", user.isResumoSemanal());
        return document;
    }

    private User fromDocument(DocumentSnapshot snapshot) {
        User user = new User();
        user.setId(snapshot.getLong("id"));
        user.setNome(snapshot.getString("nome"));
        user.setEmail(snapshot.getString("email"));
        user.setAdmin(Boolean.TRUE.equals(snapshot.getBoolean("admin")));
        user.setIdioma(snapshot.getString("idioma"));
        user.setFusoHorario(snapshot.getString("fusoHorario"));
        user.setFormatoDeData(snapshot.getString("formatoDeData"));
        user.setNotificacoesEmail(Boolean.TRUE.equals(snapshot.getBoolean("notificacoesEmail")));
        user.setAlertasWeb(Boolean.TRUE.equals(snapshot.getBoolean("alertasWeb")));
        user.setResumoSemanal(Boolean.TRUE.equals(snapshot.getBoolean("resumoSemanal")));
        return user;
    }
}
