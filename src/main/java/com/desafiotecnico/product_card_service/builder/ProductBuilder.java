package com.desafiotecnico.product_card_service.builder;

import com.desafiotecnico.product_card_service.entity.Product;
import com.desafiotecnico.product_card_service.record.CreateProductRecord;
import com.desafiotecnico.product_card_service.record.ProductResponseDTO;
import com.desafiotecnico.product_card_service.record.UpdateProductRecord;

public class ProductBuilder {

    private ProductBuilder() {
        // Construtor privado para evitar instâncias
    }

    // Convert CreateProductRecord to Product
    public static Product ProductRecordToProduct(CreateProductRecord request) {
        return Product.builder()
                .name(request.name())
                .price(request.price())
                .build();
    }

    // Convert UpdateProductRecord to Product
    public static Product updateProductRecordToProduct(UpdateProductRecord request, Product existingProduct) {
        return Product.builder()
                .id(existingProduct.getId())  // preserve ID from existingProduct
                .name(request.name() != null ? request.name() : existingProduct.getName())  // updates name
                .price(request.price() != null ? request.price() : existingProduct.getPrice())  // updates price
                .creationDate(existingProduct.getCreationDate())  // preserves creationDate
                .updateDate(java.time.LocalDateTime.now())  // updates creationTime
                .build();
    }

    // Product to ProductResponseDTO
    public static ProductResponseDTO productToResponseDTO(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getCreationDate()
        );
    }
}