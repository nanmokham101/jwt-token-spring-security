package com.mokham.security.test;

import com.mokham.security.model.db1.ProductDTO;
import com.mokham.security.model.db1.Role;
import com.mokham.security.model.db2.CategoryDTO;
import com.mokham.security.service.CategoryService;
import com.mokham.security.service.ProductService;
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
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ProductDTO getProductById(@RequestParam Integer id){
        return productService.findById(id);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public List<ProductDTO> getAllProducts(){
        return productService.getAll();
    }

    @GetMapping("/findProductsByUserId")
    @PreAuthorize("hasRole('ADMIN')")
    public List<ProductDTO> findByUserId(@RequestParam Integer id){
        return productService.findByUserId(id);
    }

    @PostMapping("/new")
    public ResponseEntity<String> addProduct(@RequestBody ProductDTO productDto, HttpServletRequest request) {

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


    @PostMapping("/saveCategory")
    public String saveTeacher(@RequestBody CategoryDTO categoryDTO) {
       return categoryService.save(categoryDTO);
    }
    @GetMapping("/allCategory")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public List<CategoryDTO> getAllCategory(){
        return categoryService.getAll();
    }
}
