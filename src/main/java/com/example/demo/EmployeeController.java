package com.example.demo;

import com.datastax.driver.core.utils.UUIDs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("employee")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @PostConstruct
    public void saveEmployees() {
        List<Employee> employees = new ArrayList<>();
    }

    @PostMapping("/")
    public Mono<ResponseEntity<Employee>> createEmployee(@RequestBody Employee createEmployeeDto) {
        createEmployeeDto.setId(UUIDs.timeBased());
        return employeeService.save(createEmployeeDto);
    }

    @GetMapping("/list")
    public Flux<Employee> getAllEmployees() {
        Flux<Employee> employees = employeeService.getAllEmployees();
        return employees;
    }

    @GetMapping("/{id}")
    public Mono<Employee> getEmployeeById(@PathVariable int id) {
        return employeeService.getEmployeeById(id);
    }

    @GetMapping("/filterByAge/{age}")
    public Flux<Employee> getEmployeeByAge(@PathVariable int age) {
        return employeeService.getEmployeesFilterByAge(age);
    }
}
