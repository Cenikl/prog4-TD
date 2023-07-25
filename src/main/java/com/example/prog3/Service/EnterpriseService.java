package com.example.prog3.Service;

import com.example.prog3.Repository.EnterpriseRepository;
import com.example.prog3.model.Enterprise;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class EnterpriseService {
    private EnterpriseRepository repository;

    public Enterprise getEnterprise(){
        return repository.findAll().get(0);
    }
    public void updateEnterprise(){
    }
}
