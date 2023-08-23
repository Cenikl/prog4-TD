package com.example.prog3.Service.last1;

import com.example.prog3.Repository.RepositoryFacadeImpl;
import com.example.prog3.Repository.last2.CnapsRepository;
import com.example.prog3.model.last1.Employee;
import com.example.prog3.Repository.last1.EmployeeRepository;
import com.example.prog3.model.last1.Phone;
import com.example.prog3.model.last2.Cnaps;
import com.example.prog3.utils.MatriculeGenerator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor
@Transactional
public class EmployeeService {
  private final EmployeeRepository employeeRepository;
  private final CnapsRepository cnapsRepository;
  private final RepositoryFacadeImpl repository;
  @PersistenceContext
  private EntityManager entityManager;

  public void exportToCsv(List<Employee> employees, PhoneService phoneService,
                          HttpServletResponse response) throws IOException {
    String header =
        "First Name,Last Name,Birth Date,Matricule,Gender,Csp,Address,Email Professionel,Email Personnel,Fonction,Nombres dEnfants,Employement Date,Departure Date,Numéro Cnaps,Telephone,Numéro Cin\n";
    try (OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream())) {
      for (Employee employee : employees) {
        writer.write(header);
        String numbers = "";
        for (Phone phone : phoneService.getAllByEmployee(employee)) {
          numbers += "," + phone.getPhoneNumber();
        }
        writer.write(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s\n",
            employee.getFirstName(), employee.getLastName(), employee.getBirthDate(),
            employee.getMatricule(), employee.getSex(), employee.getCsp(), employee.getAddress(),
            employee.getEmailPro(), employee.getEmailPerso(), employee.getRole(),
            employee.getChild(),
            employee.getEmployementDate(), employee.getDepartureDate(), employee.getCnaps(),
            numbers,
            employee.getCin()));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public Optional<Employee> getById(Long id) {
    return employeeRepository.findById(id);
  }

  @Transactional
  public Employee getByMatriculeWithCnaps(String matricule) {
    return repository.findByMatricule(matricule);
  }

  // Does an employee should always have a cnaps ?
  public Employee getByMatricule(String matricule) {
    Cnaps cnaps = cnapsRepository.findByMatricule(matricule);
    if (cnaps != null) {
      return employeeRepository.findByMatricule(cnaps.getMatricule());
    } else {
      return employeeRepository.findByMatricule(matricule);
    }
  }

  public Employee getByEmailPro(String emailPro) {
    return employeeRepository.findByEmailPro(emailPro);
  }

  public List<Employee> findEmployeesByFirstNameAndLastName(String firstName, String lastName) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
    Root<Employee> root = criteriaQuery.from(Employee.class);
    List<Predicate> predicates = new ArrayList<>();

    if (firstName != null && !firstName.isEmpty()) {
      predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")),
          "%" + firstName.toLowerCase() + "%"));
    }
    if (lastName != null && !lastName.isEmpty()) {
      predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")),
          "%" + lastName.toLowerCase() + "%"));
    }
    criteriaQuery.where(predicates.toArray(new Predicate[0]));
    return entityManager.createQuery(criteriaQuery).getResultList();
  }

  public List<Employee> getFilteredEmployees(String firstName, String lastName, String sex,
                                             String role, LocalDate startDate, LocalDate endDate,
                                             String countryCode, String sort) {
    List<Employee> employees = new ArrayList<>();
    List<Employee> employeesMock = new ArrayList<>();
    if (endDate == null) {
      endDate = LocalDate.now();
    }
    if (firstName != null && !firstName.isEmpty() || lastName != null && !lastName.isEmpty()) {
      employees.addAll(findEmployeesByFirstNameAndLastName(firstName, lastName));
      employeesMock.addAll(findEmployeesByFirstNameAndLastName(firstName, lastName));
    }
    if (sex != null && !sex.isEmpty()) {
      if (!employees.isEmpty()) {
        for (Employee employee : employeesMock) {
          for (Employee employee1 : employeeRepository.findBySex(Employee.Sex.valueOf(sex))) {
            if (employee.getSex() != employee1.getSex()) {
              employees.remove(employee);
            }
          }
        }
      } else {
        employees.addAll(employeeRepository.findBySex(Employee.Sex.valueOf(sex)));
      }
    }
    if (role != null && !role.isEmpty()) {
      if (!employees.isEmpty()) {
        for (Employee employee : employeesMock) {
          for (Employee employee1 : employeeRepository.findByRoleContainingIgnoreCase(role)) {
            if (employee.getRole() != employee1.getRole()) {
              employees.remove(employee);
            }
          }
        }
      } else {
        employees.addAll(employeeRepository.findByRoleContainingIgnoreCase(role));
      }
    }
    if (startDate != null && endDate != null) {
      if (!employees.isEmpty()) {
        for (Employee employee : employeesMock) {
          for (Employee employee1 : employeeRepository.findEmployeesByDateRange(startDate,
              endDate)) {
            if (employee.getEmployementDate() != employee1.getEmployementDate() &&
                employee.getDepartureDate() != employee1.getDepartureDate()) {
              employees.remove(employee);
            }
          }
        }
      } else {
        employees.addAll(employeeRepository.findEmployeesByDateRange(startDate, endDate));
      }
    }
    if (countryCode != null && !countryCode.isEmpty()) {
      if (!employees.isEmpty()) {
        for (Employee employee : employeesMock) {
          for (Employee employee1 : employeeRepository.findEmployeesByCountryCode(countryCode)) {
            if (employee.getId() != employee1.getId()) {
              employees.remove(employee);
            }
          }
        }
      } else {
        employees.addAll(employeeRepository.findEmployeesByCountryCode(countryCode));
      }
    }

    if (employees.isEmpty() && employeesMock.isEmpty()) {
      return employeeRepository.findAll(sortEmployees(sort));
    }
    if (sort != null && !sort.isEmpty()) {
      sortingCriteria(employees, sort);
    }
    return employees;
  }

  public void sortingCriteria(List<Employee> employees, String sort) {
    if (Objects.equals(sort, "firstNameAsc")) {
      employees.sort(Comparator.comparing(Employee::getFirstName));
    } else if (Objects.equals(sort, "firstNameDesc")) {
      employees.sort(Comparator.comparing(Employee::getFirstName).reversed());
    } else if (Objects.equals(sort, "lastNameAsc")) {
      employees.sort(Comparator.comparing(Employee::getLastName));
    } else if (Objects.equals(sort, "lastNameDesc")) {
      employees.sort(Comparator.comparing(Employee::getLastName).reversed());
    } else if (Objects.equals(sort, "genderAsc")) {
      employees.sort(Comparator.comparing(Employee::getSex));
    } else if (Objects.equals(sort, "genderDesc")) {
      employees.sort(Comparator.comparing(Employee::getSex).reversed());
    } else if (Objects.equals(sort, "roleAsc")) {
      employees.sort(Comparator.comparing(Employee::getRole));
    } else if (Objects.equals(sort, "roleDesc")) {
      employees.sort(Comparator.comparing(Employee::getRole).reversed());
    } else if (Objects.equals(sort, "dDateAsc")) {
      employees.sort(Comparator.comparing(Employee::getDepartureDate));
    } else if (Objects.equals(sort, "dDateDesc")) {
      employees.sort(Comparator.comparing(Employee::getDepartureDate).reversed());
    } else if (Objects.equals(sort, "eDateAsc")) {
      employees.sort(Comparator.comparing(Employee::getEmployementDate));
    } else if (Objects.equals(sort, "eDateDesc")) {
      employees.sort(Comparator.comparing(Employee::getEmployementDate).reversed());
    } else {
      employees.sort(Comparator.comparing(Employee::getId));
    }
  }

  public Sort sortEmployees(String sort) {
    if (Objects.equals(sort, "firstNameAsc")) {
      return Sort.by(Sort.Direction.ASC, "firstName");
    }
    if (Objects.equals(sort, "firstNameDesc")) {
      return Sort.by(Sort.Direction.DESC, "firstName");
    }
    if (Objects.equals(sort, "lastNameAsc")) {
      return Sort.by(Sort.Direction.ASC, "lastName");
    }
    if (Objects.equals(sort, "lastNameDesc")) {
      return Sort.by(Sort.Direction.DESC, "lastName");
    }
    if (Objects.equals(sort, "genderAsc")) {
      return Sort.by(Sort.Direction.ASC, "sex");
    }
    if (Objects.equals(sort, "genderDesc")) {
      return Sort.by(Sort.Direction.DESC, "sex");
    }
    if (Objects.equals(sort, "roleAsc")) {
      return Sort.by(Sort.Direction.ASC, "role");
    }
    if (Objects.equals(sort, "roleDesc")) {
      return Sort.by(Sort.Direction.DESC, "role");
    }
    if (Objects.equals(sort, "dDateAsc")) {
      return Sort.by(Sort.Direction.ASC, "employementDate");
    }
    if (Objects.equals(sort, "dDateDesc")) {
      return Sort.by(Sort.Direction.DESC, "employementDate");
    }
    if (Objects.equals(sort, "eDateAsc")) {
      return Sort.by(Sort.Direction.ASC, "departureDate");
    }
    if (Objects.equals(sort, "eDateDesc")) {
      return Sort.by(Sort.Direction.DESC, "departureDate");
    }
    return Sort.by(Sort.Direction.ASC, "id");
  }
  public void createEmployee(String firstName, String lastName, LocalDate birthDate, String sex,
                             String csp, String address, String emailPro, String emailPerso,
                             String role, Integer child, LocalDate eDate, LocalDate dDate,
                             String cnaps, String cin, byte[] emplImg) {
    Employee employee = Employee.builder()
        .firstName(firstName)
        .lastName(lastName)
        .birthDate(birthDate)
        .emplImg(emplImg)
        .matricule(MatriculeGenerator.generateMatricule(
            employeeRepository.findAll().size() == 0 ? 0 : employeeRepository.findAll().size()))
        .sex(Employee.Sex.valueOf(sex))
        .csp(Employee.Csp.valueOf(csp))
        .address(address)
        .emailPerso(emailPerso)
        .cin(cin)
        .cnaps(cnaps)
        .emailPro(emailPro)
        .role(role)
        .child(child)
        .employementDate(eDate)
        .departureDate(dDate)
        .build();
    employeeRepository.save(employee);
  }

  public void crupdateEmployee(String matricule, String name, String lastName,
                               LocalDate birthDate, String sex, String csp, String address,
                               String emailPro, String emailPerso, String role, Integer child,
                               LocalDate eDate, LocalDate dDate, String cin, byte[] image) {

    Employee employee = Employee.builder()
        .firstName(name)
        .lastName(lastName)
        .birthDate(birthDate)
        .matricule(matricule)
        .cin(cin)
        .emplImg(image)
        .sex(Employee.Sex.valueOf(sex))
        .csp(Employee.Csp.valueOf(csp))
        .address(address)
        .emailPerso(emailPerso)
        .emailPro(emailPro)
        .role(role)
        .child(child)
        .employementDate(eDate)
        .departureDate(dDate)
        .build();
    employeeRepository.save(employee);
  }
}
