package com.crm.MVP.modules.Business.Service;

import com.crm.MVP.modules.Business.Entity.Business;
import com.crm.MVP.modules.Business.Repository.BusinessRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BusinessService {

    private final BusinessRepository businessRepository;

    public BusinessService(BusinessRepository businessRepository) {
        this.businessRepository = businessRepository;
    }

    public Business create(Business business) {
        return businessRepository.save(business);
    }

    public List<Business> findAll() {
        return businessRepository.findAll();
    }

    public Business findById(Long id) {
        return businessRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Negócio não encontrado"));
    }

    public Business update(Long id, Business updatedBusiness) {
        Business business = findById(id);
        updatedBusiness.setId(business.getId());
        return businessRepository.save(updatedBusiness);
    }

    public void delete(Long id) {
        findById(id);
        businessRepository.deleteById(id);
    }
}
