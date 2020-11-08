package com.bodegapp.demo.service;

import com.bodegapp.demo.exception.ResourceNotFoundException;
import com.bodegapp.demo.model.Product;
import com.bodegapp.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ResponseEntity<?> deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "Id", productId));
        productRepository.delete(product);
        return ResponseEntity.ok().build();
    }

    @Override
    public Product updateProduct(Long productId, Product productRequest) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "Id", productId));
        product.setProductName(productRequest.getProductName());
        product.setProviderName(productRequest.getProviderName());
        product.setSalePrice(productRequest.getSalePrice());
        product.setPurchasePrice(productRequest.getPurchasePrice());
        return productRepository.save(product);
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "Id", productId));
    }

    @Override
    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

}
