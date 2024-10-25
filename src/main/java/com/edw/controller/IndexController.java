package com.edw.controller;

import com.edw.model.Employee;
import com.edw.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <pre>
 *  com.edw.controller.IndexController
 * </pre>
 *
 * @author Muhammad Edwin < edwin at redhat dot com >
 * 16 Oct 2024 16:42
 */
@RestController
public class IndexController {

    private EmployeeService employeeService;

    @Autowired
    public IndexController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(path = "/")
    public HashMap index() {
        return new HashMap(){{
            put("hello", "world");
        }};
    }

    @GetMapping(path = "/employee/{id}")
    public Employee get(@PathVariable Long id) throws Exception {
        return employeeService.get(id);
    }

    @GetMapping(path = "/employee")
    public List<Employee> get() throws Exception {
        return employeeService.get();
    }

    @PostMapping(path = "/employee")
    public ResponseEntity saveEmployee(@RequestBody Employee employee) throws Exception {
        employeeService.save(employee);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
