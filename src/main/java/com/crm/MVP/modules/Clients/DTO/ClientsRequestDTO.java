package com.crm.MVP.modules.Clients.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientsRequestDTO {

    @NotBlank(message = "O Id do cliente não pode ser nulo")
    @Size(max = 100, message = "O Id do cliente deve ter no máximo 100 caracteres.")
    private long id;

    @NotBlank(message = "O nome do cliente não pode ser nulo")
    @Size(max = 100, message = "O nome do cliente deve ter no máximo 100 caracteres.")
    private String nome;

    @NotBlank(message = "O email do cliente não pode ser nulo")
    @Size(max = 100, message = "O email do cliente deve ter no máximo 100 caracteres.")
    private String email;

    @NotBlank(message = "O telefone do cliente não pode ser nulo")
    @Size(max = 100, message = "O telefone do cliente deve ter no máximo 100 caracteres.")
    private String telefone;

    @NotBlank(message = "O nome da empresa do cliente não pode ser nulo")
    @Size(max = 100, message = "O nome da empresa do cliente deve ter no máximo 100 caracteres.")
    private String empresa;

    @NotBlank(message = "O status do cliente não pode ser nulo")
    @Size(max = 100, message = "O status do cliente deve ter no máximo 100 caracteres.")
    private String Status;

    @NotBlank (message = "A data do cliente não pode ser nula")
    @Size(max = 100, message = "A data do cliente deve ter no máximo 100 caracteres.")
    private String data;

    @NotBlank (message = "O valor do cliente não pode ser nulo")
    @Size(max = 100, message = "O valor do cliente deve ter no máximo 100 caracteres.")
    private String value;

    @NotBlank (message = "O responsável do cliente não pode ser nulo")
    @Size(max = 100, message = "O responsável do cliente deve ter no máximo 100 caracteres.")
    private String responsible;

}
