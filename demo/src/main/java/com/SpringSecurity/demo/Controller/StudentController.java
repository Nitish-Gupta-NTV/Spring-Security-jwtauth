package com.SpringSecurity.demo.Controller;

import com.SpringSecurity.demo.Models.Student;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {
    List<Student> students=new ArrayList<>(List.of(new Student(1,"Ram","Ramemail@gmial.com",76)
    ,new Student(2,"Shyam","Shyam@gmial.com",85)));
    @GetMapping("/api/allstudent")
    public List<Student> getallstudent()
    {
        return students;
    }
    @GetMapping("/api/csrf")
    public CsrfToken getcsrftoken(HttpServletRequest request)
    {
        return (CsrfToken) request.getAttribute("_csrf");
    }
    @PostMapping("/api/register")
    public Student registerstudent(@RequestBody Student student)
    {
      students.add(student);
      return student;

    }
}
