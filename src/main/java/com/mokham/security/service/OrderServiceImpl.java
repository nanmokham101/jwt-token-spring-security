package com.mokham.security.service;

import com.mokham.security.jdbc.OrderRepository;
import com.mokham.security.model.db3.Order;
import com.mokham.security.model.db3.OrderDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private OrderRepository orderRepository;
    private OrderDTO convertEntityToDto(Order order) {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(order, OrderDTO.class);
    }

    private Order convertDtoToEntity(OrderDTO orderDTO) {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        Order order =modelMapper.map(orderDTO, Order.class);
        order.setOrderDate(new Date());
        return order;
    }
    @Override
    public String save(OrderDTO orderDTO) {
        return orderRepository.save(convertDtoToEntity(orderDTO));
    }

    @Override
    public List<OrderDTO> getAll() {
        List<Order> orderList = orderRepository.getAll();
        return  orderList.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }
}
