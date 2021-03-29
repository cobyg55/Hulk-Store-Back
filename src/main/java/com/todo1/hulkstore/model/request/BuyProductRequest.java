package com.todo1.hulkstore.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyProductRequest {

  @NotNull
  private Long productId;

  @NotNull
  @Positive
  private BigDecimal amount;
}
