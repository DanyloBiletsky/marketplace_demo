package dev.biletskyi.productservice.domain.service;

import dev.biletskyi.productservice.api.ProductCreateRequest;
import dev.biletskyi.productservice.api.ProductUpdateRequest;
import dev.biletskyi.productservice.domain.db.Product;


public interface ProductService {
    Product create(ProductCreateRequest createRequest);
    Product update(Long id, ProductUpdateRequest updateRequest);
    Product getById(Long id);
    void delete(Long id);
}
