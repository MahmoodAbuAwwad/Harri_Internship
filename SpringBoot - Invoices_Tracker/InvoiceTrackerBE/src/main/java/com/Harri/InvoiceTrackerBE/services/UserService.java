package com.Harri.InvoiceTrackerBE.services;

import com.Harri.InvoiceTrackerBE.dtos.UserDTO;
import com.Harri.InvoiceTrackerBE.enums.UserRole;
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

        User registeredUser = userRepo.findByEmail((user.getEmail()));
        if(registeredUser == null){
            if(userRepo.save(user)!=null){
                return new ResponseEntity<>(HttpStatus.CREATED);
            }else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }else{
            registeredUser.setDeleted(0);
            registeredUser.setPassword(user.getPassword());
            registeredUser.setFirstName(user.getFirstName());
            registeredUser.setLastName(user.getLastName());
            registeredUser.setAddress(user.getAddress());
            registeredUser.setAge(user.getAge());
            registeredUser.setDeleted(0);
            registeredUser.setRole(user.getRole());
            if(userRepo.save(registeredUser)!=null){
                return new ResponseEntity<>(HttpStatus.CREATED);
            }else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
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
        User deletedUser = userRepo.findById(id);
        if(deletedUser!=null){
            deletedUser.setDeleted(1);
            if(userRepo.save(deletedUser)!=null){
                return  new ResponseEntity<>(HttpStatus.OK);
            }
            else{
                return  new ResponseEntity<>("User can't be deleted !",HttpStatus.BAD_REQUEST);
            }
        }
        else{
            return  new ResponseEntity<>("User not found !",HttpStatus.FORBIDDEN);
        }
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
}
