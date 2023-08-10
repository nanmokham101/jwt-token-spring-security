package com.mokham.security.service;

import com.mokham.security.model.db1.ProductDTO;
import com.mokham.security.model.db2.CategoryDTO;

import java.util.List;

public interface CategoryService {
    String save(CategoryDTO categoryDTO);
    List<CategoryDTO> getAll();
}
