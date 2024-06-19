package com.epic.spring_boot_CRUD.service;

import com.epic.spring_boot_CRUD.entity.User;
import com.epic.spring_boot_CRUD.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //User registration method
    public User registerUser(User user){
        //Encoding password and store it in the database
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }
}
