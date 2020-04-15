package com.scholanova.ecommerce.order.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.scholanova.ecommerce.order.entity.Orders;
import com.scholanova.ecommerce.order.repository.OrderRepository;
import com.scholanova.ecommerce.product.entity.Product;

@RestController
@EnableSpringDataWebSupport
@RequestMapping("/orders")
public class OrderController {
	
	private OrderRepository repository;
	
	public OrderController(@Autowired OrderRepository repository) {
		this.repository = repository;
	}

	@GetMapping
    public List<Orders> getAllOrders(Pageable pageable) {
        return repository.findAll(pageable).get().collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Orders getOrdersById(@PathVariable("id") Orders order) {
        return order;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Orders addOrders(@RequestBody Orders order){
        return repository.save(order);
    }
	
}
