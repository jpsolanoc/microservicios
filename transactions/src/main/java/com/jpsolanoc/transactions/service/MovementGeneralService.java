package com.jpsolanoc.transactions.service;

import com.jpsolanoc.transactions.dto.AccountDTO;
import com.jpsolanoc.transactions.dto.MovementDTO;
import com.jpsolanoc.transactions.dto.MovementDTOSearch;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface MovementGeneralService {
    ResponseEntity<Object> createOrUpdateAccount(AccountDTO accountDTO);
    ResponseEntity<Object> deleteAccount(Long id);
    ResponseEntity<Object> getAccountForId(Long id);

    ResponseEntity<Object> createOrUpdateMovement(MovementDTO movementDTO);
    ResponseEntity<Object> deleteMovement(Long id);
    ResponseEntity<Object> getMovementForId(Long id);

    ResponseEntity<Object> reporteMovement(MovementDTOSearch movementDTOSearch, Pageable pageable);
}
