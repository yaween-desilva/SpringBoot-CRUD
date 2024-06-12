package com.epic.spring_boot_CRUD.repository;

import com.epic.spring_boot_CRUD.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
