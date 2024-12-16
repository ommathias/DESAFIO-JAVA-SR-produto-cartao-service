package com.desafiotecnico.product_card_service.service;


import com.desafiotecnico.product_card_service.builder.ProductBuilder;
import com.desafiotecnico.product_card_service.entity.Product;
import com.desafiotecnico.product_card_service.exception.DatabaseException;
import com.desafiotecnico.product_card_service.exception.NotFoundException;
import com.desafiotecnico.product_card_service.record.ProductRecordCreate;
import com.desafiotecnico.product_card_service.record.ProducRecordUpdate;
import com.desafiotecnico.product_card_service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    //GETALL
    public List<Product> getAllProducts() {
        try {

            return productRepository.findAll();

        } catch (DataAccessException e) {
            throw new DatabaseException("Error fetching record from the database.");
        }
    }


    //GetByID
    public Product getProductById(Long id) {

            return productRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Record with id " + id + " not found."));
           }

    //POST
    public Product createProduct(ProductRecordCreate productRecord) {
        Product product = ProductBuilder.ProductRecordToProduct(productRecord);

        try {
            return productRepository.save(product);

        } catch (DataAccessException e) {
            throw new DatabaseException("Error persisting record to the database.");
        }
    }


    //DELETE
    public void deleteProduct(Long id) {

        Product productDeleting = getProductById(id);
        if (productDeleting == null) {
            throw new NotFoundException("Record with id " + id + " not found.");
        }

        try {productRepository.deleteById(id);
        } catch (DataAccessException e) {
            throw new DatabaseException("Error deleting record from the database.");
        }
    }


    //PUT
    public Product updateProduct(Long id, ProducRecordUpdate productDetails) {
        try {
            Product existingProduct = productRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Product with id " + id + " not found."));
            Product updatedProduct = ProductBuilder.updateProductRecordToProduct(productDetails, existingProduct);
            return productRepository.save(updatedProduct);
        } catch (DataAccessException e) {
            throw new DatabaseException("Error updating record to the database.");
        }
    }


}