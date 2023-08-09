package com.mokham.security.model.db1;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class ProductDTO {
    private String name;
    private int price;
    private String description;
    @JsonIgnore
    private User user;
}
