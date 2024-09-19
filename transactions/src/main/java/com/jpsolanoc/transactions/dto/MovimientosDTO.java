package com.jpsolanoc.transactions.dto;

import com.jpsolanoc.transactions.enumdata.TipoMovimiento;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MovimientosDTO {
    private Long id;
    @NotNull(message = "El tipo de movimiento es requerido.")
    private TipoMovimiento type;
    @NotNull(message = "El valor es requerido.")
    private BigDecimal value;
    @NotNull(message = "El balance es requerido.")
    private BigDecimal balance;
    @NotNull(message = "La descriptión es requerido.")
    private String description;
    @NotNull(message = "El id de la cuenta es requerido.")
    private Long cuenta;
}
