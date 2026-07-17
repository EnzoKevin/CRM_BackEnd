package com.crm.MVP.modules.User.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRequestDTO {

    @NotBlank(message = "O nome é obrigatório.")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
    private String nome;

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "Informe um e-mail válido.")
    @Size(max = 255, message = "O e-mail deve ter no máximo 255 caracteres.")
    private String email;

    @NotNull(message = "Informe se o usuário é administrador.")
    private Boolean admin;

    @NotBlank(message = "O idioma é obrigatório.")
    @Size(max = 10, message = "O idioma deve ter no máximo 10 caracteres.")
    private String idioma;

    @NotBlank(message = "O fuso horário é obrigatório.")
    @Size(max = 50, message = "O fuso horário deve ter no máximo 50 caracteres.")
    private String fusoHorario;

    @NotBlank(message = "O formato de data é obrigatório.")
    @Size(max = 20, message = "O formato de data deve ter no máximo 20 caracteres.")
    private String formatoDeData;

    @NotNull(message = "Informe a preferência de notificações por e-mail.")
    private Boolean notificacoesEmail;

    @NotNull(message = "Informe a preferência de alertas web.")
    private Boolean alertasWeb;

    @NotNull(message = "Informe a preferência de resumo semanal.")
    private Boolean resumoSemanal;
}
