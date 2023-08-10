package com.mokham.security.service;

import com.mokham.security.model.db1.Product;
import com.mokham.security.model.db2.Category;
import com.mokham.security.model.db2.CategoryDTO;
import com.mokham.security.repository2.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryRepository categoryRepository;
    private CategoryDTO convertEntityToDto(Category category) {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(category, CategoryDTO.class);
    }

    private Category convertDtoToEntity(CategoryDTO categoryDTO) {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(categoryDTO, Category.class);
    }

    @Override
    public String save(CategoryDTO categoryDTO) {
        categoryRepository.save(convertDtoToEntity(categoryDTO));
        return "Category added successfully.";
    }

    @Override
    public List<CategoryDTO> getAll() {
        List<Category> productList = categoryRepository.findAll();
        return productList.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }
}
