package com.epic.spring_boot_CRUD.service;

import com.epic.spring_boot_CRUD.dto.ProductDTO;
import com.epic.spring_boot_CRUD.entity.Product;
import com.epic.spring_boot_CRUD.exception.ProductNotFoundException;
import com.epic.spring_boot_CRUD.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    ModelMapper modelmapper;

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

    public ProductDTO updateProduct(Long id, ProductDTO productDetails){
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product Not Found"));
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setQuantity(productDetails.getQuantity());
        return convertToDTO(productRepository.save(product));
    }

    public void deleteProduct(Long id){
        Product product = productRepository.findById(id).orElseThrow(()-> new ProductNotFoundException("Product Not Found"));
        productRepository.delete(product);
    }

    public ProductDTO convertToDTO(Product product){
        return modelmapper.map(product, ProductDTO.class);
    }

    public Product convertToEntity(ProductDTO productDTO){
        return modelmapper.map(productDTO, Product.class);
    }
}
