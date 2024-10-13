package com.jpsolanoc.clientcore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpsolanoc.clientcore.Resource;
import com.jpsolanoc.clientcore.dto.CustomerDTO;
import com.jpsolanoc.clientcore.repository.CustomerRepository;
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
    private CustomerRepository clienteRepository;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testCrearClientOk() throws Exception {
        CustomerDTO cuentaDTO = Resource.clienteDTO(null);
        mockMvc.perform(post("/core/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cuentaDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void testEditClientOk() throws Exception {
        testCrearClientOk();
        CustomerDTO cuentaDTO = Resource.clienteDTO(clienteRepository.findAll().get(0).getId());
        mockMvc.perform(put("/core/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cuentaDTO)))
                .andExpect(status().isOk());
    }
}
