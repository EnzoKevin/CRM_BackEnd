package com.crm.MVP.modules.Business.Entity;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Business {

    private Long id;
    private String titulo;
    private String contact;
    private String nomeEmpresa;
    private BigDecimal valorContrato;
    private String data;
    private String estagioDeNegociacao;
    private Long clienteId;
}
