package com.Harri.InvoiceTrackerBE.controllers;

import com.Harri.InvoiceTrackerBE.configs.JwtTokenUtil;
import com.Harri.InvoiceTrackerBE.dtos.UserDTO;
import com.Harri.InvoiceTrackerBE.models.LoginRequest;
import com.Harri.InvoiceTrackerBE.models.LoginResponse;
import com.Harri.InvoiceTrackerBE.models.User;
import com.Harri.InvoiceTrackerBE.repositories.UserRepository;
import com.Harri.InvoiceTrackerBE.services.LoginUserDetailsService;
import com.Harri.InvoiceTrackerBE.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    //inject all services and repos needed.
    @Autowired
    UserService userService;
    @Autowired
    LoginUserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder bcryptEncoder;
    @Autowired
    JwtTokenUtil tokenService;
    @Autowired
    UserRepository userRepo;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    //sign up a user
    @PostMapping("/signup")
    public ResponseEntity<?> Register(@RequestBody UserDTO user) throws Exception {
        User newUser = new User();
        newUser.setAge(user.getAge());
        newUser.setEmail(user.getEmail());
        newUser.setAddress(user.getAddress());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setRole(user.getRole());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        return userService.addNewUser(newUser);
    }

    /*
    * Login a user, this will initiate the JWT process
    * User Role is an enum, the authority is given to User during Login process
    * this request return token as response
    * */
    @PostMapping(value = "/login",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getEmail(),  authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);
       return  ResponseEntity.ok((new LoginResponse(token)));
    }

    //authenticate user via username(Email) and password using authentication manager
    private void authenticate(String username, String password) throws BadCredentialsException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    //this returned logged in user
    //INFO: need to change token to be generated from user info.
    @GetMapping("/logged-in")
    public ResponseEntity<?> getLoggedInUser(@RequestHeader("authorization") String token) throws Exception {
        String email  = tokenService.getUsernameFromToken(token.split(" ")[1]);
        User loggedInUser = userService.findUserByEmail(email);
        return  ResponseEntity.ok(loggedInUser);
    }

    //return all users data
    @PreAuthorize("hasAuthority('SUPERUSER')")
    @GetMapping("/users")
    public List<User> getAllUsers(){
        List<User> users = userService.getAllUser();
        List<User> activatedUsers = new ArrayList<User>();
        for(int i =0; i<users.size();i++){
            if(users.get(i).getDeleted()==0){
                activatedUsers.add(users.get(i));
            }
        }
        return activatedUsers;
    }

    //return specific user info.
    @PreAuthorize("hasAuthority('SUPERUSER')")
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable long id) throws Exception {
        User returnedUser = userService.getUser(id);
        if(returnedUser.getDeleted()==1){
            throw new UnsupportedOperationException("User is not found (deleted) ");
        }
        return returnedUser;
    }

    //delete user - Dont delete invoice
    @PreAuthorize("hasAuthority('SUPERUSER')")
    @PutMapping("/users/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id) throws Exception {
       return userService.deleteUser(id);
    }

    //edit user
    @PreAuthorize("hasAuthority('SUPERUSER')")
    @PutMapping("/users/{id}")
    public ResponseEntity<?> editUser(@PathVariable long id, @RequestBody UserDTO user ) throws Exception {
        return userService.editUser(id,user);
    }
}
