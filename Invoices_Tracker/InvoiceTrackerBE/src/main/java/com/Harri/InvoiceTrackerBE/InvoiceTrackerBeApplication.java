package com.Harri.InvoiceTrackerBE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class InvoiceTrackerBeApplication {
	public static void main(String[] args) {
		SpringApplication.run(InvoiceTrackerBeApplication.class, args);
	}
}
