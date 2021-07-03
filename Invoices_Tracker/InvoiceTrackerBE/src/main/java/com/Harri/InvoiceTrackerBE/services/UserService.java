package com.Harri.InvoiceTrackerBE.services;

import com.Harri.InvoiceTrackerBE.models.User;
import com.Harri.InvoiceTrackerBE.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    @Autowired
    LoginUserDetailsService jwtUserDetailsService;

    @Autowired
    UserRepository userRepo;

    public ResponseEntity<?> signUp( User user){

        if(validateEmail(user.getEmail())) {
            userRepo.save(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else
        {
            return new ResponseEntity<>("email already exists", HttpStatus.CONFLICT);
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
