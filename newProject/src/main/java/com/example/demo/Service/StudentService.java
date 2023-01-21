package com.example.demo.Service;


import com.example.demo.Exception.StudentException;
import com.example.demo.details.Student;

import net.minidev.json.JSONObject;

public interface StudentService {

    public void createStudent(Student student) throws  StudentException;

    public String updateMarks(int id,JSONObject marks) throws StudentException;

    public void deleteStudent(int id) throws StudentException;

    public Student getStudentByGender(String gender);
    
    public Student getStudentByAge(int age);

    public Student getStudentWithAverageGreaterThanGivenValue(float average);

    public Student getStudentWithAverageLesserThanGivenValue(float average);
    
}
