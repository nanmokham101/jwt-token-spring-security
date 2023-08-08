package com.mokham.security.reposirory;

import com.mokham.security.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    @Query(value = "SELECT * FROM _product WHERE user_id = ?1", nativeQuery = true)
    List<Product> findProductsByUserId(Integer id);
}
