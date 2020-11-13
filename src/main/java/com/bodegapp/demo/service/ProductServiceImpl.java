package com.bodegapp.demo.service;

import com.bodegapp.demo.exception.ResourceNotFoundException;
import com.bodegapp.demo.model.CartLine;
import com.bodegapp.demo.model.OrderDetail;
import com.bodegapp.demo.model.Product;
import com.bodegapp.demo.repository.OrderDetailRepository;
import com.bodegapp.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

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

    @Override
    public List<CartLine> getAllProductsByOrderId(Long orderId) {
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);
        List<CartLine> cartLine = new ArrayList<>();

        for (OrderDetail order: orderDetails) {
            CartLine info = new CartLine();
            Product product = order.getProduct();
            info.setProductName(product.getProductName());
            info.setProviderName(product.getProviderName());
            long e = order.getId();
            info.setId(e);
            double price = product.getSalePrice();
            info.setSalePrice(price);
            int i = order.getQuantity();
            info.setQuantity(i);
            cartLine.add(info);
        }

        return cartLine;
    }

}
