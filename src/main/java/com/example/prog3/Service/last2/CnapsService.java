package com.example.prog3.Service.last2;

import com.example.prog3.Repository.last2.CnapsRepository;
import com.example.prog3.model.last2.Cnaps;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
@AllArgsConstructor
@Transactional
public class CnapsService {
    private final CnapsRepository employeeRepository;

    public List<Cnaps> getAll(){
        return employeeRepository.findAll();
    }
}
