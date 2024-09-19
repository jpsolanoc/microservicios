package com.jpsolanoc.transactions.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MovimientosDTOSearch {
    @NotNull(message = "La fecha de inicio es requerido.")
    private Date fechaInicio;
    @NotNull(message = "La fecha de fin es requerido.")
    private Date fechaFin;
    @NotNull(message = "El id del cliente es requerido.")
    private Long clienteId;
}
