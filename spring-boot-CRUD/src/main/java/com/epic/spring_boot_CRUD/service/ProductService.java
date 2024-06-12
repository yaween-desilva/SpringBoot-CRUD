package com.epic.spring_boot_CRUD.service;

import com.epic.spring_boot_CRUD.dto.ProductDTO;
import com.epic.spring_boot_CRUD.entity.Product;
import com.epic.spring_boot_CRUD.exception.ProductNotFoundException;
import com.epic.spring_boot_CRUD.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public ProductDTO createProduct(ProductDTO productDTO){
        Product product = convertToEntity(productDTO);
        return convertToDTO(productRepository.save(product));
    }
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }
    public ProductDTO getProductById(Long id){
        Product product = productRepository.findById(id).orElseThrow(()-> new ProductNotFoundException("Product Not Found"));
        return convertToDTO(product);
    }
    public Product updateProduct(Long id, Product productDetails){
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product Not Found"));
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setQuantity(productDetails.getQuantity());
        return productRepository.save(product);
    }
    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

    public static ProductDTO convertToDTO(Product product){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        product.setQuantity(product.getQuantity());
        return productDTO;
    }

    public static Product convertToEntity(ProductDTO productDTO){
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());
        return product;
    }

    public void updateEntity(Product product, ProductDTO productDTO){
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());
    }
}
