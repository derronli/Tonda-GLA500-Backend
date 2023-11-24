package com.tonda.eccomerce.orderservice.service;

import java.util.List;
import java.util.UUID;

import com.tonda.eccomerce.orderservice.dto.OrderLineItemsDto;
import com.tonda.eccomerce.orderservice.dto.OrderRequest;
import com.tonda.eccomerce.orderservice.model.Order;
import com.tonda.eccomerce.orderservice.model.OrderLineItems;
import com.tonda.eccomerce.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Will create the order object from DTO - to be saved into DB
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
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
        // Save to DB by interfacing with repository
        orderRepository.save(order);
    }
    // recall: orderlineitems each represent a single item
    // We convert the dto to a single orderlineitem and send it back
    // which then puts them all into a list
    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItems.getQuantity());
        orderLineItems.setSkuCode(orderLineItems.getSkuCode());
        return orderLineItems;
    }
}
