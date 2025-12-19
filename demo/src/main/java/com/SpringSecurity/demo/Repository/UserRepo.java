package com.SpringSecurity.demo.Repository;

import com.SpringSecurity.demo.Models.Student;
import com.SpringSecurity.demo.Models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users,Long> {
    Users findByUsername(String username);
}
