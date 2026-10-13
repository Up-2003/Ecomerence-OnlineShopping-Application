package com.ecomerence.onlineshopping.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckoutForm {
    @NotBlank
    @Size(max = 500)
    private String shippingAddress;

    @NotBlank @Pattern(regexp = "^[0-9]{10,15}$")
    private String phone;

    @Size(max = 500)
    private String note;
}
