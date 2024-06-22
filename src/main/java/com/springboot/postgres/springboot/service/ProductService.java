package com.springboot.postgres.springboot.service;

import com.springboot.postgres.springboot.dto.ProductRecordDto;
import com.springboot.postgres.springboot.models.ProductModel;
import com.springboot.postgres.springboot.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public List<ProductModel> findAll() {
        return productRepository.findAll();
    }
    public ProductModel save(@RequestBody @Valid ProductRecordDto productRecordDto) {
        var product = new ProductModel();
        BeanUtils.copyProperties(productRecordDto, product);
        return productRepository.save(product);
    }

    public ProductModel findIdOrThrowNotFoundStatusException(UUID id) {
        return productRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }
    public void replace(UUID id, ProductRecordDto productRecordDto) {
        ProductModel productToReplace = findIdOrThrowNotFoundStatusException(id);
        BeanUtils.copyProperties(productRecordDto, productToReplace);
        productRepository.save(productToReplace);
    }
    public void delete(UUID id) {
        ProductModel productToDelete = findIdOrThrowNotFoundStatusException(id);
        productRepository.delete(productToDelete);
    }
}
