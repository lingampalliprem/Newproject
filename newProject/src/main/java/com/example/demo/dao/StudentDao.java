package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.details.Student;

@Repository
public interface StudentDao extends JpaRepository<Student,Integer>{
    public Student findByGender(String gender);
    public Student findByAge(int age);
    public Student findByAverageGreaterThan(float average);
    public Student findByAverageLessThan(float average);
}
