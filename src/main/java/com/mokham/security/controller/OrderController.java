package com.mokham.security.controller;

import com.mokham.security.model.db2.CategoryDTO;
import com.mokham.security.model.db3.OrderDTO;
import com.mokham.security.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping("/save")
    public String save(@RequestBody OrderDTO orderDTO) {
        return orderService.save(orderDTO);
    }
    @GetMapping("/all")
    public List<OrderDTO> getAll(){
        return orderService.getAll();
    }
}
