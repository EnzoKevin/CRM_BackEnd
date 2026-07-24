package com.crm.MVP.modules.Business.DTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BusinessRequestDTO {

    @NotBlank(message = "O título é obrigatório.")
    @Size(max = 100, message = "O título deve ter no máximo 100 caracteres.")
    private String titulo;

    @NotBlank(message = "O nome da empresa é obrigatório.")
    @Size(max = 150, message = "O nome da empresa deve ter no máximo 150 caracteres.")
    private String nomeEmpresa;

    @NotNull(message = "O valor do contrato é obrigatório.")
    @DecimalMin(value = "0.01", message = "O valor do contrato deve ser maior que zero.")
    private BigDecimal valorContrato;

    @NotBlank(message = "A data é obrigatória.")
    @Size(max = 20, message = "A data deve ter no máximo 20 caracteres.")
    private String data;

    @NotBlank(message = "O estágio de negociação é obrigatório.")
    @Size(max = 50, message = "O estágio de negociação deve ter no máximo 50 caracteres.")
    private String estagioDeNegociacao;

    private Long clienteId;
}
