package com.ecomerence.onlineshopping.model.service;

import com.ecomerence.onlineshopping.dto.ProductForm;
import com.ecomerence.onlineshopping.model.entity.Product;
import com.ecomerence.onlineshopping.model.repository.CategoryRepository;
import com.ecomerence.onlineshopping.model.repository.ProductRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

    @Service
    @Transactional(readOnly = true)
    public class ProductService {

        private final ProductRepository productRepo;
        private final CategoryRepository categoryRepo;

        public ProductService(ProductRepository p, CategoryRepository c) {
            this.productRepo = p;
            this.categoryRepo = c;
        }

        public Page<Product> findAll(Pageable pageable) {
            return productRepo.findByActiveTrue(pageable);
        }

        public Page<Product> search(String q, Pageable pageable) {
            return productRepo.search(q, pageable);
        }

        public Page<Product> filter(Long categoryId, BigDecimal minPrice,
                                    BigDecimal maxPrice, Pageable pageable) {
            return productRepo.filter(categoryId, minPrice, maxPrice, pageable);
        }

        public Page<Product> findByCategory(Long categoryId, Pageable pageable) {
            return productRepo.findByCategoryIdAndActiveTrue(categoryId, pageable);
        }

        public Product findById(Long id) {
            return productRepo.findByIdAndActiveTrue(id)
                    .orElseThrow(() -> new RuntimeException("Product not found"));
        }

        @Transactional
        public Product create(ProductForm form) {
            Product p = new Product();
            applyForm(p, form);
            return productRepo.save(p);
        }

        @Transactional
        public Product update(Long id, ProductForm form) {
            Product p = productRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            applyForm(p, form);
            return productRepo.save(p);
        }

        @Transactional
        public void delete(Long id) {
            Product p = productRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            p.setActive(false);
            productRepo.save(p);
        }

        private void applyForm(Product p, ProductForm form) {
            p.setName(form.getName());
            p.setDescription(form.getDescription());
            p.setPrice(form.getPrice());
            p.setStock(form.getStock());
            p.setImageUrl(form.getImageUrl());

            if (form.getCategoryId() != null) {
                p.setCategory(categoryRepo.findById(form.getCategoryId()).orElse(null));
            }
        }
    }
