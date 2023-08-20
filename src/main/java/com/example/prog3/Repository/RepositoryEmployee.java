package com.example.prog3.Repository;

import com.example.prog3.Repository.last1.EmployeeRepository;
import com.example.prog3.Repository.last2.CnapsRepository;
import com.example.prog3.model.last1.Employee;
import com.example.prog3.model.last2.Cnaps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface RepositoryEmployee{
    Employee findByMatricule(String matricule);
}
