package com.crm.MVP.modules.Business.Repository;

import com.crm.MVP.modules.Business.Entity.Business;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import org.springframework.stereotype.Repository;

@Repository
public class BusinessRepository {

    private static final String BUSINESS_COLLECTION = "businesses";
    private static final String SEQUENCES_COLLECTION = "sequences";
    private static final String BUSINESS_SEQUENCE_DOCUMENT = "businesses";

    private final Firestore firestore;

    public BusinessRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    public Business save(Business business) {
        try {
            if (business.getId() != null) {
                firestore.collection(BUSINESS_COLLECTION)
                        .document(business.getId().toString())
                        .set(toDocument(business))
                        .get();
                return business;
            }

            return firestore.runTransaction(transaction -> {
                DocumentReference sequence = firestore.collection(SEQUENCES_COLLECTION)
                        .document(BUSINESS_SEQUENCE_DOCUMENT);
                DocumentSnapshot snapshot = transaction.get(sequence).get();
                Long currentValue = snapshot.getLong("value");
                long nextValue = currentValue == null ? 1L : currentValue + 1L;

                business.setId(nextValue);
                transaction.set(sequence, Map.of("value", nextValue));
                transaction.set(firestore.collection(BUSINESS_COLLECTION)
                        .document(Long.toString(nextValue)), toDocument(business));
                return business;
            }).get();
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Operação no Firebase interrompida.", exception);
        } catch (ExecutionException exception) {
            throw new IllegalStateException("Não foi possível salvar o negócio no Firebase.", exception);
        }
    }

    public List<Business> findAll() {
        try {
            List<Business> businesses = new ArrayList<>();
            for (DocumentSnapshot snapshot : firestore.collection(BUSINESS_COLLECTION).get().get().getDocuments()) {
                businesses.add(fromDocument(snapshot));
            }
            return businesses;
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Operação no Firebase interrompida.", exception);
        } catch (ExecutionException exception) {
            throw new IllegalStateException("Não foi possível buscar os negócios no Firebase.", exception);
        }
    }

    public Optional<Business> findById(Long id) {
        try {
            DocumentSnapshot snapshot = firestore.collection(BUSINESS_COLLECTION)
                    .document(id.toString())
                    .get()
                    .get();
            return snapshot.exists() ? Optional.of(fromDocument(snapshot)) : Optional.empty();
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Operação no Firebase interrompida.", exception);
        } catch (ExecutionException exception) {
            throw new IllegalStateException("Não foi possível buscar o negócio no Firebase.", exception);
        }
    }

    public void deleteById(Long id) {
        try {
            firestore.collection(BUSINESS_COLLECTION).document(id.toString()).delete().get();
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Operação no Firebase interrompida.", exception);
        } catch (ExecutionException exception) {
            throw new IllegalStateException("Não foi possível excluir o negócio no Firebase.", exception);
        }
    }

    private Map<String, Object> toDocument(Business business) {
        Map<String, Object> document = new HashMap<>();
        document.put("id", business.getId());
        document.put("titulo", business.getTitulo());
        document.put("nomeEmpresa", business.getNomeEmpresa());
        document.put("valorContrato", business.getValorContrato());
        document.put("data", business.getData());
        document.put("estagioDeNegociacao", business.getEstagioDeNegociacao());
        document.put("clienteId", business.getClienteId());
        return document;
    }

    private Business fromDocument(DocumentSnapshot snapshot) {
        Business business = new Business();
        business.setId(snapshot.getLong("id"));
        business.setTitulo(snapshot.getString("titulo"));
        business.setNomeEmpresa(snapshot.getString("nomeEmpresa"));
        business.setValorContrato(toBigDecimal(snapshot.get("valorContrato")));
        business.setData(snapshot.getString("data"));
        business.setEstagioDeNegociacao(snapshot.getString("estagioDeNegociacao"));
        business.setClienteId(snapshot.getLong("clienteId"));
        return business;
    }

    private BigDecimal toBigDecimal(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Number number) {
            return BigDecimal.valueOf(number.doubleValue());
        }
        if (value instanceof String stringValue) {
            return new BigDecimal(stringValue);
        }
        throw new IllegalArgumentException("Valor de contrato inválido no Firestore: " + value);
    }
}
