package com.microservicios.pruebatecnica.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequest {

    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;

    @Email(message = "El email debe ser válido")
    private String email;

    @Min(value = 18, message = "La edad debe ser mayor o igual a 18")
    private int age;

    @NotBlank(message = "La dirección no puede estar vacía")
    private String address;

    public UserRequest(String name, String email, int age, String address) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.address = address;
    }

    public UserRequest() {
    }
}
