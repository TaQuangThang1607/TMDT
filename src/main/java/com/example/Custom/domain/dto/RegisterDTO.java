package com.example.Custom.domain.dto;

import com.example.Custom.service.Validator.RegisterChecked;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@RegisterChecked
@Data
public class RegisterDTO {
    @Size(min = 3, message = "First Name tối thiểu 3 kí tự")
    private String firstName;

    private String lastName;
    @Email(message = "Email không hợp lệ", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?``{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    private String password;
    @Size(min = 3, message = "Password tối thiểu 3 kí tự")
    private String confirmPassword;
}
