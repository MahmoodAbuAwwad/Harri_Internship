package com.Harri.InvoiceTrackerBE;

import com.Harri.InvoiceTrackerBE.controllers.InvoiceController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class InvoiceTrackerBeApplication {


	public static void main(String[] args) {
		new File(InvoiceController.uploadDir).mkdir();

		SpringApplication.run(InvoiceTrackerBeApplication.class, args);
	}
}
