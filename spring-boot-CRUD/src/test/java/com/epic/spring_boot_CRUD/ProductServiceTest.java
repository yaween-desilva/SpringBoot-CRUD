package com.epic.spring_boot_CRUD;

import com.epic.spring_boot_CRUD.dto.ProductDTO;
import com.epic.spring_boot_CRUD.entity.Product;
import com.epic.spring_boot_CRUD.repository.ProductRepository;
import com.epic.spring_boot_CRUD.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;



public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private ProductDTO productDTO;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateProduct() {
        product = new Product();
        product.setId(1L);
        product.setName("testName");
        product.setDescription("testDescription");
        product.setPrice(BigDecimal.valueOf(100.00));
        product.setQuantity(10);

        productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName("testName");
        productDTO.setDescription("testDescription");
        productDTO.setPrice(BigDecimal.valueOf(100.00));
        productDTO.setQuantity(10);

        when(modelMapper.map(any(ProductDTO.class), eq(Product.class))).thenReturn(product);
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(modelMapper.map(any(Product.class), eq(ProductDTO.class))).thenReturn(productDTO);

        ProductDTO result = productService.createProduct(productDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("testName", result.getName());
        assertEquals("testDescription", result.getDescription());
        assertEquals(BigDecimal.valueOf(100.00), result.getPrice());
        assertEquals(10, result.getQuantity());

        verify(productRepository, times(1)).save(any(Product.class));
        verify(modelMapper, times(1)).map(any(ProductDTO.class), eq(Product.class));
        verify(modelMapper, times(1)).map(any(Product.class), eq(ProductDTO.class));
    }

    @Test
    public void testGetAllProducts(){
        //creating two products for test
        Product product1 = new Product();
        Product product2 = new Product();

        //List to store the products
        List<Product> expectedProduct = Arrays.asList(product1, product2);

        when(productRepository.findAll()).thenReturn(expectedProduct);

        //List to store the products resulted in service class method
        List<Product> actualProduct = productService.getAllProducts();

        //check whether the elements in both arrays are equal
        assertEquals(expectedProduct, actualProduct);
    }

    @Test
    public void testGetProductById(){
        product = new Product();
        product.setId(1L);
        product.setName("testName");
        product.setDescription("testDescription");
        product.setPrice(BigDecimal.valueOf(100.00));
        product.setQuantity(10);

        productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName("testName");
        productDTO.setDescription("testDescription");
        productDTO.setPrice(BigDecimal.valueOf(100.00));
        productDTO.setQuantity(10);

        when(productRepository.findById(2L)).thenReturn(Optional.of(product));
        when(modelMapper.map(product, ProductDTO.class)).thenReturn(productDTO);

        ProductDTO result = productService.getProductById(2L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("testName", result.getName());
        assertEquals("testDescription", result.getDescription());
        assertEquals(BigDecimal.valueOf(100.00), result.getPrice());
        assertEquals(10, result.getQuantity());
    }

    @Test
    public void testUpdateProduct(){
        Product product = new Product();
        product.setId(1L);
        product.setName("testName");
        product.setDescription("testDescription");
        product.setPrice(BigDecimal.valueOf(100.00));
        product.setQuantity(10);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("testNameNew");
        productDTO.setDescription("testDescriptionNew");
        productDTO.setPrice(BigDecimal.valueOf(99.00));
        productDTO.setQuantity(20);

        Product updatedProduct = new Product();
        updatedProduct.setId(1L);
        updatedProduct.setName(productDTO.getName());
        updatedProduct.setDescription(productDTO.getDescription());
        updatedProduct.setPrice(productDTO.getPrice());
        updatedProduct.setQuantity(productDTO.getQuantity());

        ProductDTO updatedProductDTO = new ProductDTO();
        updatedProductDTO.setId(1L);
        updatedProductDTO.setName("testNameNew");
        updatedProductDTO.setDescription("testDescriptionNew");
        updatedProductDTO.setPrice(BigDecimal.valueOf(99.00));
        updatedProductDTO.setQuantity(20);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);
        when(modelMapper.map(productDTO, Product.class)).thenReturn(updatedProduct);
        when(modelMapper.map(updatedProduct, ProductDTO.class)).thenReturn(updatedProductDTO);

        ProductDTO result = productService.updateProduct(1L, productDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("testNameNew", result.getName());
        assertEquals("testDescriptionNew", result.getDescription());
        assertEquals(BigDecimal.valueOf(99.00), result.getPrice());
        assertEquals(20, result.getQuantity());
    }

    @Test
    public void testDeleteProduct(){
        Product product = new Product();
        product.setId(1L);
        product.setName("testName");
        product.setDescription("testDescription");
        product.setPrice(BigDecimal.valueOf(100.00));
        product.setQuantity(10);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        productService.deleteProduct(1L);

        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).delete(product);
    }
}
