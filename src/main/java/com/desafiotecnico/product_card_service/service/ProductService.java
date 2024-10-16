package com.desafiotecnico.product_card_service.service;


import com.desafiotecnico.product_card_service.builder.ProductBuilder;
import com.desafiotecnico.product_card_service.entity.Product;
import com.desafiotecnico.product_card_service.exception.DatabaseException;
import com.desafiotecnico.product_card_service.exception.NoContentException;
import com.desafiotecnico.product_card_service.exception.NotFoundException;
import com.desafiotecnico.product_card_service.record.CreateProductRecord;
import com.desafiotecnico.product_card_service.record.UpdateProductRecord;
import com.desafiotecnico.product_card_service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        try {
            List<Product> products = productRepository.findAll();

            if (products.isEmpty()) {
                throw new NoContentException("No products found");
            }

            return products;

        } catch (DataAccessException e) {
            // Lança uma exceção  erros de acesso ao banco de dados
            throw new DatabaseException("Error fetching products from the database.");
        }
    }

    public Product getProductById(Long id) {


        return productRepository.findById(id).orElse(null);
    }

    public Product createProduct(CreateProductRecord productRecord) {
        Product product = ProductBuilder.ProductRecordToProduct(productRecord);

        return productRepository.save(product);
    }



    public void deleteProduct(Long id) {

        Product productDeleting =  getProductById(id);
        if(productDeleting == null) {
            System.out.println("Product not found");
            return;
        }

        productRepository.deleteById(id);
    }

    public Product updateProduct(Long id, UpdateProductRecord productDetails) {
        Product existingProduct = productRepository.findById(id).orElse(null);

        if (existingProduct == null) {
            return null;
        }

        Product updatedProduct = ProductBuilder.updateProductRecordToProduct(productDetails, existingProduct);

        return productRepository.save(updatedProduct);
    }
}