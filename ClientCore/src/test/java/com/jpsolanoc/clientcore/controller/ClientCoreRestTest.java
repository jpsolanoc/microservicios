package com.jpsolanoc.clientcore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpsolanoc.clientcore.Resource;
import com.jpsolanoc.clientcore.dto.ClienteDTO;
import com.jpsolanoc.clientcore.entity.Cliente;
import com.jpsolanoc.clientcore.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class ClientCoreRestTest {

    @Autowired
    MockMvc mockMvc;

    private ObjectMapper objectMapper;
    @Autowired
    private ClienteRepository clienteRepository;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testCrearCuentaOk() throws Exception {
        ClienteDTO cuentaDTO = Resource.clienteDTO(null);
        mockMvc.perform(post("/core/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cuentaDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void testEditCuentaOk() throws Exception {
        testCrearCuentaOk();
        ClienteDTO cuentaDTO = Resource.clienteDTO(clienteRepository.findAll().get(0).getId());
        mockMvc.perform(put("/core/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cuentaDTO)))
                .andExpect(status().isOk());
    }
}
