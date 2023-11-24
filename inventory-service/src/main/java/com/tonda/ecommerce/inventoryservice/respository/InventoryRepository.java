package com.tonda.ecommerce.inventoryservice.respository;

import java.util.Optional;

import com.tonda.ecommerce.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findBySkuCode(String skuCode); // logic of this query is automatically generated.
}
