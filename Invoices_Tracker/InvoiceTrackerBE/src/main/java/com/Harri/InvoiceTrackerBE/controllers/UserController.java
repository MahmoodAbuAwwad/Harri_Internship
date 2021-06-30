package com.Harri.InvoiceTrackerBE.controllers;

import com.Harri.InvoiceTrackerBE.models.User;
import com.Harri.InvoiceTrackerBE.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/users/signup")
    public ResponseEntity signUp(@RequestBody User user){
        return userService.signUp(user);
    }
}
