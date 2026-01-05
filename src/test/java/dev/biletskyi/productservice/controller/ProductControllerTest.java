package dev.biletskyi.productservice.controller;

import dev.biletskyi.productservice.api.ProductController;
import dev.biletskyi.productservice.api.ProductDto;
import dev.biletskyi.productservice.api.ProductDtoMapper;
import dev.biletskyi.productservice.domain.db.Product;
import dev.biletskyi.productservice.domain.service.DbProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import java.time.Instant;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbProductService dbProductService;

    @MockBean
    private ProductDtoMapper productDtoMapper;

    @Test
    public void getProductById_returnsProductDto() throws Exception {

        Long id = 42L;
        Product product = Product.builder()
                .id(id)
                .name("Test product")
                .price(new BigDecimal("12.34"))
                .description("desc")
                .createdAt(Instant.parse("2024-01-01T00:00:00Z"))
                .updatedAt(Instant.parse("2024-01-02T00:00:00Z"))
                .build();

        ProductDto dto = new ProductDto(
                id,
                "Test product",
                new BigDecimal("12.34"),
                "desc",
                Instant.parse("2024-01-01T00:00:00Z"),
                Instant.parse("2024-01-02T00:00:00Z")
        );

        when(dbProductService.getById(id)).thenReturn(product);
        when(productDtoMapper.toProductDto(product)).thenReturn(dto);

        // when / then
        mockMvc.perform(get("/api/products/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id.intValue()))
                .andExpect(jsonPath("$.name").value("Test product"))
                .andExpect(jsonPath("$.price").value(12.34))
                .andExpect(jsonPath("$.description").value("desc"));
    }
}
