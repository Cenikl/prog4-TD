package com.example.prog3.Controller;

import com.example.prog3.Service.CinService;
import com.example.prog3.Service.PhoneService;
import com.example.prog3.model.Employee;
import com.example.prog3.Service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final PhoneService phoneService;
    private final CinService cinService;

    @GetMapping("/index")
    public String index(
            @RequestParam(value = "name",required = false) String name,
            @RequestParam(value = "lastName",required = false) String lastName,
            @RequestParam(value = "gender",required = false) String sex,
            @RequestParam(value = "role",required = false) String role,
            @RequestParam(value = "employementDate",required = false) String eDate,
            @RequestParam(value = "departureDate",required = false) String dDate,
            Model model){
        model.addAttribute("employees",employeeService.filterEmployees(name,lastName,sex,role,eDate,dDate));
        return "index";
    }

    @PostMapping("/saveEmployee")
    public String saveEmployee(
            @RequestParam("testImg") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("lastName") String lastName,
            @RequestParam("birthDate") String birthDate,
            @RequestParam("gender") String sex,
            @RequestParam("csp") String csp,
            @RequestParam("address") String address,
            @RequestParam("emailPro") String emailPro,
            @RequestParam("emailPerso") String emailPerso,
            @RequestParam("role") String role,
            @RequestParam("child") Integer child,
            @RequestParam("employementDate") String eDate,
            @RequestParam("departureDate") String dDate,
            @RequestParam("cnaps") String cnaps,
            @RequestParam("phoneNumbers") String phoneNumbers,
            @RequestParam("cinNumber") String cinNumber,
            @RequestParam("cinDate") String cinDate,
            @RequestParam("cinLocation") String cinLocation) throws IOException {
        byte[] fileData = file.getBytes();
        employeeService.createEmployee(name,lastName,birthDate,sex,csp,address,emailPro,emailPerso,role,child,eDate,dDate,cnaps,fileData);
        phoneService.createPhoneNumber(phoneNumbers,employeeService.getByEmailPro(emailPro));
        cinService.createCin(cinNumber,cinDate,cinLocation,employeeService.getByEmailPro(emailPro));
        return "redirect:/index";
    }

    @GetMapping("/form")
    public String form(@ModelAttribute Employee employee, Model model){
        model.addAttribute("employee", new Employee());
        return "form";
    }

    @GetMapping("/formEmployee/image/{id}")
    public ResponseEntity<byte[]> showImage(@PathVariable Long id) {
        Optional<Employee> employeeOptional = employeeService.getById(id);
        byte[] imageData = employeeOptional.get().getEmplImg();
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(imageData,header, HttpStatus.OK);
    }

    @GetMapping("/updateEmployee/{matricule}")
    public String updateEmployee(@PathVariable String matricule,Model model){
        model.addAttribute("employee",employeeService.getByMatricule(matricule));
        return "updateEmployee";
    }

    @GetMapping("/formEmployee/{matricule}")
    public String formEmployee(@PathVariable String matricule,Model model){
        model.addAttribute("employee",employeeService.getByMatricule(matricule));
        return "formEmployee";
    }

    @PostMapping("/updateEmp/{matricule}")
    public String upEmployee(
            @PathVariable String matricule,
            @RequestParam("Img") MultipartFile file,
            @RequestParam("name") String name ,
            @RequestParam("lastName") String lastName,
            @RequestParam("gender") String sex,
            @RequestParam("birthDate") String birthDate,
            @RequestParam("csp") String csp,
            @RequestParam("address") String address,
            @RequestParam("emailPro") String emailPro,
            @RequestParam("emailPerso") String emailPerso,
            @RequestParam("role") String role,
            @RequestParam("child") Integer child,
            @RequestParam("employementDate") String eDate,
            @RequestParam("departureDate") String dDate,
            @RequestParam("cnaps") String cnaps,
            @RequestParam("phoneNumbers") String phoneNumbers,
            @RequestParam("cinNumber") String cinNumber,
            @RequestParam("cinDate") String cinDate,
            @RequestParam("cinLocation") String cinLocation) throws IOException {
                byte[] fileData = file.getBytes();
                employeeService.crupdateEmployee(matricule,name,lastName,birthDate,sex,csp,address,emailPro,emailPerso,role,child,eDate,dDate,cnaps,fileData);
                phoneService.updatePhoneNumber(phoneNumbers,employeeService.getByMatricule(matricule));
                cinService.updateCin(employeeService.getByMatricule(matricule),cinNumber,cinDate,cinLocation);
        return "redirect:/index";
    }
}
