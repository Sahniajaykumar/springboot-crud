package com.crud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.entity.User;
import com.crud.exception.ResourceNotFoundException;
import com.crud.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User  saveUser(User user){
        if(userRepository.existsByEmail(user.getEmail())){
            throw new RuntimeException("Email already exists");
        }
        return userRepository.save(user);
    }

    public List<User>getAllUsers(){
       // return userRepository.findAll();
       List<User> users = userRepository.findAll();
       if(users.isEmpty()){
        throw new RuntimeException("user not found");
       }
       return users;
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(
              () -> new ResourceNotFoundException("user not found with id" + id)); // make sure ResourceNotFoundException already created in exception package
    }

    public User updateUser(Long id , User user){
        User exiting = getUserById(id);
        exiting.setUsername(user.getUsername());
        exiting.setName(user.getName());
        exiting.setEmail(user.getEmail());
        exiting.setMobile(user.getMobile());
        return userRepository.save(exiting);
    }

    public void deleteUser(Long id){
        if(!userRepository.existsById(id)){
            throw new ResourceNotFoundException("user not found with id" + id);
        }
         userRepository.deleteById(id);

    }

    
}
