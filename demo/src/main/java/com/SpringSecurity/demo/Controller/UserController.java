package com.SpringSecurity.demo.Controller;

import com.SpringSecurity.demo.Models.Users;
import com.SpringSecurity.demo.service.UsersServices;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UsersServices service;
    @GetMapping("/api")
    public String getmapping(HttpServletRequest http)
    {
        System.out.println("controller working fine");

        return "hello world"+http.getSession().getId();
    }
    @PostMapping("/api/register/users")
    public Users registeruser(@RequestBody Users users)
    {
        System.out.println("entering registering controller ");
        System.out.println("Controller");

        return service.RegisterUsers(users);
    }
    @GetMapping("/api/getallusers")
    public List<Users> getallusers()
    {


        return service.getallusers();
    }
    @PostMapping("/api/login-test")
    public String loginUser(@RequestBody Users user)
    {
        System.out.println("login end point is hit");
        return service.Verify(user);
    }
}
