package com.mokham.security.service;

import com.mokham.security.model.db1.ProductDTO;

import java.util.List;

public interface ProductService {
    String save(ProductDTO productDto, String jwtToken);
    ProductDTO findById(Integer id);

    List<ProductDTO> getAll();

    List<ProductDTO> findByUserId(Integer id);
}
