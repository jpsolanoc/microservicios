package com.jpsolanoc.transactions.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CuentaDTO {

    private Long id;
    @NotNull(message = "El numberCuenta es requerido.")
    private String numberCuenta;
    @NotNull(message = "El type es requerido.")
    private String type;
    @NotNull(message = "El initBalance es requerido.")
    private BigDecimal initBalance;
    @NotNull(message = "El state es requerido.")
    private Boolean state;
    @NotNull(message = "El idCliente es requerido.")
    private Integer idCliente;
}
