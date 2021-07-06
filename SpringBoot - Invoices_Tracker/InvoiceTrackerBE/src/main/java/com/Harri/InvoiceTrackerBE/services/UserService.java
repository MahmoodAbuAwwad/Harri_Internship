package com.Harri.InvoiceTrackerBE.services;

import com.Harri.InvoiceTrackerBE.dtos.UserDTO;
import com.Harri.InvoiceTrackerBE.models.Item;
import com.Harri.InvoiceTrackerBE.models.User;
import com.Harri.InvoiceTrackerBE.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {
    @Autowired
    LoginUserDetailsService jwtUserDetailsService;

    @Autowired
    UserRepository userRepo;

    public ResponseEntity<?> addNewUser( User user){

        if(validateEmail(user.getEmail())) {
            userRepo.save(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else
        {
            return new ResponseEntity<>("email already exists", HttpStatus.CONFLICT);
        }
    }

    public User getUser(long id){
        return  userRepo.findById(id);
    }

    public User findUserByEmail(String email){
        return  userRepo.findByEmail(email);
    }

    public List<User> getAllUser(){
        return (List<User>) userRepo.findAll();
    }

    public ResponseEntity<?> deleteUser(long id){
        if(userRepo.existsById(id)){
            userRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return  new ResponseEntity<>("User not found !",HttpStatus.FORBIDDEN);
    }

    public ResponseEntity<?> editUser( long id , UserDTO user){
        if(userRepo.existsById(id)){
            User editUser = userRepo.findById(id);
            editUser.setEmail(user.getEmail());
            editUser.setRole(user.getRole());
            editUser.setFirstName(user.getFirstName());
            editUser.setLastName(user.getLastName());
            editUser.setAddress(user.getAddress());
            editUser.setAge(user.getAge());
            User eddUser = userRepo.save(editUser);
            if(eddUser!=null){
                return  new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return  new ResponseEntity<>("User not found !",HttpStatus.FORBIDDEN);
    }

    private boolean validateEmail(String email){
        if(userRepo.existsByEmail(email)){
            //email exists in the db
            return  false;
        }
        return true;
    }
}
