package com.example.prog3.Repository;

import com.example.prog3.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Employee findByMatricule(String matricule);
    Employee findByName(String name);
}
