package com.jpsolanoc.clientcore.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDTO extends PersonDTO {
    @NotNull(message = "El password es requerido.")
    private String password;
    @NotNull(message = "El state es requerido.")
    private Boolean state;
}
