package com.ecomerence.onlineshopping.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryForm {
    @NotBlank(message = "Category name is required")
    @Size(min = 2, max = 100, message = "Name must be 2–100 characters")
    private String name;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @Size(max = 500, message = "Image URL too long")
    private String imageUrl;

    private boolean active = true;
}
