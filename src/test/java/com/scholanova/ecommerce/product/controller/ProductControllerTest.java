package com.scholanova.ecommerce.product.controller;

import com.scholanova.ecommerce.product.entity.Product;
import com.scholanova.ecommerce.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extensions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductRepository repository;
    @InjectMocks
    private ProductController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void postProductShouldIgnoreAGivenID() throws Exception {
        Product product = Product.create("tested", "test", 1.5f, 0.2f, "EUR");
        product.setId(Long.valueOf(1234l));
        String body = "{\n" +
                "\t\"id\": 12,\n" +
                "\t\"name\": \"myNewProduct\",\n" +
                "\t\"description\": \"this is a test product for posting\",\n" +
                "\t\"vat\": 0.2,\n" +
                "\t\"priceVatExcluded\": 10.45,\n" +
                "\t\"currency\": \"EUR\"\n" +
                "}";

        given(repository.save(any(Product.class))).willReturn(product);

        mockMvc.perform(
                post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
        )
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id", is(1234)));
    }

}