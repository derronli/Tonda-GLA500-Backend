package com.tonda.eccomerce.orderservice.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.tonda.eccomerce.orderservice.dto.InventoryResponse;
import com.tonda.eccomerce.orderservice.dto.OrderLineItemsDto;
import com.tonda.eccomerce.orderservice.dto.OrderRequest;
import com.tonda.eccomerce.orderservice.model.Order;
import com.tonda.eccomerce.orderservice.model.OrderLineItems;
import com.tonda.eccomerce.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

// Will create the order object from DTO - to be saved into DB
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    @Autowired
    public OrderService(OrderRepository orderRepository, WebClient.Builder webClientBuilder) {
        this.orderRepository = orderRepository;
        this.webClientBuilder = webClientBuilder;
    }

    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        // random number as order ID
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                                                          .stream()
                                                          .map(orderLineItemsDto -> mapToDto(orderLineItemsDto))
                                                          .toList();
        order.setOrderLineItemsList(orderLineItems);

        List<String> skuCodes = order.getOrderLineItemsList().stream()
                                     .map(orderLineItem -> orderLineItem.getSkuCode()).toList();

        // NOTE: we only want to place an order if items are IN STOCK -> i.e. need to call Inventory Service

        // call the service (remember based on our endpoint implementation -> we need the request parameters in the url)
        // web client will build it for us with uriBuilder !
        InventoryResponse[] inventoryResponses = webClientBuilder.build().get()
                                  .uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                                  .retrieve()
                                  .bodyToMono(InventoryResponse[].class) // data type in the framework. need it to read response + will parse the response to an array
                                  .block();

        // basically just loops over every inventory response in the array and checks if each is in stock
        // This below is  just weird syntax
        boolean allProductsInStock = Arrays.stream(inventoryResponses).allMatch(inventoryResponse -> inventoryResponse.isInStock());

        // Make sure its in stock
        if (allProductsInStock) {
            // Save to DB by interfacing with repository
            orderRepository.save(order);
        }
        else {
            throw new IllegalArgumentException("Product is not in stock, please try again later");
        }

    }
    // recall: orderlineitems each represent a single item
    // We convert the dto to a single orderlineitem and send it back
    // which then puts them all into a list
    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
