package com.jpsolanoc.clientcore.service;

import com.jpsolanoc.clientcore.dto.ClienteDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ClientCoreService {
    ResponseEntity<Object> crearUpdateCliente(ClienteDTO clienteDTO);
    ResponseEntity<Object> deleteCliente(Long id);
    ResponseEntity<Object> getCliente(Long id);
    ResponseEntity<Object> getClientes(List<Long> id);
}
