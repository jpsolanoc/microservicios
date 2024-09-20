package com.jpsolanoc.transactions.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpsolanoc.transactions.Resource;
import com.jpsolanoc.transactions.app.MicroServerClientCore;
import com.jpsolanoc.transactions.dto.CuentaDTO;
import com.jpsolanoc.transactions.dto.MovimientosDTOSearch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class MovementGeneralRestTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private MicroServerClientCore microServerClientCore;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testCrearCuentaOk() throws Exception {
        CuentaDTO cuentaDTO = Resource.cuentaDTO();
        mockMvc.perform(post("/core/cuenta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cuentaDTO)))
                .andExpect(status().isOk());
    }

    //Esto es una prueba de integración ya que estamos simulando la respuesta del microservicio de cliente.
    @Test
    void testMovimientosIntegrationClientesOk() throws Exception {
        when(microServerClientCore.sendPostCliente(Mockito.anyList())).thenReturn(Resource.clienteDTOS());
        MovimientosDTOSearch movimientosDTOSearch = Resource.movimientosDTOSearch();
        mockMvc.perform(post("/core/movement/report")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movimientosDTOSearch)))
                .andExpect(status().isOk());
    }

}
