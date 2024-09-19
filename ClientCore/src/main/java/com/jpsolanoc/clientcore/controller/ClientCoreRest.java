package com.jpsolanoc.clientcore.controller;

import com.jpsolanoc.clientcore.dto.ClienteDTO;
import com.jpsolanoc.clientcore.service.ClientCoreService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/core")
@RestController
public class ClientCoreRest {

    private final ClientCoreService clientCoreService;

    public ClientCoreRest(ClientCoreService clientCoreService) {
        this.clientCoreService = clientCoreService;
    }

    @PostMapping("/cliente")
    public ResponseEntity<Object> createClient(@Valid @RequestBody ClienteDTO client, BindingResult result) {
        managerError(result);
        return clientCoreService.crearUpdateCliente(client);
    }

    @DeleteMapping("/cliente")
    public ResponseEntity<Object> deleteClient(@RequestHeader Long id) {
        Objects.requireNonNull(id);
        return clientCoreService.deleteCliente(id);
    }

    @PutMapping("/cliente")
    public ResponseEntity<Object> updateClient(@Valid @RequestBody ClienteDTO client, BindingResult result) {
        managerError(result);
        Objects.requireNonNull(client.getId(),"El id es requerido para actualizar");
        return clientCoreService.crearUpdateCliente(client);
    }

    @GetMapping("/cliente/{id}")
    public ResponseEntity<Object> getClient(@PathVariable Long id) {
        Objects.requireNonNull(id);
        return clientCoreService.getCliente(id);
    }

    @PostMapping("/cliente/listId")
    public ResponseEntity<Object> searchClientesIds(@RequestBody List<Long> idClientes) {
        Objects.requireNonNull(idClientes);
        return clientCoreService.getClientes(idClientes);
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
