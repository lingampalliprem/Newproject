package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Constants.Constants;
import com.example.demo.Exception.StudentException;
import com.example.demo.Service.StudentService;
import com.example.demo.details.Student;

import net.minidev.json.JSONObject;


@RestController
public class newProjectController {

	@Autowired
    StudentService studentService;

    @PostMapping("/student")
    public ResponseEntity<?> createStudent(@RequestBody Student student){
        try{
            if(student==null){
                return ResponseEntity.badRequest().body(Constants.STUDENT_EMPTY);
            }

            if(student.firstName.length() < 3){
                return ResponseEntity.badRequest().body(Constants.FIRST_NAME_INVALID);
            }

            if(student.lastName.length() < 3){
                return ResponseEntity.badRequest().body(Constants.LAST_NAME_INVALID);
            }

            if(student.dateOfBirth==null){
                return ResponseEntity.badRequest().body(Constants.DATE_OF_BIRTH_EMPTY);
            }

            if(!(student.marks1 > 0 && student.marks1 < 100)){
                return ResponseEntity.badRequest().body(Constants.MARKS1_RANGE);
            }

            if(!(student.marks2 > 0 && student.marks2 < 100)){
                return ResponseEntity.badRequest().body(Constants.MARKS2_RANGE);
            }

            if(!(student.marks3 > 0 && student.marks3 < 100)){
                return ResponseEntity.badRequest().body(Constants.MARKS3_RANGE);
            }

            studentService.createStudent(student);

            return ResponseEntity.ok().body(Constants.STUDENT_ADDED);

        }catch(StudentException exception) {
			return ResponseEntity.badRequest().body(exception.getMessage());
		}
        catch(Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Constants.SOMETHING_WENT_WRONG);
        }
        
    }

    @PostMapping("/updatemarks")
    public ResponseEntity<?> updateMarks(@RequestParam(value="id") final int id,
        @RequestBody JSONObject marks){
            try{

                return ResponseEntity.ok().body(studentService.updateMarks(id,marks));
            }catch(StudentException exception) {
                return ResponseEntity.badRequest().body(exception.getMessage());
            }
            catch(Exception ex){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Constants.SOMETHING_WENT_WRONG);
            }
        }

        @DeleteMapping("deletestudent")
        public ResponseEntity<?> deleteStudent(@RequestParam(value = "id") final int id){
            try{
                studentService.deleteStudent(id);
                return ResponseEntity.ok().body(Constants.STUDENT_DELETED);

            }catch(StudentException exception) {
                return ResponseEntity.badRequest().body(exception.getMessage());
            }
            catch(Exception ex){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Constants.SOMETHING_WENT_WRONG);
            }
        }

        @GetMapping("getstudentbygender/{gender}")
        public ResponseEntity<?> getStudentByGender(@PathVariable String gender){
            try{
                return ResponseEntity.ok().body(studentService.getStudentByGender(gender));
            }catch(Exception ex){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Constants.SOMETHING_WENT_WRONG);
            }
        }
 
        @GetMapping("getstudentbyage/{age}")
        public ResponseEntity<?> getStudentByAge(@PathVariable int age){
            try{
                return ResponseEntity.ok().body(studentService.getStudentByAge(age));
            }catch(Exception ex){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Constants.SOMETHING_WENT_WRONG);
            }
        }

        @GetMapping("getstudentbyaveragegreaterthan/{average}")
        public ResponseEntity<?> getStudentByAverageGreaterThan(@PathVariable float average){
            try{
                return ResponseEntity.ok().body(studentService.getStudentWithAverageGreaterThanGivenValue(average));
            }catch(Exception ex){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Constants.SOMETHING_WENT_WRONG);
            }
        }

        @GetMapping("getstudentbyaveragelessthan/{average}")
        public ResponseEntity<?> getStudentByAverageLessThan(@PathVariable float average){
            try{
                return ResponseEntity.ok().body(studentService.getStudentWithAverageLesserThanGivenValue(average));
            }catch(Exception ex){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Constants.SOMETHING_WENT_WRONG);
            }
        }

}
