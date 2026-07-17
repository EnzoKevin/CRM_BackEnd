package com.crm.MVP.modules.User.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseDTO {

    private final Long id;
    private final String nome;
    private final String email;
    private final boolean admin;
    private final String idioma;
    private final String fusoHorario;
    private final String formatoDeData;
    private final boolean notificacoesEmail;
    private final boolean alertasWeb;
    private final boolean resumoSemanal;
}
