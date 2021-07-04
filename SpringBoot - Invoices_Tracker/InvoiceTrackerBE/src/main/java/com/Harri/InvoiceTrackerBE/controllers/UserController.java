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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
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


    @PostMapping("/users/signup")
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

    @PostMapping(value = "/users/login",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getEmail(),  authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getEmail());

        final String token = jwtTokenUtil.generateToken(userDetails);

       return  ResponseEntity.ok((new LoginResponse(token)));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @GetMapping("users/logged-in")
    public ResponseEntity<?> getLoggedInUser(@RequestHeader("authorization") String token) throws Exception {
        String email  = tokenService.getUsernameFromToken(token.split(" ")[1]);
        User loggedInUser = userRepo.findByEmail(email);
        return  ResponseEntity.ok(loggedInUser);
    }

    @GetMapping("users")
    public List<User> getAllUsers(){
        return (List<User>) userRepo.findAll();
    }

    @GetMapping("users/{id}")
    public User getUser(@PathVariable long id){
        return  userRepo.findById(id);
    }

    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id) throws Exception {
        if(userRepo.existsById(id)){
            userRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return  new ResponseEntity<>("User not found !",HttpStatus.FORBIDDEN);
    }

    @PutMapping("/users/edit/{id}")
    public ResponseEntity<?> editUser(@PathVariable long id, @RequestBody UserDTO user ) throws Exception {
        if(userRepo.existsById(id)){
            User newUser = new User();
            newUser.setAge(user.getAge());
            newUser.setEmail(user.getEmail());
            newUser.setAddress(user.getAddress());
            newUser.setFirstName(user.getFirstName());
            newUser.setLastName(user.getLastName());
            newUser.setRole(user.getRole());
            newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
            this.userService.editUser(newUser);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return  new ResponseEntity<>("User not found !",HttpStatus.FORBIDDEN);
    }
}
