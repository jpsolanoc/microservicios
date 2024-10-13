package com.jpsolanoc.clientcore.controller;

import com.jpsolanoc.clientcore.dto.CustomerDTO;
import com.jpsolanoc.clientcore.service.CustomerCoreService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
@Validated
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/core")
@RestController
public class ClientCoreRest {

    private final CustomerCoreService clientCoreService;

    public ClientCoreRest(CustomerCoreService clientCoreService) {
        this.clientCoreService = clientCoreService;
    }

    @PostMapping("/clientes")
    public ResponseEntity<Object> createClient(@Valid @RequestBody CustomerDTO client) {
        return clientCoreService.crearteOrUpdateCustomer(client);
    }

    @DeleteMapping("/clientes")
    public ResponseEntity<Object> deleteClient(@RequestHeader Long id) {
        Objects.requireNonNull(id);
        return clientCoreService.deleteCustomer(id);
    }

    @PutMapping("/clientes")
    public ResponseEntity<Object> updateClient(@Valid @RequestBody CustomerDTO client) {
        Objects.requireNonNull(client.getId(),"El id es requerido para actualizar");
        return clientCoreService.crearteOrUpdateCustomer(client);
    }

    @GetMapping("/clientes/{id}")
    public ResponseEntity<Object> getClient(@PathVariable Long id) {
        Objects.requireNonNull(id);
        return clientCoreService.getCustomer(id);
    }

    @PostMapping("/clientes/listId")
    public ResponseEntity<Object> searchClientesIds(@RequestBody List<Long> idClientes) {
        Objects.requireNonNull(idClientes);
        return clientCoreService.getCustomers(idClientes);
    }
}
