package com.todo1.hulkstore.model.request;

import lombok.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

  private Long productId;

  @NotNull
  @NotEmpty
  private String description;

  @DecimalMin(value = "0")
  private BigDecimal amount;

  @DecimalMin(value = "0")
  private BigDecimal price;

  @NotNull
  @NotEmpty
  private String photo;

  private LocalDateTime creationDate;
}
