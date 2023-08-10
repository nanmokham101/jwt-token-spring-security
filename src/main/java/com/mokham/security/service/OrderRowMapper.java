package com.mokham.security.service;

import com.mokham.security.model.db3.Order;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRowMapper implements RowMapper<Order> {
    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        Order order = new Order();
        order.setId(rs.getInt("id"));
        order.setOrderNumber(rs.getString("order_number"));
        order.setOrderDate(rs.getDate("order_date"));
        order.setTotalAmount(rs.getInt("total_amount"));
        order.setCustomerName(rs.getString("customer_name"));
        // Map other properties if needed
        return order;
    }
}
