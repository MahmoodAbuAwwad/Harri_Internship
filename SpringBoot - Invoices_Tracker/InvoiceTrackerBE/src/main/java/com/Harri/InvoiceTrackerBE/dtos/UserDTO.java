package com.Harri.InvoiceTrackerBE.dtos;

import com.Harri.InvoiceTrackerBE.enums.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.*;

//User  Data transfer object
public class UserDTO {
    //this class used to pass data to Models.User to store in DB
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private int age;
    private String password;
    private UserRole role;

//    public Collection<? extends GrantedAuthority> getAuthorities(){
//        //this should be moved to a table, so it would be like ==> rolesRepo.getRoles() instead of appending each role
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        Set<UserRole> roles = new HashSet<>();
//        roles.add(UserRole.USER);
//        roles.add(UserRole.SUPERUSER);
//        roles.add(UserRole.AUDITOR);
//
//        for(UserRole role: roles){
//            authorities.add(new SimpleGrantedAuthority(role.name()));
//        }
//        return  authorities;
//    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}