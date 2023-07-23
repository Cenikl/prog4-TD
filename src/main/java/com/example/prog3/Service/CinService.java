package com.example.prog3.Service;

import com.example.prog3.Repository.CinRepository;
import com.example.prog3.model.Cin;
import com.example.prog3.model.Employee;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class CinService {
    private final CinRepository repository;

    public Cin getByEmployee(Employee employee){return repository.findByCinEmployee(employee);}
    public void createCin(String cinNumber, String cinDate, String cinLocation, Employee employee){
        Cin cin = new Cin();
        cin.setCinNumber(cinNumber);
        cin.setIssueDate(cinDate);
        cin.setIssueLocation(cinLocation);
        cin.setCinEmployee(employee);
        repository.save(cin);
    }
    public void updateCin(Employee employee,String cinNumber,String cinDate,String cinLocation){
        Cin cin = getByEmployee(employee);
        cin.setCinNumber(cinNumber);
        cin.setIssueDate(cinDate);
        cin.setIssueLocation(cinLocation);
        repository.save(cin);
    }
}
