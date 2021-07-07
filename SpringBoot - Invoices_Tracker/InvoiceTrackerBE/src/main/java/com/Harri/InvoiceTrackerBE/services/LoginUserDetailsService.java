package com.Harri.InvoiceTrackerBE.services;
import java.util.*;

import com.Harri.InvoiceTrackerBE.enums.UserRole;
import com.Harri.InvoiceTrackerBE.models.User;
import com.Harri.InvoiceTrackerBE.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    //login
    //load user from database
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        com.Harri.InvoiceTrackerBE.models.User user = userRepo.findByEmail(email);
        //if user is deleted, don't allow siging in
        if (user == null || user.getDeleted()==1) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        //set authority of logged in here to allow access using preauthorize(hasauthority)
        // enum should extend GrantedAuthoritreis and return it, check UserRole Enum
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                Arrays.asList(user.getRole()));
    }
}