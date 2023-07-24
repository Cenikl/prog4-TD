package com.example.prog3.Service;

import com.example.prog3.Repository.PhoneRepository;
import com.example.prog3.model.Employee;
import com.example.prog3.model.Enterprise;
import com.example.prog3.model.Phone;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class PhoneService {
    private final PhoneRepository repository;

    public Phone getByEmployee(Employee employee){return repository.findByPhoneEmployee(employee);}

    public List<Phone> getAllByEmployee(Employee employee){return repository.findPhonesByPhoneEmployee(employee);}

    public void createPhoneNumberEmployee(String phoneNumbers, Employee employee){
        String[] numberList = phoneNumbers.split(",");
        for (String phoneNumber : numberList) {
            Phone phone = new Phone();
            phone.setPhoneEmployee(employee);
            phone.setPhoneNumber(phoneNumber);
            repository.save(phone);
        }
    }
    public void createPhoneNumberEnterprise(String phoneNumbers, Enterprise enterprise){
        String[] numberList = phoneNumbers.split(",");
        for (String phoneNumber : numberList) {
            Phone phone = new Phone();
            phone.setPhoneEnterprise(enterprise);
            phone.setPhoneNumber(phoneNumber);
            repository.save(phone);
        }
    }
    public void updatePhoneNumber(String phoneNumbers,Employee employee){
        if(phoneNumbers == "" || phoneNumbers == null){
            //do nothing
        }else {
            String[] numberList = phoneNumbers.split(",");
            for (String phoneNumber : numberList) {
                Phone phone = new Phone();
                phone.setPhoneEmployee(employee);
                phone.setPhoneNumber(phoneNumber);
                repository.save(phone);
            }
        }
    }
    public void updatePhoneNumberEnterprise(String phoneNumbers,Enterprise enterprise){
        if(phoneNumbers == "" || phoneNumbers == null){
            //do nothing
        }else {
            String[] numberList = phoneNumbers.split(",");
            for (String phoneNumber : numberList) {
                Phone phone = new Phone();
                phone.setPhoneEnterprise(enterprise);
                phone.setPhoneNumber(phoneNumber);
                repository.save(phone);
            }
        }
    }
}
