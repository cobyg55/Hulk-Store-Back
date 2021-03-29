package com.todo1.hulkstore.service.impl;

import com.todo1.hulkstore.exceptions.BusinessServiceException;
import com.todo1.hulkstore.model.Product;
import com.todo1.hulkstore.model.request.ProductRequest;
import com.todo1.hulkstore.model.request.BuyProductRequest;
import com.todo1.hulkstore.repository.ProductRepository;
import com.todo1.hulkstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Validated
public class ProductServiceImpl implements ProductService {

  private ProductRepository productRepository;

  @Autowired
  public ProductServiceImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public Product save(@Valid ProductRequest productRequest) {
    Product product = new Product(0L,
            productRequest.getDescription(),
            productRequest.getAmount(),
            productRequest.getPrice(),
            productRequest.getPhoto(),
            LocalDateTime.now());
    return this.productRepository.save(product);
  }

  @Override
  public synchronized void purchase(@Valid BuyProductRequest purchaseProductDTO) {
    Optional<Product> product = this.productRepository.findById(purchaseProductDTO.getProductId());
    if (product.isPresent()) {
      if (purchaseProductDTO.getAmount().compareTo(product.get().getAmount()) <= 0) {
        product.get().setAmount(product.get().getAmount().subtract(purchaseProductDTO.getAmount()));
        this.productRepository.save(product.get());
      } else {
        throw new BusinessServiceException(
            "Stock for product " + product.get().getDescription() + "not available yet");
      }
    } else {
      throw new BusinessServiceException("Stock for product " + product.get().getDescription() + "not available yet");
    }
  }

  @Override
  public void delete(Long productId) {
    this.productRepository.deleteById(productId);
  }

  @Override
  public synchronized Product update(@Valid ProductRequest productRequest) {
    Optional<Product> product = this.productRepository.findById(productRequest.getProductId());
    if (product.isPresent()) {
      product.get().setDescription(productRequest.getDescription());
      product.get().setAmount(productRequest.getAmount());
      product.get().setPrice(productRequest.getPrice());
      product.get().setPhoto(productRequest.getPhoto());
      return this.productRepository.save(product.get());
    } else {
      throw new BusinessServiceException("Product #" + productRequest.getProductId() + " not found :(");
    }
  }

  @Override
  public List<Product> getAll() {
    return this.productRepository.findAll();
  }

  @Override
  public Product getProductById(Long productId) {
    Optional<Product> product = this.productRepository.findById(productId);
    return product.orElse(null);
  }
}
