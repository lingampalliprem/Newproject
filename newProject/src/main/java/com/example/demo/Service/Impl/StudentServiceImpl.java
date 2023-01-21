package com.example.demo.Service.Impl;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Constants.Constants;
import com.example.demo.Exception.StudentException;
import com.example.demo.Service.StudentService;
import com.example.demo.dao.StudentDao;
import com.example.demo.details.Student;

import net.minidev.json.JSONObject;

@Service
public class StudentServiceImpl implements StudentService{

    @Autowired
    StudentDao studentDao;


    //To Create a Student
    @Override
    public void createStudent(Student student) throws StudentException {
        LocalDate date=LocalDate.parse(student.dateOfBirth);
        int age=calculateAge(date);
        
        if(age > 20 || age < 16){
            throw new StudentException(Constants.AGE_INVALID);
        }

        if(!student.section.equals(Constants.SECTION_A) && !student.section.equals(Constants.SECTION_B) && !student.section.equals(Constants.SECTION_C)){
            throw new StudentException(Constants.SECTION_INVALID);
        }

        if(!student.gender.equals(Constants.GENDER_M) && !student.gender.equals(Constants.GENDER_F)){
            throw new StudentException(Constants.GENDER_INVALID);
        }

        student.age=age;

        int totalMarks=totalMarks(student.marks1,student.marks2,student.marks3);
        
        student.total=totalMarks;

        float average=average(totalMarks);


        student.average=average;

        student.result= calculateResult(student.marks1, student.marks2, student.marks3) ? "Pass" : "Fail"; 

        studentDao.save(student);

    }

    //Calculate Age
    private int calculateAge(LocalDate dob)   
    {   
        LocalDate curDate = LocalDate.now(); 
        if ((dob != null) && (curDate != null)){  
            return Period.between(dob, curDate).getYears();  
        }  
         return 0;
    }

    //Calculate Total Marks
    private int totalMarks(int marks1,int marks2,int marks3){
        return marks1+marks2+marks3;
    }

    //Calculate Average
    private float average(int total){
        return total/3;
    }

    private Boolean calculateResult(int marks1,int marks2,int marks3){
        if(marks1<35 || marks2<35 || marks3<35) return false;
        return true;
    }

    //To Update the Marks
    @Override
    public String updateMarks(int id,JSONObject marks) throws StudentException {


        if(marks.getAsString("marks1") == null || marks.getAsString("marks1").isEmpty()){
            throw new StudentException(Constants.MARKS1_EMPTY);
        }
        if(marks.getAsString("marks2") == null || marks.getAsString("marks2").isEmpty()){
            throw new StudentException(Constants.MARKS2_EMPTY);
        }
        if(marks.getAsString("marks3") == null || marks.getAsString("marks3").isEmpty()){
            throw new StudentException(Constants.MARKS3_EMPTY);
        }
        

        int marks1=Integer.parseInt(marks.getAsString("marks1"));
        int marks2=Integer.parseInt(marks.getAsString("marks2"));
        int marks3=Integer.parseInt(marks.getAsString("marks3"));

        if(!(marks1 > 0 && marks1<100)){
            throw new StudentException(Constants.MARKS1_RANGE);
        }
        if(!(marks2 > 0 && marks2<100)){
            throw new StudentException(Constants.MARKS2_RANGE);
        }
        if(!(marks3 > 0 && marks3<100)){
            throw new StudentException(Constants.MARKS3_RANGE);
        }

        Optional<Student> student=studentDao.findById(id);

        if(Optional.of(student).isEmpty()) throw new StudentException(Constants.STUDENT_NOT_FOUND);

        student.get().marks1=marks1;
        student.get().marks2=marks2;
        student.get().marks3=marks3;

        int totalMarks=totalMarks(marks1,marks2,marks3);
        
        student.get().total=totalMarks;

        float average=average(totalMarks);

        student.get().average=average;

        student.get().result= calculateResult(marks1, marks2, marks3) ? "Pass" : "Fail"; 

        System.out.println(student.get());

        studentDao.save(student.get());

        return Constants.MARKS_UPDATED;
    }

    @Override
    public void deleteStudent(int id) throws StudentException {
        // TODO Auto-generated method stub
        
        Optional<Student> student=studentDao.findById(id);

        if(Optional.of(student).isEmpty()) throw new StudentException(Constants.STUDENT_NOT_FOUND);

        studentDao.deleteById(id);

    }

    @Override
    public Student getStudentByGender(String gender) {
        return studentDao.findByGender(gender);
    }

    @Override
    public Student getStudentByAge(int age) {
        return studentDao.findByAge(age);
    }

    @Override
    public Student getStudentWithAverageGreaterThanGivenValue(float average) {
        return studentDao.findByAverageGreaterThan(average);
    }

    @Override
    public Student getStudentWithAverageLesserThanGivenValue(float average) {
        return studentDao.findByAverageLessThan(average);
    }
    
}
