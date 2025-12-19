package com.SpringSecurity.demo.service;

import com.SpringSecurity.demo.Models.Users;
import com.SpringSecurity.demo.Repository.UserRepo;
import org.apache.catalina.realm.UserDatabaseRealm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersServices {
    @Autowired
    private UserRepo Repo;
    @Autowired
    AuthenticationManager auth;
    @Autowired
    private JWTService jwtservice;

    private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(8);
    public Users RegisterUsers(Users users)
    {
        System.out.println("reach the servicelayer from the controller->service layer");
        System.out.println("PASSWORD = " + users.getPassword());
        users.setPassword(encoder.encode(users.getPassword()));
        System.out.println("password encoded"+users.getPassword()+"username"+users.getUsername());
       return Repo.save(users);
    }
    public List<Users> getallusers()
    {

        return Repo.findAll();
    }
    public String Verify(Users users)
    {
        System.out.println("verify method called from controller");
        System.out.println(users.getUsername()+"   "+users.getPassword());
        try {
            Authentication authentication= null;

            authentication = null;

            authentication = auth.authenticate(
                    new UsernamePasswordAuthenticationToken(users.getUsername(),users.getPassword())
            );

            System.out.println("Verify method called from the controller not rreaches the authentication ");
            System.out.println(users.getUsername()+"   "+users.getPassword());
            if(authentication.isAuthenticated())
            {

                String str=jwtservice.generateToken(users.getUsername());
                System.out.println("token generrtaed  ="+str);
                return str;

            }
            else
            {

                return "invalid username or Password";
            }
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        return "invalid user";

    }
}
