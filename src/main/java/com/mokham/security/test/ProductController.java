package com.mokham.security.test;

import com.mokham.security.dto.ProductDto;
import com.mokham.security.model.Role;
import com.mokham.security.reposirory.ProductRepository;
import com.mokham.security.service.ProductService;
import com.mokham.security.service.ProductServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/v1/product")

public class ProductController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ProductDto getProductById(@RequestParam Integer id){
        return productService.findById(id);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public List<ProductDto> getAllProducts(){
        return productService.getAll();
    }

    @GetMapping("/findProductsByUserId")
    @PreAuthorize("hasRole('ADMIN')")
    public List<ProductDto> findByUserId(@RequestParam Integer id){
        return productService.findByUserId(id);
    }

    @PostMapping("/new")
    public ResponseEntity<String> addProduct(@RequestBody ProductDto productDto, HttpServletRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        if (authorities.stream().anyMatch(a -> a.getAuthority().equals(Role.ADMIN.name()))) {
            String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (jwtToken != null && jwtToken.startsWith("Bearer ")) {
                jwtToken = jwtToken.substring(7);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or missing JWT token");
            }

            String result = productService.save(productDto, jwtToken);
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
        }
    }



    @PutMapping
    @PreAuthorize("hasAuthority('admin:update')")

    public String put() {
        return "PUT:: admin controller";
    }
    @DeleteMapping
    @PreAuthorize("hasAuthority('admin:delete')")

    public String delete() {
        return "DELETE:: admin controller";
    }
}
