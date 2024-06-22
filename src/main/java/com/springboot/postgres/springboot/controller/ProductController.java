package com.springboot.postgres.springboot.controller;

import com.springboot.postgres.springboot.dto.ProductRecordDto;
import com.springboot.postgres.springboot.models.ProductModel;
import com.springboot.postgres.springboot.repository.ProductRepository;
import com.springboot.postgres.springboot.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    public ResponseEntity<ProductModel> save(@RequestBody @Valid ProductRecordDto productRecordDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(productRecordDto));
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductModel>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductModel> findById(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.ok(productService.findIdOrThrowNotFoundStatusException(id));
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Void> replace(@PathVariable(value = "id") UUID id, @RequestBody @Valid ProductRecordDto productRecordDto) {
        productService.replace(id, productRecordDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") UUID id) {
        productService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
