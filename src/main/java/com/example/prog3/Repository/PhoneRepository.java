package com.example.prog3.Repository;

import com.example.prog3.model.Employee;
import com.example.prog3.model.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneRepository extends JpaRepository<Phone,Long> {
    Phone findByPhoneEmployee(Employee employee);

    List<Phone> findPhonesByPhoneEmployee(Employee employee);
}
