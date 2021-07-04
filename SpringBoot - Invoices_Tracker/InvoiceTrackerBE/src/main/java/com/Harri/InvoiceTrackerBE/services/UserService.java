package com.Harri.InvoiceTrackerBE.services;

import com.Harri.InvoiceTrackerBE.models.User;
import com.Harri.InvoiceTrackerBE.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


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

    @Modifying
    public ResponseEntity<?> editUser( User user){

        if(userRepo.existsByEmail(user.getEmail())) {
            userRepo.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } else
        {
            return new ResponseEntity<>("Can not edit this user", HttpStatus.CONFLICT);
        }
    }

    private boolean validateEmail(String email){
        if(userRepo.existsByEmail(email)){
            //email exists in the db
            return  false;
        }
        return true;
    }
}
