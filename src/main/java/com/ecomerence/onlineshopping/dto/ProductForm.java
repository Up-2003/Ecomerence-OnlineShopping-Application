package com.ecomerence.onlineshopping.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductForm {
    @NotBlank
    @Size(max=100)
    private String name;
    private String description;
    @NotNull
    @DecimalMin("0.01")
    private BigDecimal price;
    @NotNull @Min(0)
    private Integer stock;
    private String imageUrl;
    private Long categoryId;


}
