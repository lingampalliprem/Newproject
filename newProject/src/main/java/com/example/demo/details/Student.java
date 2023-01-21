package com.example.demo.details;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Student {

    @Id
    @JsonProperty("Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @JsonProperty("First_Name")
    public String firstName;

    @JsonProperty("Last_Name")
    public String lastName;

    @JsonProperty("DOB")
    public String dateOfBirth;

    @JsonProperty("age")
    public int age;

    @JsonProperty("Section")
    public String section;

    @JsonProperty("Gender")
    public String gender;

    @JsonProperty("Marks1")
    public int marks1;

    @JsonProperty("Marks2")
    public int marks2;

    @JsonProperty("Marks3")
    public int marks3;

    @JsonProperty("Total")
    public int total;

    @JsonProperty("Average")
    public float average;

    @JsonProperty("Result")
    public String result;
    
}
