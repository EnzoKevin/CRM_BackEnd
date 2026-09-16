package com.crm.MVP.modules.Clients.Repository;

import com.crm.MVP.modules.Clients.Entity.Contacts;
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
public class ClientsRepository {

    private static final String CLIENTS_COLLECTION = "clients";
    private static final String SEQUENCES_COLLECTION = "sequences";
    private static final String CLIENTS_SEQUENCE_DOCUMENT = "clients";

    private final Firestore firestore;

    public ClientsRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    public Contacts save(Contacts contact) {
        try {
            if (contact.getId() > 0) {
                firestore.collection(CLIENTS_COLLECTION)
                        .document(Long.toString(contact.getId()))
                        .set(toDocument(contact))
                        .get();
                return contact;
            }

            return firestore.runTransaction(transaction -> {
                DocumentReference sequence = firestore.collection(SEQUENCES_COLLECTION)
                        .document(CLIENTS_SEQUENCE_DOCUMENT);
                DocumentSnapshot snapshot = transaction.get(sequence).get();
                Long currentValue = snapshot.getLong("value");
                long nextValue = currentValue == null ? 1L : currentValue + 1L;

                contact.setId(nextValue);
                transaction.set(sequence, Map.of("value", nextValue));
                transaction.set(firestore.collection(CLIENTS_COLLECTION)
                        .document(Long.toString(nextValue)), toDocument(contact));
                return contact;
            }).get();
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Operação no Firebase interrompida.", exception);
        } catch (ExecutionException exception) {
            throw new IllegalStateException("Não foi possível salvar o cliente no Firebase.", exception);
        }
    }

    public List<Contacts> findAll() {
        try {
            List<Contacts> contacts = new ArrayList<>();
            for (DocumentSnapshot snapshot : firestore.collection(CLIENTS_COLLECTION).get().get().getDocuments()) {
                contacts.add(fromDocument(snapshot));
            }
            return contacts;
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Operação no Firebase interrompida.", exception);
        } catch (ExecutionException exception) {
            throw new IllegalStateException("Não foi possível buscar os clientes no Firebase.", exception);
        }
    }

    public Optional<Contacts> findById(Long id) {
        if (id == null) {
            return Optional.empty();
        }

        try {
            DocumentSnapshot snapshot = firestore.collection(CLIENTS_COLLECTION)
                    .document(id.toString())
                    .get()
                    .get();
            return snapshot.exists() ? Optional.of(fromDocument(snapshot)) : Optional.empty();
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Operação no Firebase interrompida.", exception);
        } catch (ExecutionException exception) {
            throw new IllegalStateException("Não foi possível buscar o cliente no Firebase.", exception);
        }
    }

    public void deleteById(Long id) {
        if (id == null) {
            return;
        }

        try {
            firestore.collection(CLIENTS_COLLECTION).document(id.toString()).delete().get();
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Operação no Firebase interrompida.", exception);
        } catch (ExecutionException exception) {
            throw new IllegalStateException("Não foi possível excluir o cliente no Firebase.", exception);
        }
    }

    private Map<String, Object> toDocument(Contacts contact) {
        Map<String, Object> document = new HashMap<>();
        document.put("id", contact.getId());
        document.put("nome", contact.getNome());
        document.put("email", contact.getEmail());
        document.put("telefone", contact.getTelefone());
        document.put("empresa", contact.getEmpresa());
        document.put("status", contact.getStatus());
        document.put("data", contact.getData());
        document.put("value", contact.getValue());
        document.put("responsible", contact.getResponsible());
        return document;
    }

    private Contacts fromDocument(DocumentSnapshot snapshot) {
        Contacts contact = new Contacts();
        Long id = snapshot.getLong("id");
        contact.setId(id == null ? Long.parseLong(snapshot.getId()) : id);
        contact.setNome(snapshot.getString("nome"));
        contact.setEmail(snapshot.getString("email"));
        contact.setTelefone(snapshot.getString("telefone"));
        contact.setEmpresa(snapshot.getString("empresa"));
        contact.setStatus(snapshot.getString("status"));
        contact.setData(snapshot.getString("data"));
        contact.setValue(snapshot.getString("value"));
        contact.setResponsible(snapshot.getString("responsible"));
        return contact;
    }
}