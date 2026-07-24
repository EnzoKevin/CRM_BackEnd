package com.crm.MVP.modules.Business.Mapper;

import com.crm.MVP.modules.Business.DTO.BusinessRequestDTO;
import com.crm.MVP.modules.Business.DTO.BusinessResponseDTO;
import com.crm.MVP.modules.Business.Entity.Business;
import org.springframework.stereotype.Component;

@Component
public class BusinessMapper {

    public Business toEntity(BusinessRequestDTO dto) {
        Business business = new Business();
        updateEntity(dto, business);
        return business;
    }

    public BusinessResponseDTO toResponseDTO(Business business) {
        return new BusinessResponseDTO(
                business.getId(),
                business.getTitulo(),
                business.getNomeEmpresa(),
                business.getValorContrato(),
                business.getData(),
                business.getEstagioDeNegociacao(),
                business.getClienteId()
        );
    }

    public void updateEntity(BusinessRequestDTO dto, Business business) {
        business.setTitulo(dto.getTitulo());
        business.setNomeEmpresa(dto.getNomeEmpresa());
        business.setValorContrato(dto.getValorContrato());
        business.setData(dto.getData());
        business.setEstagioDeNegociacao(dto.getEstagioDeNegociacao());
        business.setClienteId(dto.getClienteId());
    }
}
