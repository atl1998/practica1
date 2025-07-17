package com.example.demo_examenfinal_2024_2.repository;


import com.example.demo_examenfinal_2024_2.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
