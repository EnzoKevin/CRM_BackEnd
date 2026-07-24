package com.crm.MVP.modules.Business.DTO;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BusinessResponseDTO {

    private final Long id;
    private final String titulo;
    private final String nomeEmpresa;
    private final BigDecimal valorContrato;
    private final String data;
    private final String estagioDeNegociacao;
    private final Long clienteId;
}
