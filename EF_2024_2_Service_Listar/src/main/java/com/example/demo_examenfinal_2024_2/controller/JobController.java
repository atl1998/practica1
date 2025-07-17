package com.example.demo_examenfinal_2024_2.controller;

import com.example.demo_examenfinal_2024_2.entity.Job;
import com.example.demo_examenfinal_2024_2.repository.JobRepository;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class JobController {

    final JobRepository jobRepository;

    public JobController(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @GetMapping(value="/rh/trabajo/orderby/{ordercolum}", produces="application/json")
    public List<Job> listaTrabajos(@PathVariable("ordercolum") String  ordercolum)

    {
        if (ordercolum.toLowerCase().equals("title")) {
            ordercolum = "jobTitle";
        } else if (ordercolum.toLowerCase().equals("min")) {
            ordercolum = "minSalary";
        } else if (ordercolum.toLowerCase().equals("max")) {
            ordercolum = "maxSalary";
        }
        return jobRepository.findAll(Sort.by(Sort.Direction.ASC, ordercolum));
    }
}
