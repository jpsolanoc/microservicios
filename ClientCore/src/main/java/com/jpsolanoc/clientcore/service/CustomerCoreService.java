package com.jpsolanoc.clientcore.service;

import com.jpsolanoc.clientcore.dto.CustomerDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CustomerCoreService {
    ResponseEntity<Object> crearteOrUpdateCustomer(CustomerDTO clienteDTO);
    ResponseEntity<Object> deleteCustomer(Long id);
    ResponseEntity<Object> getCustomer(Long id);
    ResponseEntity<Object> getCustomers(List<Long> id);
}
