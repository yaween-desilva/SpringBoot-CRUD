package com.epic.spring_boot_CRUD.controller;

import com.epic.spring_boot_CRUD.config.TestConfig;
import com.epic.spring_boot_CRUD.controller.ProductController;
import com.epic.spring_boot_CRUD.dto.ProductDTO;
import com.epic.spring_boot_CRUD.service.ProductService;
import com.epic.spring_boot_CRUD.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@ActiveProfiles("test")
@Import({TestConfig.class, TokenService.class})
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TokenService tokenService;

    private ProductDTO productDTO;
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName("testName");
        productDTO.setDescription("testDescription");
        productDTO.setPrice(BigDecimal.valueOf(100.00));
        productDTO.setQuantity(10);

        User user = new User("user", "password", List.of(new SimpleGrantedAuthority("ROLE_USER")));
        authentication = new UsernamePasswordAuthenticationToken(user, "password", user.getAuthorities());
    }

    @Test
    void testCreateProduct() throws Exception {
        String token = tokenService.generateToken(authentication);

        when(productService.createProduct(any(ProductDTO.class))).thenReturn(productDTO);

        mockMvc.perform(post("/api/products/add")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
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
        String token = tokenService.generateToken(authentication);

        List<ProductDTO> products = Arrays.asList(productDTO);
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));
        Page<ProductDTO> productPage = new PageImpl<>(products, pageable, products.size());

        when(productService.getAllProducts(anyInt(), anyInt(), anyString())).thenReturn(productPage);

        mockMvc.perform(get("/api/products/getAll")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
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
    void testGetProductById() throws Exception {
        String token = tokenService.generateToken(authentication);

        when(productService.getProductById(1L)).thenReturn(productDTO);

        mockMvc.perform(get("/api/products/getAll/1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("testName"))
                .andExpect(jsonPath("$.description").value("testDescription"))
                .andExpect(jsonPath("$.price").value(100.00))
                .andExpect(jsonPath("$.quantity").value(10));
    }

    @Test
    void testUpdateProduct() throws Exception {
        String token = tokenService.generateToken(authentication);

        when(productService.updateProduct(eq(1L), any(ProductDTO.class))).thenReturn(productDTO);

        mockMvc.perform(put("/api/products/update/1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
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
    void testDeleteProduct() throws Exception {
        String token = tokenService.generateToken(authentication);

        Mockito.doNothing().when(productService).deleteProduct(1L);

        mockMvc.perform(delete("/api/products/delete/1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk());
    }
}



//package com.epic.spring_boot_CRUD.controller;
//
//import com.epic.spring_boot_CRUD.dto.ProductDTO;
//import com.epic.spring_boot_CRUD.service.ProductService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.domain.*;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.math.BigDecimal;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(ProductController.class)
//class ProductControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private ProductService productService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    private ProductDTO productDTO;
//
//    @BeforeEach
//    void setUp() {
//        productDTO = new ProductDTO();
//        productDTO.setId(1L);
//        productDTO.setName("testName");
//        productDTO.setDescription("testDescription");
//        productDTO.setPrice(BigDecimal.valueOf(100.00));
//        productDTO.setQuantity(10);
//    }
//
//    @Test
//    @WithMockUser(username = "yaween", password = "2003", roles = "USER")
//    void testCreateProduct() throws Exception {
//        when(productService.createProduct(any(ProductDTO.class))).thenReturn(productDTO);
//
//        mockMvc.perform(post("/api/products/add")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(productDTO)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1L))
//                .andExpect(jsonPath("$.name").value("testName"))
//                .andExpect(jsonPath("$.description").value("testDescription"))
//                .andExpect(jsonPath("$.price").value(100.00))
//                .andExpect(jsonPath("$.quantity").value(10));
//    }
//
//    @Test
//    void testGetAllProductsWithPaginationAndSorting() throws Exception {
//        List<ProductDTO> products = Arrays.asList(productDTO);
//        Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));
//        Page<ProductDTO> productPage = new PageImpl<>(products, pageable, products.size());
//
//        when(productService.getAllProducts(anyInt(), anyInt(), anyString())).thenReturn(productPage);
//
//        mockMvc.perform(get("/api/products/getAll")
//                        .param("page", "0")
//                        .param("size", "10")
//                        .param("sortBy", "id")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.content.size()").value(1))
//                .andExpect(jsonPath("$.content[0].id").value(1L))
//                .andExpect(jsonPath("$.content[0].name").value("testName"))
//                .andExpect(jsonPath("$.content[0].description").value("testDescription"))
//                .andExpect(jsonPath("$.content[0].price").value(100.00))
//                .andExpect(jsonPath("$.content[0].quantity").value(10));
//    }
//
//    @Test
//    public void testGetProductById() throws Exception{
//        when(productService.getProductById(1L)).thenReturn(productDTO);
//
//        mockMvc.perform(get("/api/products/getAll/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1L))
//                .andExpect(jsonPath("$.name").value("testName"))
//                .andExpect(jsonPath("$.description").value("testDescription"))
//                .andExpect(jsonPath("$.price").value(100.00))
//                .andExpect(jsonPath("$.quantity").value(10));
//    }
//
//    @Test
//    public void testUpdateProduct() throws Exception{
//        when(productService.updateProduct(eq(1L), any(ProductDTO.class))).thenReturn(productDTO);
//
//        mockMvc.perform(put("/api/products/update/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(productDTO)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1L))
//                .andExpect(jsonPath("$.name").value("testName"))
//                .andExpect(jsonPath("$.description").value("testDescription"))
//                .andExpect(jsonPath("$.price").value(100.00))
//                .andExpect(jsonPath("$.quantity").value(10));
//    }
//
//    @Test
//    public void testDeleteProduct() throws Exception{
//        Mockito.doNothing().when(productService).deleteProduct(1L);
//
//        mockMvc.perform(delete("/api/products/delete/1"))
//                .andExpect(status().isOk());
//    }
//}
