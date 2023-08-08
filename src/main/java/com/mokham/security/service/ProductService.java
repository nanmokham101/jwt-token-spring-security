package com.mokham.security.service;

import com.mokham.security.dto.ProductDto;

import java.util.List;

public interface ProductService {
    String save(ProductDto productDto, String jwtToken);
    ProductDto findById(Integer id);

    List<ProductDto> getAll();

    List<ProductDto> findByUserId(Integer id);
}
