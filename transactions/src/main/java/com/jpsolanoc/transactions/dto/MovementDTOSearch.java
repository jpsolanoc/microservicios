package com.jpsolanoc.transactions.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MovementDTOSearch {
    @NotNull(message = "La fecha de inicio es requerido.")
    private Date dateInit;
    @NotNull(message = "La fecha de fin es requerido.")
    private Date dateEnd;
    @NotNull(message = "El id del cliente es requerido.")
    private Long clientId;
}
