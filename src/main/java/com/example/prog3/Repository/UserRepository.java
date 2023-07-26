package com.example.prog3.Repository;

import com.example.prog3.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users,Long> {
    Users findByUsernameAndPassword(String username,String password);
}