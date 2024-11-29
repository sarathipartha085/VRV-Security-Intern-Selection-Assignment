package com.matops.vsv_security.repository;

import com.matops.vsv_security.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
