package com.Harri.InvoiceTrackerBE.services;

import com.Harri.InvoiceTrackerBE.models.User;
import com.Harri.InvoiceTrackerBE.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UserService {
    @Autowired
    UserRepository userRepo;
    public ResponseEntity signUp( User user){
        userRepo.save(user);
        return new ResponseEntity<>("User Signed Up Successfully", HttpStatus.OK);
    }
}
