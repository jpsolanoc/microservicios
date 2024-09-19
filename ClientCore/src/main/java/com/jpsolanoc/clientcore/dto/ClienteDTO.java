package com.jpsolanoc.clientcore.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteDTO extends PersonaDTO{
    private Long id;
    @NotNull(message = "El clientId es requerido.")
    private Long clientId;
    @NotNull(message = "El password es requerido.")
    private String password;
    @NotNull(message = "El state es requerido.")
    private Boolean state;
}
