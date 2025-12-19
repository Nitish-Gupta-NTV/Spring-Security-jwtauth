package com.SpringSecurity.demo.service;

import com.SpringSecurity.demo.Models.UserPrinciple;
import com.SpringSecurity.demo.Models.Users;
import com.SpringSecurity.demo.Repository.UserRepo;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MyUserDeatileService implements UserDetailsService {
    @Autowired
    private UserRepo repo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("reach the my userservice loadusernameby username");
        Users users= repo.findByUsername(username);
        if(users==null)
        {
            System.out.println("User not found");
            throw new UsernameNotFoundException("usernot found exception");
        }
        return new UserPrinciple(users);
    }

}
