package com.desafiotecnico.product_card_service.builder;

import com.desafiotecnico.product_card_service.entity.Product;
import com.desafiotecnico.product_card_service.record.ProductRecordCreate;
import com.desafiotecnico.product_card_service.record.ProductResponseDTO;
import com.desafiotecnico.product_card_service.record.ProducRecordUpdate;

public class ProductBuilder {

    private ProductBuilder() {
    }

    // Convert CreateProductRecord to Product
    // The plant for the new entity
    public static Product ProductRecordToProduct(ProductRecordCreate request) {
        return Product.builder()
                .name(request.name())
                .price(request.price())
                .build();
    }

    // Convert UpdateProductRecord to Product

    //instructions to update
    public static Product updateProductRecordToProduct(ProducRecordUpdate request, Product existingProduct) {
        return Product.builder()
                .id(existingProduct.getId())  // preserve ID from existingProduct
                .name(request.name() != null ? request.name() : existingProduct.getName())  // updates name
                .price(request.price() != null ? request.price() : existingProduct.getPrice())  // updates price
                .creationDate(existingProduct.getCreationDate())  // preserves creationDate
                .updateDate(java.time.LocalDateTime.now())  // updates creationTime
                .build();
    }

    // Product to ProductResponseDTO

    //pictures of the entity
    public static ProductResponseDTO productToResponseDTO(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getCreationDate()
        );
    }
}