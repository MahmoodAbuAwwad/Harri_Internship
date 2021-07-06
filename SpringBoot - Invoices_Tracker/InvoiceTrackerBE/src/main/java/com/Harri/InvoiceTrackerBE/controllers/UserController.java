package com.Harri.InvoiceTrackerBE.controllers;

import com.Harri.InvoiceTrackerBE.configs.JwtTokenUtil;
import com.Harri.InvoiceTrackerBE.dtos.UserDTO;
import com.Harri.InvoiceTrackerBE.enums.UserRole;
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
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PostMapping(value = "/login",  produces = MediaType.APPLICATION_JSON_VALUE)
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

    @GetMapping("/logged-in")
    public ResponseEntity<?> getLoggedInUser(@RequestHeader("authorization") String token) throws Exception {
        String email  = tokenService.getUsernameFromToken(token.split(" ")[1]);
        User loggedInUser = userService.findUserByEmail(email);
        return  ResponseEntity.ok(loggedInUser);
    }
    @PreAuthorize("hasAuthority('SUPERUSER')")
    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userService.getAllUser();
    }
    @PreAuthorize("hasAuthority('SUPERUSER')")
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable long id){
        return userService.getUser(id);
    }
    @PreAuthorize("hasAuthority('SUPERUSER')")
    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id) throws Exception {
       return userService.deleteUser(id);
    }
    @PreAuthorize("hasAuthority('SUPERUSER')")
    @PutMapping("/users/edit/{id}")
    public ResponseEntity<?> editUser(@PathVariable long id, @RequestBody UserDTO user ) throws Exception {
        return userService.editUser(id,user);
    }
}
