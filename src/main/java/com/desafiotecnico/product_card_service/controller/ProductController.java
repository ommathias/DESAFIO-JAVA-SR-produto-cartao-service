package com.desafiotecnico.product_card_service.controller;

import com.desafiotecnico.product_card_service.builder.ProductBuilder;
import com.desafiotecnico.product_card_service.entity.Product;
import com.desafiotecnico.product_card_service.record.ProductRecordCreate;
import com.desafiotecnico.product_card_service.record.ProductResponseDTO;
import com.desafiotecnico.product_card_service.record.ProducRecordUpdate;
import com.desafiotecnico.product_card_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        List<Product> products = productService.getAllProducts();

        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<ProductResponseDTO> productsResponse = products.stream()
                .map(ProductBuilder::productToResponseDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(productsResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        ProductResponseDTO response = ProductBuilder.productToResponseDTO(product);


        return ResponseEntity.ok().body(response);
    }



    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductRecordCreate productRecord) {
        Product newProduct = productService.createProduct(productRecord);
        ProductResponseDTO response = new ProductResponseDTO(
                newProduct.getId(),
                newProduct.getName(),
                newProduct.getPrice(),
                newProduct.getCreationDate()
        );
        return ResponseEntity.status(201).body(response);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody ProducRecordUpdate producRecordUpdate) {
        Product updatedProduct = productService.updateProduct(id, producRecordUpdate);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}