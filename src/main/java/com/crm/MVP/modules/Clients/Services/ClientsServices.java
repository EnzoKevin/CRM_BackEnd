package com.crm.MVP.modules.Clients.Services;

import com.crm.MVP.modules.Clients.Entity.Contacts;
import com.crm.MVP.modules.Clients.Repository.ClientsRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ClientsServices {

    private final ClientsRepository clientsRepository;

    public ClientsServices(ClientsRepository clientsRepository) {
        this.clientsRepository = clientsRepository;
    }

    public Contacts create(Contacts contacts) {
        return clientsRepository.save(contacts);
    }

    public List<Contacts> findAll() {
        return clientsRepository.findAll();
    }

    public Contacts findById(Long id) {
        return clientsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contato não encontrado"));
    }

    public Contacts update(Long id, Contacts updatedContacts) {
        Contacts contacts = findById(id);
        updatedContacts.setId(contacts.getId());
        return clientsRepository.save(updatedContacts);
    }

    public void delete(Long id) {
        findById(id);
        clientsRepository.deleteById(id);
    }
}
