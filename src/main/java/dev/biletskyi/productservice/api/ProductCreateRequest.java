package dev.biletskyi.productservice.api;

import java.math.BigDecimal;

/*
 DTO для створення нового продукту. Містить тільки поля,
 які клієнт може/повинен передати при створенні (name, price, description).
 Використовується в контролері як вхідний payload і
 дозволяє відокремити API‑контракт від внутрішньої сутності.
 */
public record ProductCreateRequest (
        String name,
        BigDecimal price,
        String description
) {}
