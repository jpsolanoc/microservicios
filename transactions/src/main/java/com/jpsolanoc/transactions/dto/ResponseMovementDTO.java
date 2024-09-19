package com.jpsolanoc.transactions.dto;

import com.jpsolanoc.transactions.entity.Movimientos;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ResponseMovementDTO {
    private String fecha;
    private String cliente;
    private String numeroCuenta;
    private String tipo;
    private BigDecimal saldoInicial;
    private Boolean estado;
    private BigDecimal movimiento;
    private BigDecimal saldoDisponible;

    public ResponseMovementDTO(Movimientos movimientos) {
        this.fecha = movimientos.getCreateAt().toString();
        this.cliente = movimientos.getCuenta().getIdCliente().toString();
        this.numeroCuenta = movimientos.getCuenta().getNumberCuenta();
        this.tipo = movimientos.getType().getDescripcion();
        this.saldoInicial = movimientos.getBalance().add(movimientos.getValue().negate());
        this.estado = movimientos.getCuenta().getState();
        this.movimiento = movimientos.getValue();
        this.saldoDisponible =movimientos.getBalance();
    }
}
