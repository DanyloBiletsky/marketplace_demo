package dev.biletskyi.productservice.api;

import java.math.BigDecimal;
import java.time.Instant;
/*
DTO для відповіді клієнту.
View for Entity without inner details
 */
public record ProductDto(
        Long id,
        String name,
        BigDecimal price,
        String description,
        Instant createdAt,
        Instant updatedAt
) {}
