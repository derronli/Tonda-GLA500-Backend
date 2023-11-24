package com.tonda.ecommerce.inventoryservice;

import com.tonda.ecommerce.inventoryservice.model.Inventory;
import com.tonda.ecommerce.inventoryservice.respository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

}
