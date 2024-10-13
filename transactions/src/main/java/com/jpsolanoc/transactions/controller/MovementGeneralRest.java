package com.jpsolanoc.transactions.controller;

import com.jpsolanoc.transactions.dto.AccountDTO;
import com.jpsolanoc.transactions.dto.MovementDTO;
import com.jpsolanoc.transactions.dto.MovementDTOSearch;
import com.jpsolanoc.transactions.service.MovementGeneralService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.Objects;

@Validated
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/core")
@RestController
public class MovementGeneralRest {

    private final MovementGeneralService movementGeneralService;

    @Autowired
    public MovementGeneralRest(MovementGeneralService movementGeneralService) {
        this.movementGeneralService = movementGeneralService;
    }

    @PostMapping("/cuentas")
    public ResponseEntity<Object> createCuenta(@Valid @RequestBody AccountDTO cuentaDTO) {
        return movementGeneralService.createOrUpdateAccount(cuentaDTO);
    }

    @DeleteMapping("/cuentas")
    public ResponseEntity<Object> deleteCuenta(@RequestHeader Long id) {
        Objects.requireNonNull(id);
        return movementGeneralService.deleteAccount(id);
    }

    @PutMapping("/cuentas")
    public ResponseEntity<Object> updateCuenta(@Valid @RequestBody AccountDTO cuentaDTO) {
        Objects.requireNonNull(cuentaDTO.getId());
        return movementGeneralService.createOrUpdateAccount(cuentaDTO);
    }

    @GetMapping("/cuentas/{id}")
    public ResponseEntity<Object> getCuentaForId(@PathVariable Long id) {
        Objects.requireNonNull(id);
        return movementGeneralService.getAccountForId(id);
    }
    @PostMapping("/movimientos")
    public ResponseEntity<Object> createMovimientos(@Valid @RequestBody MovementDTO movimientosDTO) {
        return movementGeneralService.createOrUpdateMovement(movimientosDTO);
    }

    @DeleteMapping("/movimientos")
    public ResponseEntity<Object> deleteMovimientos(@RequestHeader Long id) {
        Objects.requireNonNull(id);
        return movementGeneralService.deleteMovement(id);
    }

    @PutMapping("/movimientos")
    public ResponseEntity<Object> updateMoviminetos(@Valid @RequestBody MovementDTO movimientosDTO) {
        Objects.requireNonNull(movimientosDTO.getId());
        return movementGeneralService.createOrUpdateMovement(movimientosDTO);
    }

    @GetMapping("/movimientos/{id}")
    public ResponseEntity<Object> getMovimientosForId(@PathVariable Long id) {
        Objects.requireNonNull(id);
        return movementGeneralService.getMovementForId(id);
    }

    @PostMapping("/movimientos/report")
    public ResponseEntity<Object> searchMovement(@Valid @RequestBody MovementDTOSearch movimientosDTO, Pageable pageable) {
        return movementGeneralService.reporteMovement(movimientosDTO,pageable);
    }
}
