package com.crm.MVP.modules.Clients.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClientsResponseDTO {
    private final long id;
    private final String nome;
    private final String email;
    private final String telefone;
    private final String empresa;
    private final String Status;
    private final String data;
    private final String value;
    private final String responsible;
}
