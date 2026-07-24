package com.crm.MVP.modules.Business.Controller;

import com.crm.MVP.modules.Business.DTO.BusinessRequestDTO;
import com.crm.MVP.modules.Business.DTO.BusinessResponseDTO;
import com.crm.MVP.modules.Business.Entity.Business;
import com.crm.MVP.modules.Business.Mapper.BusinessMapper;
import com.crm.MVP.modules.Business.Service.BusinessService;
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
@RequestMapping("/businesses")
public class BusinessController {

    private final BusinessService businessService;
    private final BusinessMapper businessMapper;

    public BusinessController(BusinessService businessService, BusinessMapper businessMapper) {
        this.businessService = businessService;
        this.businessMapper = businessMapper;
    }

    @PostMapping
    public ResponseEntity<BusinessResponseDTO> create(@Valid @RequestBody BusinessRequestDTO dto) {
        Business business = businessService.create(businessMapper.toEntity(dto));
        return ResponseEntity.created(URI.create("/businesses/" + business.getId()))
                .body(businessMapper.toResponseDTO(business));
    }

    @GetMapping
    public List<BusinessResponseDTO> findAll() {
        return businessService.findAll().stream()
                .map(businessMapper::toResponseDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public BusinessResponseDTO findById(@PathVariable Long id) {
        return businessMapper.toResponseDTO(businessService.findById(id));
    }

    @PutMapping("/{id}")
    public BusinessResponseDTO update(@PathVariable Long id, @Valid @RequestBody BusinessRequestDTO dto) {
        Business business = businessMapper.toEntity(dto);
        return businessMapper.toResponseDTO(businessService.update(id, business));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        businessService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
