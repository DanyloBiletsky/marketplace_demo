package dev.biletskyi.productservice.api;

import dev.biletskyi.productservice.domain.db.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface ProductDtoMapper {
    // перетворює DTO у JPA‑сутність для збереження в БД
    Product toEntity(ProductDto productDto);

    // створює DTO для повернення клієнту або логіки сервісу.
    ProductDto toProductDto(Product productEntity);
}
