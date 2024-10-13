package com.jpsolanoc.transactions.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDTO extends PersonDTO{
    private String password;
    @NotNull(message = "El state es requerido.")
    private Boolean state;
}
