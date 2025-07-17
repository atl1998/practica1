package com.example.ef_buscar_20242;

import com.example.ef_buscar_20242.entity.Employee;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rh")
public class BuscarController {

    @Autowired
    private ServiceBuscarClient serviceBuscarClient;

    private final Gson gson = new Gson();

    @GetMapping(value="/filtrarempleado/{letra}/{order}", produces="application/json")
    public String listarBuscar(@PathVariable("letra") String letra,
                               @PathVariable("order") String order) {

        // Obtener el JSON desde el microservicio de listar
        String jsonLista = serviceBuscarClient.listarEmpleados(order);

        // Convertir JSON a lista de empleados
        Type listType = new TypeToken<ArrayList<Employee>>() {}.getType();
        List<Employee> empleados = gson.fromJson(jsonLista, listType);

        // Filtrar según letra inicial en el campo correspondiente
        List<Employee> filtrados = empleados.stream().filter(emp -> {
            String valorCampo = switch (order.toLowerCase()) {
                case "firstname" -> emp.getFirstName();
                case "lastname" -> emp.getLastName();
                case "jobtitle" -> emp.getJob().getJobTitle();
                default -> null;
            };
            return valorCampo != null && valorCampo.toLowerCase().startsWith(letra.toLowerCase());
        }).collect(Collectors.toList());

        return gson.toJson(filtrados);
    }
}

