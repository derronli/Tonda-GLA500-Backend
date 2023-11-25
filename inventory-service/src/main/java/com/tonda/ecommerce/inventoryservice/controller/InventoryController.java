package com.tonda.ecommerce.inventoryservice.controller;

import java.util.List;

import com.tonda.ecommerce.inventoryservice.dto.InventoryResponse;
import com.tonda.ecommerce.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;



//    @GetMapping("/{sku-code}")
//    @ResponseStatus(HttpStatus.OK)
//    public boolean isInStock(@PathVariable("sku-code") String skuCode) { // acecept skuCode and check if that particular item is in stock
//        return inventoryService.isInStock(skuCode);
//    }
//
//
//    We note that accepting only a single sku-code as a path variable is not great for interfacing with Order Service -> given
//    that there can be multiple codes (items) in an order
//    Making multiple calls to this one endpoint -> BAD! time consuming to do repeated HTTP calls
//    SO -> we instead modify the endpoint to take multiple skucodes

    // we want this request param format
    // http://localhost:8082/api/inventory?sku-code=iphone-13&sky-code=tesla
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode) { // acecept skuCode and check if that particular item is in stock
        return inventoryService.isInStock(skuCode);
    }
}
