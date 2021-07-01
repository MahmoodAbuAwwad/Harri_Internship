package com.Harri.InvoiceTrackerBE.services;

import com.Harri.InvoiceTrackerBE.models.User;
import com.Harri.InvoiceTrackerBE.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.Null;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepo;
    public ResponseEntity signUp( User user){
        if(validateEmail(user.getEmail())) {
            userRepo.save(user);
            return new ResponseEntity<>("User Signed Up Successfully", HttpStatus.OK);
        } else
        {
            return new ResponseEntity<>("email already exists", HttpStatus.BAD_REQUEST);
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
