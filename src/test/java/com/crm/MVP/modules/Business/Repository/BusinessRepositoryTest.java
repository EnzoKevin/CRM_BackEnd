package com.crm.MVP.modules.Business.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.crm.MVP.modules.Business.Entity.Business;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class BusinessRepositoryTest {

    @Test
    void findById_shouldReadStringBackedAmountValue() throws Exception {
        Firestore firestore = mock(Firestore.class);
        CollectionReference collectionReference = mock(CollectionReference.class);
        DocumentReference documentReference = mock(DocumentReference.class);
        ApiFuture<DocumentSnapshot> apiFuture = mock(ApiFuture.class);
        DocumentSnapshot snapshot = mock(DocumentSnapshot.class);

        when(firestore.collection("businesses")).thenReturn(collectionReference);
        when(collectionReference.document("1")).thenReturn(documentReference);
        when(documentReference.get()).thenReturn(apiFuture);
        when(apiFuture.get()).thenReturn(snapshot);
        when(snapshot.exists()).thenReturn(true);
        when(snapshot.getLong("id")).thenReturn(1L);
        when(snapshot.getString("titulo")).thenReturn("Título");
        when(snapshot.getString("nomeEmpresa")).thenReturn("Empresa");
        when(snapshot.get("valorContrato")).thenReturn("1250.50");
        when(snapshot.getString("data")).thenReturn("2026-07-31");
        when(snapshot.getString("estagioDeNegociacao")).thenReturn("Em negociação");
        when(snapshot.getLong("clienteId")).thenReturn(7L);
        when(snapshot.getDouble("valorContrato"))
                .thenThrow(new ClassCastException("class java.lang.String cannot be cast to class java.lang.Number"));

        BusinessRepository repository = new BusinessRepository(firestore);

        Optional<Business> result = repository.findById(1L);

        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(1250.50), result.orElseThrow().getValorContrato());
    }
}
