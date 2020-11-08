package com.bodegapp.demo.controller;

import com.bodegapp.demo.model.Product;
import com.bodegapp.demo.resource.ProductResource;
import com.bodegapp.demo.resource.SaveProductResource;
import com.bodegapp.demo.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name="products", description = "Product API")
@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public Page<ProductResource> getAllProducts(Pageable pageable) {
        Page<Product> customerPage = productService.getAllProducts(pageable);
        List<ProductResource> resources = customerPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());

        return new PageImpl<>(resources, pageable, resources.size());
    }

    @GetMapping("/products/{id}")
    public ProductResource getProductById(@PathVariable(name = "id") Long productId) {
        return convertToResource(productService.getProductById(productId));
    }

    @PostMapping("/products")
    public ProductResource createProduct(@Valid @RequestBody SaveProductResource resource)  {
        Product product = convertToEntity(resource);
        return convertToResource(productService.createProduct(product));
    }

    @PutMapping("/products/{id}")
    public ProductResource updateProduct(@PathVariable(name = "id") Long productId, @Valid @RequestBody SaveProductResource resource) {
        Product product = convertToEntity(resource);
        return convertToResource(productService.updateProduct(productId, product));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable(name = "id") Long productId) {
        return productService.deleteProduct(productId);
    }

    private Product convertToEntity(SaveProductResource resource) { return mapper.map(resource, Product.class); }

    private ProductResource convertToResource(Product entity) { return mapper.map(entity, ProductResource.class); }
}
