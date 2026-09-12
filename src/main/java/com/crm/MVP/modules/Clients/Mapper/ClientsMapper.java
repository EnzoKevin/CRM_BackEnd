package com.crm.MVP.modules.Clients.Mapper;

import com.crm.MVP.modules.Clients.DTO.ClientsRequestDTO;
import com.crm.MVP.modules.Clients.DTO.ClientsResponseDTO;
import com.crm.MVP.modules.Clients.Entity.Contacts;
import org.springframework.stereotype.Component;

@Component
public class ClientsMapper {

    public Contacts toEntity(ClientsRequestDTO dto) {
        Contacts contacts = new Contacts();
        updateEntity(dto, contacts);
        return contacts;
    }

    public ClientsResponseDTO toResponseDTO(Contacts contacts) {
        return new ClientsResponseDTO(
                contacts.getId(),
                contacts.getNome(),
                contacts.getEmail(),
                contacts.getTelefone(),
                contacts.getEmpresa(),
                contacts.getStatus());
    }

    public void updateEntity(ClientsRequestDTO dto, Contacts contacts) {
        contacts.setNome(dto.getNome());
        contacts.setEmail(dto.getEmail());
        contacts.setTelefone(dto.getTelefone());
        contacts.setEmpresa(dto.getEmpresa());
        contacts.setStatus(dto.getStatus());
    }
}
