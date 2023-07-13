package com.example.prog3.Service;

import com.example.prog3.model.Employee;
import com.example.prog3.Repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public Optional<Employee> getById(Long id){
        return employeeRepository.findById(id);
    }
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    public Employee getByMatricule(String matricule){
        return employeeRepository.findByMatricule(matricule);
    }
    public Employee getByName(String name){
        return employeeRepository.findByName(name);
    }

    public Employee crupdateEmployee(Employee employee){
        Employee employeeOptional = employeeRepository.findByMatricule(employee.getMatricule());
        if(employeeOptional != null){
            employeeOptional.setName(employee.getName());
            employeeOptional.setLastName(employee.getLastName());
            employeeOptional.setBirthDate(employee.getBirthDate());
            employeeOptional.setMatricule(employee.getMatricule());
            employeeOptional.setEmplImg(employee.getEmplImg());
            return employeeRepository.save(employeeOptional);
        } else {
        return employeeRepository.save(employee);
        }
    }
}
