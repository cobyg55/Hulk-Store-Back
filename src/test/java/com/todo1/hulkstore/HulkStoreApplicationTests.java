package com.todo1.hulkstore;

import com.todo1.hulkstore.model.Product;
import com.todo1.hulkstore.model.request.ProductRequest;
import com.todo1.hulkstore.model.request.BuyProductRequest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class HulkStoreApplicationTests {

  @Autowired TestRestTemplate testRestTemplate;

  private String url = "/api/products";

  @Test
  public void getAllProductTest() {
    ProductRequest productRequest1 =
        new ProductRequest(0L, "Vaso Hulk", new BigDecimal("1"), new BigDecimal("150.50"), "http://www.google.com/photo",null);
    ProductRequest productRequest2 =
        new ProductRequest(0L, "Vaso Ironman", new BigDecimal("2"), new BigDecimal("300.50"), "http://www.google.com/photo", null);
    this.testRestTemplate.postForObject(url, productRequest1, Product.class);
    this.testRestTemplate.postForObject(url, productRequest2, Product.class);
    Product[] products = this.testRestTemplate.getForObject("/api/products", Product[].class);
    Assert.assertEquals(2, products.length);
  }

  @Test
  public void updateProductTest() {
    ProductRequest productRequest1 =
        new ProductRequest(
            0L, "Best Spiderman", new BigDecimal("1"), new BigDecimal("150.50"), "http://www.google.com/photo", null);
    Product product = this.testRestTemplate.postForObject(url, productRequest1, Product.class);
    Assert.assertNotNull(product);
    product.setDescription("Best Spiderman and Venom");
    product.setAmount(new BigDecimal("2.00"));
    product.setPrice(new BigDecimal("300.00"));
    this.testRestTemplate.put(url, product);
    product = this.testRestTemplate.getForObject(url + "/1", Product.class);
    Assert.assertNotNull(product);
    Assert.assertEquals("The amazing Spiderman and Venom", product.getDescription());
    Assert.assertEquals(new BigDecimal("2.00"), product.getAmount());
    Assert.assertEquals(new BigDecimal("300.00"), product.getPrice());
  }

  @Test
  public void deleteProductTest() {
    ProductRequest productRequest1 =
        new ProductRequest(
            0L, "The amazing Spiderman", new BigDecimal("1"), new BigDecimal("150.50"),"http://www.google.com/photo", null);
    Product product = this.testRestTemplate.postForObject(url, productRequest1, Product.class);
    Assert.assertNotNull(product);
    this.testRestTemplate.delete(url + "/1");
    product = this.testRestTemplate.getForObject(url + "/1", Product.class);
    Assert.assertNull(product);
  }

  @Test
  public void getProductByIdTest() {
    ProductRequest productRequest1 =
        new ProductRequest(
            0L, "The amazing Spiderman", new BigDecimal("1"), new BigDecimal("150.50"), "http://www.google.com/photo", null);
    Product product = this.testRestTemplate.postForObject(url, productRequest1, Product.class);
    Assert.assertEquals(productRequest1.getDescription(), product.getDescription());
  }

  @Test
  public void reduceAmountAfterPurchaseTest() {
    ProductRequest productRequest1 =
        new ProductRequest(0L, "Joker", new BigDecimal("10"), new BigDecimal("850.50"),"http://www.google.com/photo", null);
    Product product = this.testRestTemplate.postForObject(url, productRequest1, Product.class);
    Assert.assertNotNull(product);
    BuyProductRequest purchaseProductDTO = new BuyProductRequest(1L, new BigDecimal("10"));
    this.testRestTemplate.postForObject(url + "/purchase", purchaseProductDTO, Void.class);
    product = this.testRestTemplate.getForObject(url + "/1", Product.class);
    Assert.assertEquals(new BigDecimal("0.00"), product.getAmount());
  }
}
