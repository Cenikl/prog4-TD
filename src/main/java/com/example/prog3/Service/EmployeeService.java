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

    public Optional<Employee> getById(String id){
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

    public Employee crupdateEmployee(Employee employee){
        Employee employeeOptional = getById(employee.getId()).get();
        if(employeeOptional != null){
            employeeOptional.setFirstName(employee.getFirstName());
            employeeOptional.setLastName(employee.getLastName());
            employeeOptional.setBirthDate(employee.getBirthDate());
            employeeOptional.setMatricule(
                    MatriculeGenerator.generateMatricule(
                            getAllEmployees().size() == 0 ? 0 : getAllEmployees().size()+1
                    )
            );
            employeeOptional.setEmplImg(employee.getEmplImg());
            return employeeRepository.save(employeeOptional);
        } else {
        return employeeRepository.save(employee);
        }
    }
}
