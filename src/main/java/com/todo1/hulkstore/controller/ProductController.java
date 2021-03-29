package com.todo1.hulkstore.controller;

import com.todo1.hulkstore.model.Product;
import com.todo1.hulkstore.model.request.ProductRequest;
import com.todo1.hulkstore.model.request.BuyProductRequest;
import com.todo1.hulkstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestControllerAdvice
@RequestMapping("/api")
public class ProductController {

  @Autowired
  private ProductService productService;

  @Autowired
  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping("/products/{productId}")
  public Product getProductById(@PathVariable Long productId) {
    return this.productService.getProductById(productId);
  }

  @GetMapping("/products")
  public List<Product> getAll() {
    return this.productService.getAll();
  }

  @PostMapping("/products")
  public Product addNew(@RequestBody ProductRequest productRequest) {
    return productService.save(productRequest);
  }

  @PostMapping("/products/purchase")
  public void purchase(@RequestBody BuyProductRequest purchaseProductDTO) {
    this.productService.purchase(purchaseProductDTO);
  }

  @DeleteMapping("/products/{productId}")
  public void deleteOne(@PathVariable Long productId) {
    this.productService.delete(productId);
  }

  @PutMapping("/products")
  public Product updateProduct(@RequestBody ProductRequest productRequest) {
    return this.productService.update(productRequest);
  }
}
