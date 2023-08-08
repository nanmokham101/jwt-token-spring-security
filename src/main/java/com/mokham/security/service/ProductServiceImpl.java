package com.mokham.security.service;

import com.mokham.security.dto.ProductDto;
import com.mokham.security.model.Product;
import com.mokham.security.model.User;
import com.mokham.security.reposirory.ProductRepository;
import com.mokham.security.reposirory.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    private ProductDto convertEntityToDto(Product product) {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(product, ProductDto.class);
    }

    private Product convertDtoToEntity(ProductDto productDto, User user) {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        Product product = modelMapper.map(productDto, Product.class);
        product.setUser(user);
        return product;
    }
    @Override
    public String save(ProductDto productDto, String jwtToken) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken).getBody();
        String currentUserId = claims.getSubject();

        Optional<User> userOptional = userRepository.findByEmail(currentUserId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Product product = convertDtoToEntity(productDto, user);
            productRepository.save(product);
            return "Add product successful.";
        } else {
            throw new RuntimeException("User not found with email: " + currentUserId);
        }
    }

    @Override
    public ProductDto findById(Integer id){
        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            return convertEntityToDto(product);
        } else {
            throw new RuntimeException("Product not found with this id : " + id);
        }
    }

    @Override
    public List<ProductDto> getAll() {
        List<Product> productList = productRepository.findAll();
        return productList.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> findByUserId(Integer id) {
        log.info("id.... : "+ id);
        List<Product> productList = productRepository.findProductsByUserId(id);
        return productList.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }
}
