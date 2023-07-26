package com.example.prog3.Service;

import com.example.prog3.Repository.UserRepository;
import com.example.prog3.model.Users;
import com.example.prog3.utils.TokenGeneration;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final TokenGeneration tokenGeneration;

    public boolean checkUser(String username,String password){
        return repository.findByUsernameAndPassword(username,password) != null;
    }
    public Users getUser(String username, String password){
        return repository.findByUsernameAndPassword(username, password);
    }

    public String generateTokenForUserId(Long id){
        return tokenGeneration.generateToken(id);
    }
}