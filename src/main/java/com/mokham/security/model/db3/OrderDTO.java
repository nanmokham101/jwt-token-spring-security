package com.mokham.security.model.db3;

import lombok.Data;

import java.util.Date;

@Data
public class OrderDTO {
    private String orderNumber;
    private Integer totalAmount;
    private Date orderDate;
    private String customerName;
}
