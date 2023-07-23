package com.example.prog3.Service;

import com.example.prog3.model.Employee;
import com.example.prog3.Repository.EmployeeRepository;
import com.example.prog3.utils.MatriculeGenerator;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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

    public Employee createEmployee(String firstName,String lastName,String birthDate,byte[] emplImg){
        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setBirthDate(birthDate);
        employee.setMatricule(MatriculeGenerator.generateMatricule(getAllEmployees().size() == 0 ? 0 : getAllEmployees().size()));
        employee.setEmplImg(emplImg);
        return employeeRepository.save(employee);
    };

    public Employee crupdateEmployee(String matricule,String name,String lastName,String birthDate,byte[] image){
        Employee employee = getByMatricule(matricule);
        employee.setFirstName(name);
        employee.setLastName(lastName);
        employee.setBirthDate(birthDate);
        employee.setEmplImg(image);
        return employeeRepository.save(employee);
        }
}
