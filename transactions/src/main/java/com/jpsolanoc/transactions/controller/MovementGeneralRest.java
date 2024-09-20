package com.jpsolanoc.transactions.controller;

import com.jpsolanoc.transactions.dto.CuentaDTO;
import com.jpsolanoc.transactions.dto.MovimientosDTO;
import com.jpsolanoc.transactions.dto.MovimientosDTOSearch;
import com.jpsolanoc.transactions.service.MovementGeneralService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/core")
@RestController
public class MovementGeneralRest {

    private final MovementGeneralService movementGeneralService;

    @Autowired
    public MovementGeneralRest(MovementGeneralService movementGeneralService) {
        this.movementGeneralService = movementGeneralService;
    }

    @PostMapping("/cuenta")
    public ResponseEntity<Object> createCuenta(@Valid @RequestBody CuentaDTO cuentaDTO, BindingResult result) {
        managerError(result);
        return movementGeneralService.createOrUpdateCuenta(cuentaDTO);
    }

    @DeleteMapping("/cuenta")
    public ResponseEntity<Object> deleteCuenta(@RequestHeader Long id) {
        Objects.requireNonNull(id);
        return movementGeneralService.deleteCuenta(id);
    }

    @PutMapping("/cuenta")
    public ResponseEntity<Object> updateCuenta(@Valid @RequestBody CuentaDTO cuentaDTO, BindingResult result) {
        managerError(result);
        Objects.requireNonNull(cuentaDTO.getId());
        return movementGeneralService.createOrUpdateCuenta(cuentaDTO);
    }

    @GetMapping("/cuenta/{id}")
    public ResponseEntity<Object> getCuentaForId(@PathVariable Long id) {
        Objects.requireNonNull(id);
        return movementGeneralService.getCuentaForId(id);
    }
    @PostMapping("/movement")
    public ResponseEntity<Object> createMovimientos(@Valid @RequestBody MovimientosDTO movimientosDTO,
                                                    BindingResult result) {
        managerError(result);
        return movementGeneralService.createOrUpdateMoviminetos(movimientosDTO);
    }

    @DeleteMapping("/movement")
    public ResponseEntity<Object> deleteMovimientos(@RequestHeader Long id) {
        Objects.requireNonNull(id);
        return movementGeneralService.deleteMovimientos(id);
    }

    @PutMapping("/movement")
    public ResponseEntity<Object> updateMoviminetos(@Valid @RequestBody MovimientosDTO movimientosDTO, BindingResult result) {
        managerError(result);
        Objects.requireNonNull(movimientosDTO.getId());
        return movementGeneralService.createOrUpdateMoviminetos(movimientosDTO);
    }

    @GetMapping("/movement/{id}")
    public ResponseEntity<Object> getMovimientosForId(@PathVariable Long id) {
        Objects.requireNonNull(id);
        return movementGeneralService.getMovimientosForId(id);
    }

    @PostMapping("/movement/report")
    public ResponseEntity<Object> searchMovement(@Valid @RequestBody MovimientosDTOSearch movimientosDTO, Pageable pageable, BindingResult result) {
        managerError(result);
        return movementGeneralService.reporteMovimientos(movimientosDTO,pageable);
    }

    public void managerError(BindingResult result){
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors().stream()
                    .map(error -> "Campo: " + error.getField() + " - " + error.getDefaultMessage())
                    .toList();
            ResponseEntity.badRequest().body("Errores en los parámetros: " + String.join(", ", errorMessages));
        }
    }

}
