package com.jpsolanoc.transactions.service;

import com.jpsolanoc.transactions.dto.CuentaDTO;
import com.jpsolanoc.transactions.dto.MovimientosDTO;
import com.jpsolanoc.transactions.dto.MovimientosDTOSearch;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface MovementGeneralService {
    ResponseEntity<Object> createOrUpdateCuenta(CuentaDTO cuentaDTO);
    ResponseEntity<Object> deleteCuenta(Long id);
    ResponseEntity<Object> getCuentaForId(Long id);

    ResponseEntity<Object> createOrUpdateMoviminetos(MovimientosDTO movimientosDTO);
    ResponseEntity<Object> deleteMovimientos(Long id);
    ResponseEntity<Object> getMovimientosForId(Long id);

    ResponseEntity<Object> reporteMovimientos(MovimientosDTOSearch movimientosDTOSearch, Pageable pageable);
}
