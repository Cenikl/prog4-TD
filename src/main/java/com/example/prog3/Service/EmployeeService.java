package com.example.prog3.Service;

import com.example.prog3.model.Employee;
import com.example.prog3.Repository.EmployeeRepository;
import com.example.prog3.utils.MatriculeGenerator;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public Employee getByEmailPro(String emailPro){return employeeRepository.findByEmailPro(emailPro);}

    public List<Employee> filterEmployees(String name,String lastName,String sex,String fonction,String eDate,String dDate){
        List<Employee> allEmployees = getAllEmployees();
        List<Employee> filteredEmployees = new ArrayList<>();

        for (Employee employee: allEmployees) {
            if(
                    (name == null || employee.getFirstName().contains(name))
                    && (lastName == null || employee.getLastName().contains(lastName))
                    && (sex == null || employee.getSex().toString().contains(sex))
                    && (fonction == null || employee.getRole().contains(fonction))
                    && (eDate == null || employee.getEmployementDate().contains(eDate))
                    && (dDate == null || employee.getDepartureDate().contains(dDate)))   {
                filteredEmployees.add(employee);
            }
        }
        return filteredEmployees;
    }

    public Employee createEmployee(
            String firstName,
            String lastName,
            String birthDate,
            String sex,
            String csp,
            String address,
            String emailPro,
            String emailPerso,
            String role,
            Integer child,
            String eDate,
            String dDate,
            String cnaps,
            byte[] emplImg){
        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setBirthDate(birthDate);
        employee.setMatricule(MatriculeGenerator.generateMatricule(getAllEmployees().size() == 0 ? 0 : getAllEmployees().size()));
        employee.setEmplImg(emplImg);
        employee.setSex(Employee.Sex.valueOf(sex));
        employee.setCsp(Employee.Csp.valueOf(csp));
        employee.setAddress(address);
        employee.setEmailPro(emailPro);
        employee.setEmailPerso(emailPerso);
        employee.setRole(role);
        employee.setChild(child);
        employee.setEmployementDate(eDate);
        employee.setDepartureDate(dDate);
        employee.setCnaps(cnaps);
        return employeeRepository.save(employee);
    };

    public Employee crupdateEmployee(
            String matricule,
            String name,
            String lastName,
            String birthDate,
            String sex,
            String csp,
            String address,
            String emailPro,
            String emailPerso,
            String role,
            Integer child,
            String eDate,
            String dDate,
            String cnaps,
            byte[] image){
        Employee employee = getByMatricule(matricule);
        employee.setCnaps(cnaps);
        employee.setFirstName(name);
        employee.setLastName(lastName);
        employee.setBirthDate(birthDate);
        employee.setEmplImg(image);
        employee.setSex(Employee.Sex.valueOf(sex));
        employee.setCsp(Employee.Csp.valueOf(csp));
        employee.setAddress(address);
        employee.setEmailPro(emailPro);
        employee.setEmailPerso(emailPerso);
        employee.setRole(role);
        employee.setChild(child);
        employee.setEmployementDate(eDate);
        employee.setDepartureDate(dDate);
        return employeeRepository.save(employee);
        }
}
