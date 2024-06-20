package com.epic.spring_boot_CRUD.service;

import com.epic.spring_boot_CRUD.dto.ProductDTO;
import com.epic.spring_boot_CRUD.entity.Product;
import com.epic.spring_boot_CRUD.exception.ProductNotFoundException;
import com.epic.spring_boot_CRUD.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    ModelMapper modelmapper;

    //Method to create a new method
    @CachePut(value = "products", key = "#result.id")
    public ProductDTO createProduct(ProductDTO productDTO){
        //Set the ID to null so that the ID will auto increment
        productDTO.setId(null);
        Product product = convertToEntity(productDTO);
        return convertToDTO(productRepository.save(product));
    }

    //Method to retrieve all available products
//    @Cacheable(value = "productsList", key = "#page + '-' + #size + '-' + #sortBy")
//    @Cacheable(value = "productsList", key = "'page' + 0 + 'size' + 10 + 'sortBy' + #id")
    public Page<ProductDTO> getAllProducts(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return productRepository.findAll(pageable).map(product -> modelmapper.map(product, ProductDTO.class));
    }

    //Method to retrieve a product using the specific id of the product
    @Cacheable(value = "products", key = "#id")
    public ProductDTO getProductById(Long id){
        Product product = productRepository.findById(id).orElseThrow(()-> new ProductNotFoundException("Product Not Found"));
        return convertToDTO(product);
    }

    //Method to update the existing information of a product
    @CachePut(value = "products", key = "#id")
    @CacheEvict(value = {"productsList"}, allEntries = true)
    public ProductDTO updateProduct(Long id, ProductDTO productDetails){
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product Not Found"));

        //setting the updated details to the existing details
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setQuantity(productDetails.getQuantity());

        return convertToDTO(productRepository.save(product));
    }

    //Method to delete an existing product using the particular id of it
    @CacheEvict(value = {"products", "productsList"}, key = "#id", allEntries = true)
    public void deleteProduct(Long id){
        Product product = productRepository.findById(id).orElseThrow(()-> new ProductNotFoundException("Product Not Found"));
        productRepository.delete(product);
    }

    //Method to convert product to productDTO
    public ProductDTO convertToDTO(Product product){
        return modelmapper.map(product, ProductDTO.class);
    }

    //Method to convert productDTO to product
    public Product convertToEntity(ProductDTO productDTO){
        return modelmapper.map(productDTO, Product.class);
    }
}
