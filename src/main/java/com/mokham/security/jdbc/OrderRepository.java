package com.mokham.security.jdbc;

import com.mokham.security.model.db3.Order;
import com.mokham.security.service.OrderRowMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderRepository {

    private final JdbcTemplate jdbcTemplate;

    public OrderRepository(@Qualifier("orderJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create() {
        String sql = "CREATE TABLE IF NOT EXISTS orders (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "order_number VARCHAR(50) NOT NULL," +
                "order_date DATE," +
                "total_amount DECIMAL(10, 2)," +
                "customer_name VARCHAR(50) " +
                ")";

        jdbcTemplate.execute(sql);
    }

    public String save(Order order) {
        String sql = "INSERT INTO orders (order_number, order_date, total_amount, customer_name) VALUES (?, ?, ?, ?)";

        jdbcTemplate.update(
                sql,
                order.getOrderNumber(),
                order.getOrderDate(),
                order.getTotalAmount(),
                order.getCustomerName()

        );
        return "Order added successfully.";
    }
     public List<Order> getAll() {
         return jdbcTemplate.query("SELECT * FROM orders", new OrderRowMapper());
     }
}