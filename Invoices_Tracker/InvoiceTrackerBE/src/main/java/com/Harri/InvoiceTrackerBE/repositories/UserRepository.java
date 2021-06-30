package com.Harri.InvoiceTrackerBE.repositories;

import com.Harri.InvoiceTrackerBE.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}