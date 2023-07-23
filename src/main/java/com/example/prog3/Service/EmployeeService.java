package com.example.prog3.Service;

import com.example.prog3.model.Employee;
import com.example.prog3.Repository.EmployeeRepository;
import com.example.prog3.utils.MatriculeGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

    public void sortEmployees(List<Employee> employees, String sort){
        switch (sort) {
            case "firstNameAsc":
                Collections.sort(employees, (e1,e2) -> e2.getFirstName().compareToIgnoreCase(e1.getFirstName()));
                break;
            case "firstNameDesc":
                Collections.sort(employees, (e1,e2) -> e2.getFirstName().compareToIgnoreCase(e1.getFirstName()));
            case "lastNameAsc":
                Collections.sort(employees, (e1,e2) -> e1.getLastName().compareToIgnoreCase(e2.getLastName()));
                break;
            case "lastNameDesc":
                Collections.sort(employees, (e1,e2) -> e2.getLastName().compareToIgnoreCase(e1.getLastName()));
                break;
            case "genderAsc":
                Collections.sort(employees, (e1,e2) -> e1.getSex().toString().compareToIgnoreCase(e2.getSex().toString()));
                break;
            case "genderDesc":
                Collections.sort(employees, (e1,e2) -> e2.getSex().toString().compareToIgnoreCase(e1.getSex().toString()));
                break;
            case "roleAsc":
                Collections.sort(employees, (e1,e2) -> e1.getRole().compareToIgnoreCase(e2.getRole()));
                break;
            case "roleDesc":
                Collections.sort(employees, (e1,e2) -> e2.getRole().compareToIgnoreCase(e1.getRole()));
                break;
            case "eDateAsc":
                Collections.sort(employees, (e1,e2) -> e1.getEmployementDate().compareToIgnoreCase(e2.getEmployementDate()));
                break;
            case "eDateDesc":
                Collections.sort(employees, (e1,e2) -> e2.getEmployementDate().compareToIgnoreCase(e1.getEmployementDate()));
                break;
            case "dDateAsc":
                Collections.sort(employees, (e1,e2) -> e1.getDepartureDate().compareToIgnoreCase(e2.getDepartureDate()));
                break;
            case "dDateDesc":
                Collections.sort(employees, (e1,e2) -> e2.getDepartureDate().compareToIgnoreCase(e1.getDepartureDate()));
                break;
            default:
                return;
        }
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
