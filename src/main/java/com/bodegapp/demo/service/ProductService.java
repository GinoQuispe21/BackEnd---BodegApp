package com.bodegapp.demo.service;

import com.bodegapp.demo.model.CartLine;
import com.bodegapp.demo.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    ResponseEntity<?> deleteProduct(Long productId);
    Product updateProduct(Long productId, Product productRequest);
    Product createProduct(Product product);
    Product getProductById(Long productId);
    Page<Product> getAllProducts(Pageable pageable);
    List<CartLine> getAllProductsByOrderId(Long orderId);
}
