package dev.biletskyi.productservice.domain.service;

import dev.biletskyi.productservice.api.ProductCreateRequest;
import dev.biletskyi.productservice.api.ProductUpdateRequest;
import dev.biletskyi.productservice.domain.db.Product;
import dev.biletskyi.productservice.domain.db.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@AllArgsConstructor
/**
 * This class works with the MANUAL Cache Mode
 */
public class ManualCachingProductService implements ProductService {

    private final ProductRepository productRepository;
    private final RedisTemplate<String, Product> redisTemplate;

    private static final String CACHE_KEY_PREFIX = "product:";
    private static final long CACHE_TTL_MINUTES = 1;

    @Override
    public Product create(ProductCreateRequest createRequest) {
        log.info("Creating product in DB: {}", createRequest.name());
        Product product = Product.builder()
                .name(createRequest.name())
                .price(createRequest.price())
                .description(createRequest.description())
                .build();
        return productRepository.save(product);
    }

    @Override
    public Product update(Long id, ProductUpdateRequest updateRequest) {
        log.info("Updating product in DB: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found: " + id));

        if (updateRequest.price() != null) {
            product.setPrice(updateRequest.price());
        }
        if (updateRequest.description() != null) {
            product.setDescription(updateRequest.description());
        }

        var savedProduct = productRepository.save(product);

        String cacheKey = CACHE_KEY_PREFIX + id;
        redisTemplate.delete(cacheKey);
        log.info("Cache invalidated for updated product: id={}", id);

        return savedProduct;
    }

    @Override
    public Product getById(Long id) {
        log.info("Getting product: id={}", id);
        String cacheKey = CACHE_KEY_PREFIX + id;

        Product entityFromCache = redisTemplate.opsForValue()
                .get(cacheKey);

        if (entityFromCache != null) {
            log.info("Product found in cache: id={}", id);
            return entityFromCache;
        }

        // if entityFromCache == null:
        log.info("Product not found in cache: id={}", id);
        Product entityFromDb = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found: " + id));

        //caching the product in Redis:
        redisTemplate.opsForValue()
                .set(cacheKey, entityFromDb, CACHE_TTL_MINUTES, TimeUnit.MINUTES);
        log.info("Product cached: id={}", id);

        return entityFromDb;
    }

    @Override
    public void delete(Long id) {

        log.info("Deleting product from DB: {}", id);

        if (productRepository.existsById(id) == false) {
            throw new RuntimeException("Product not found: " + id);
        }
        //deleting from DB:
        productRepository.deleteById(id);

        //deleting from cache:
        String cacheKey = CACHE_KEY_PREFIX + id;
        redisTemplate.delete(cacheKey);

        log.info("Cache invalidated for deleted product: id={}", id);
    }
}
