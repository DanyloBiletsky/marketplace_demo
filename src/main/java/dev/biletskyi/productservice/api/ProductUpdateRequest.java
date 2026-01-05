package dev.biletskyi.productservice.api;

import java.math.BigDecimal;

/*
DTO для часткового оновлення;
містить лише ті поля, які можна змінювати
 */
public record ProductUpdateRequest(
        BigDecimal price,
        String description
) {}