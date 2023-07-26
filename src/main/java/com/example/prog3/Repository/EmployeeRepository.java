package com.example.prog3.Repository;

import com.example.prog3.model.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Employee findByMatricule(String matricule);
    Employee findByEmailPro(String emailPro);

    List<Employee> findBySex(Employee.Sex sex);
    List<Employee> findByRoleContainingIgnoreCase(String role);
    @Query(value = "select * from employee "+
            "where ( cast(:startDate as date) is null or employement_date >= :startDate) "+
            "and ( cast(:endDate as date) is null or departure_date <= :endDate)", nativeQuery = true)
    List<Employee> findEmployeesByDateRange(LocalDate startDate,LocalDate endDate);

    @Query(value = "select e.* from employee e "+
            "JOIN phone p on e.id = p.phone_employee " +
            "where (:countryCode is null or p.country_code = :countryCode) ", nativeQuery = true)
    List<Employee> findEmployeesByCountryCode(String countryCode);
}
