package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public void initializeEmployees(List<Employee> employees) {
        System.out.println("entre");
        Flux<Employee> savedEmployees = employeeRepository.saveAll(employees);
        savedEmployees.subscribe();
    }

    public Mono<ResponseEntity<Employee>> save(Employee employeeDto) {
        return employeeRepository.save(employeeDto).map(employee -> {
                    return ResponseEntity.ok(employeeDto);
        }).defaultIfEmpty(
                new ResponseEntity<>(HttpStatus.NOT_FOUND)
        );
    }

    public Flux<Employee> getAllEmployees() {
        Flux<Employee> employees =  employeeRepository.findAll();
        return employees;
    }

    public Flux<Employee> getEmployeesFilterByAge(int age) {
        return employeeRepository.findByAgeGreaterThan(age);
    }

    public Mono<Employee> getEmployeeById(int id) {
        return employeeRepository.findById(id);
    }
}
