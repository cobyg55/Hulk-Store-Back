package com.todo1.hulkstore.service;

import com.todo1.hulkstore.model.Product;
import com.todo1.hulkstore.model.request.ProductRequest;
import com.todo1.hulkstore.model.request.BuyProductRequest;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

public interface ProductService {

  Product save(@Valid ProductRequest productRequest);

  void purchase(@Valid BuyProductRequest purchaseProductDTO);

  void delete(Long productId);

  Product update(@Valid ProductRequest productRequest);

  List<Product> getAll();

  Product getProductById(Long productId);
}
