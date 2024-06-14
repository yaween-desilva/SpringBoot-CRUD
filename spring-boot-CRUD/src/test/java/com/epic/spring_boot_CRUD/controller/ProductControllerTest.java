package com.epic.spring_boot_CRUD.controller;

import com.epic.spring_boot_CRUD.dto.ProductDTO;
import com.epic.spring_boot_CRUD.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    private ProductDTO productDTO;

    @BeforeEach
    void setUp() {
        productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName("testName");
        productDTO.setDescription("testDescription");
        productDTO.setPrice(BigDecimal.valueOf(100.00));
        productDTO.setQuantity(10);
    }

    @Test
    void testCreateProduct() throws Exception {
        when(productService.createProduct(any(ProductDTO.class))).thenReturn(productDTO);

        mockMvc.perform(post("/api/products/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("testName"))
                .andExpect(jsonPath("$.description").value("testDescription"))
                .andExpect(jsonPath("$.price").value(100.00))
                .andExpect(jsonPath("$.quantity").value(10));
    }

    @Test
    void testGetAllProductsWithPaginationAndSorting() throws Exception {
        List<ProductDTO> products = Arrays.asList(productDTO);
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));
        Page<ProductDTO> productPage = new PageImpl<>(products, pageable, products.size());

        when(productService.getAllProducts(anyInt(), anyInt(), anyString())).thenReturn(productPage);

        mockMvc.perform(get("/api/products/getAll")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.size()").value(1))
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[0].name").value("testName"))
                .andExpect(jsonPath("$.content[0].description").value("testDescription"))
                .andExpect(jsonPath("$.content[0].price").value(100.00))
                .andExpect(jsonPath("$.content[0].quantity").value(10));
    }

    @Test
    public void testGetProductById() throws Exception{
        when(productService.getProductById(1L)).thenReturn(productDTO);

        mockMvc.perform(get("/api/products/getAll/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("testName"))
                .andExpect(jsonPath("$.description").value("testDescription"))
                .andExpect(jsonPath("$.price").value(100.00))
                .andExpect(jsonPath("$.quantity").value(10));
    }

    @Test
    public void testUpdateProduct() throws Exception{
        when(productService.updateProduct(eq(1L), any(ProductDTO.class))).thenReturn(productDTO);

        mockMvc.perform(put("/api/products/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("testName"))
                .andExpect(jsonPath("$.description").value("testDescription"))
                .andExpect(jsonPath("$.price").value(100.00))
                .andExpect(jsonPath("$.quantity").value(10));
    }

    @Test
    public void testDeleteProduct() throws Exception{
        Mockito.doNothing().when(productService).deleteProduct(1L);

        mockMvc.perform(delete("/api/products/delete/1"))
                .andExpect(status().isOk());
    }
}
