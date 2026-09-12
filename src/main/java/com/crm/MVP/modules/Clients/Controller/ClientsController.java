package com.crm.MVP.modules.Clients.Controller;

import com.crm.MVP.modules.Clients.DTO.ClientsRequestDTO;
import com.crm.MVP.modules.Clients.DTO.ClientsResponseDTO;
import com.crm.MVP.modules.Clients.Entity.Contacts;
import com.crm.MVP.modules.Clients.Mapper.ClientsMapper;
import com.crm.MVP.modules.Clients.Services.ClientsServices;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Clients")
public class ClientsController {

    private final ClientsServices clientsService;
    private final ClientsMapper clientsMapper;

    public ClientsController(ClientsServices clientsService, ClientsMapper clientsMapper) {
        this.clientsService = clientsService;
        this.clientsMapper = clientsMapper;
    }

    @PostMapping
    public ResponseEntity<ClientsResponseDTO> create(@Valid @RequestBody ClientsRequestDTO dto) {
        Contacts contacts = clientsService.create(clientsMapper.toEntity(dto));
        return ResponseEntity.created(URI.create("/Clients/" + contacts.getId()))
                .body(clientsMapper.toResponseDTO(contacts));
    }

    @GetMapping
    public List<ClientsResponseDTO> findAll() {
        return clientsService.findAll().stream()
                .map(clientsMapper::toResponseDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public ClientsResponseDTO findById(@PathVariable Long id) {
        return clientsMapper.toResponseDTO(clientsService.findById(id));
    }

    @PutMapping("/{id}")
    public ClientsResponseDTO update(@PathVariable Long id, @Valid @RequestBody ClientsRequestDTO dto) {
        Contacts contacts = clientsMapper.toEntity(dto);
        return clientsMapper.toResponseDTO(clientsService.update(id, contacts));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clientsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
