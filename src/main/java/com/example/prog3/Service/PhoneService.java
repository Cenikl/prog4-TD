package com.example.prog3.Service;

import com.example.prog3.Repository.PhoneRepository;
import com.example.prog3.model.Employee;
import com.example.prog3.model.Phone;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class PhoneService {
    private final PhoneRepository repository;

    public void createPhoneNumber(String phoneNumbers, Employee employee){
        Phone phone = new Phone();
        phone.setPhoneEmployee(employee);
        String[] numberList = phoneNumbers.split(",");
        for (String phoneNumber : numberList) {
            phone.setPhoneNumber(phoneNumber);
            repository.save(phone);
        }
    }

    public void updatePhoneNumber(String phoneNumbers){}
}
