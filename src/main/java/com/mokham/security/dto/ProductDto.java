package com.mokham.security.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.mokham.security.model.User;
import lombok.Data;
import org.springframework.http.HttpStatusCode;

@Data
public class ProductDto {
    private String name;
    private int price;
    private String description;
    @JsonIgnore
    private User user;
}
