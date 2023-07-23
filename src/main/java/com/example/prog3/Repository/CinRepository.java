package com.example.prog3.Repository;

import com.example.prog3.model.Cin;
import com.example.prog3.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CinRepository extends JpaRepository<Cin,Long> {
    Cin findByCinEmployee(Employee employee);
}
