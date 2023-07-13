package com.example.prog3.Controller;

import com.example.prog3.model.Employee;
import com.example.prog3.Service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
import java.util.Optional;

@Controller
@AllArgsConstructor
public class EmployeeController {
    public EmployeeService employeeService;

    @GetMapping("/index")
    public String index(Model model){
        model.addAttribute("employees",employeeService.getAllEmployees());
        return "index";
    }

    @PostMapping("/saveEmployee")
    public String saveEmployee(@RequestParam("testImg") MultipartFile file, HttpServletRequest request) throws IOException {
        Employee empl = new Employee();
        empl.setName(request.getParameter("name"));
        empl.setLastName(request.getParameter("lastName"));
        empl.setBirthDate(request.getParameter("birthDate"));
        empl.setMatricule(request.getParameter("matricule"));
        if(!file.isEmpty()){
                byte[] fileData = file.getBytes();
                empl.setEmplImg(fileData);
                employeeService.crupdateEmployee(empl);
            } else {
            employeeService.crupdateEmployee(empl);
        }
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
    public String upEmployee(@PathVariable String matricule,@RequestParam("Img") MultipartFile file, HttpServletRequest request){
        Employee employee = employeeService.getByMatricule(matricule);
        String name = request.getParameter("name") == "" ? employee.getName() : request.getParameter("name");
        String lastName = request.getParameter("lastName") == "" ? employee.getLastName() : request.getParameter("lastName");
        String birthDate = request.getParameter("birthDate") == "" ? employee.getBirthDate() : request.getParameter("birthDate");
        try{
            if(!file.isEmpty()){
                byte[] fileData = file.getBytes();
                Employee empl = new Employee();
                empl.setName(name);
                empl.setLastName(lastName);
                empl.setMatricule(matricule);
                empl.setBirthDate(birthDate);
                empl.setEmplImg(fileData);
                employeeService.crupdateEmployee(empl);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return "redirect:/index";
    }

}
