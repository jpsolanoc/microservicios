package com.jpsolanoc.transactions.dto;

import com.jpsolanoc.transactions.enumdata.MovementType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MovementDTO {
    private Long id;
    @NotNull(message = "El tipo de movimiento es requerido.")
    private MovementType type;
    @NotNull(message = "El valor es requerido.")
    private BigDecimal value;
    private BigDecimal balance;
    @NotNull(message = "La descriptión es requerido.")
    private String description;
    @NotNull(message = "El id de la cuenta es requerido.")
    private Long account;
}
