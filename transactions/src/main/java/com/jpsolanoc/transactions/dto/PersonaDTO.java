package com.jpsolanoc.transactions.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PersonaDTO {
    private Long id;
    @NotNull(message = "El nombre es requerido.")
    private String name;
    @NotNull(message = "El genero es requerido.")
    private String gender;
    @NotNull(message = "la edad es requerido.")
    private Integer age;
    @NotNull(message = "El documento de identificación es requerido.")
    private String dni;
    @NotNull(message = "La dirección es requerido.")
    private String address;
    @NotNull(message = "El teléfono es requerido.")
    private String phone;
}
