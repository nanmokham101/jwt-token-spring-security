package com.mokham.security.service;

import com.mokham.security.model.db3.OrderDTO;

import java.util.List;

public interface OrderService {
    String save(OrderDTO orderDTO);
    List<OrderDTO> getAll();
}
