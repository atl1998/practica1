package com.example.demo_examenfinal_2024_2.controller;

import com.example.demo_examenfinal_2024_2.entity.Employee;
import com.example.demo_examenfinal_2024_2.repository.EmployeeRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rh/empleado")
public class EmployeeController {

    final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // [1] Listar empleados ordenados por campo (firstname, lastname, jobtitle)
    @GetMapping(value = "/orderby/{order}", produces = "application/json")
    public List<Employee> listaEmpleados(@PathVariable("order") String orderColumn) {
        // Validación de campo permitido
        // Mapeo de nombres "expuestos" en la URL a nombres de propiedades Java
        String sortField;

        switch (orderColumn.toLowerCase()) {
            case "firstname":
                sortField = "firstName";
                break;
            case "lastname":
                sortField = "lastName";
                break;
            case "jobtitle":
                sortField = "job.jobTitle";
                break;
            default:
                throw new IllegalArgumentException("Campo de orden no válido");
        }

        return employeeRepository.findAll(Sort.by(Sort.Direction.ASC, sortField));

    }

    // [2] Obtener información de un empleado por ID
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Employee> infoEmpleados(@PathVariable("id") Integer id) {
        Optional<Employee> opt = employeeRepository.findById(id);
        return opt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // [3] Agregar nuevo empleado
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<HashMap<String, Object>> agregarEmpleado(@RequestBody Employee newEmployee) {
        employeeRepository.save(newEmployee);
        HashMap<String, Object> responseJson = new HashMap<>();
        responseJson.put("id", newEmployee.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseJson);
    }
}

