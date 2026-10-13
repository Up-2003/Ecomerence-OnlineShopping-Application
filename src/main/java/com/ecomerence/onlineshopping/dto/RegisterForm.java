package com.ecomerence.onlineshopping.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterForm {
    @NotBlank
    @Size(min = 3, max = 50)
    private String username;

    @NotBlank @Email
    private String email;

    @NotBlank @Size(min = 6, max = 100)
    private String password;

    @NotBlank @Size(min = 6)
    private String confirmPassword;

    @Size(max = 100)
    private String fullName;

    public boolean passwordsMatch() {
        return password != null && password.equals(confirmPassword);
    }


}
