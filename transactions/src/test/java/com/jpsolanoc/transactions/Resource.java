package com.jpsolanoc.transactions;

import com.jpsolanoc.transactions.dto.ClienteDTO;
import com.jpsolanoc.transactions.dto.CuentaDTO;
import com.jpsolanoc.transactions.dto.MovimientosDTOSearch;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Resource {
    public static CuentaDTO cuentaDTO(){
        CuentaDTO cuentaDTO = new CuentaDTO();
        cuentaDTO.setNumberCuenta("123456789");
        cuentaDTO.setType("Ahorros");
        cuentaDTO.setInitBalance(BigDecimal.valueOf(1000.00));
        cuentaDTO.setState(true);
        cuentaDTO.setIdCliente(1001);
        return cuentaDTO;
    }

    public static List<ClienteDTO> clienteDTOS(){
        List<ClienteDTO> clienteDTOS = new ArrayList<>();
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(1L);
        clienteDTO.setName("John Solano");
        clienteDTO.setClientId(1L);
        clienteDTO.setState(true);
        clienteDTO.setPassword("1234");
        clienteDTO.setAddress("Loja");
        clienteDTO.setGender("Masculino");
        clienteDTO.setPhone("+593979256768");
        clienteDTOS.add(new ClienteDTO());
        return clienteDTOS;
    }

    public static MovimientosDTOSearch movimientosDTOSearch(){
        MovimientosDTOSearch movimientosDTOSearch = new MovimientosDTOSearch();
        movimientosDTOSearch.setFechaInicio(new Date());
        movimientosDTOSearch.setFechaFin(new Date());
        movimientosDTOSearch.setClienteId(1L);
        return movimientosDTOSearch;
    }
}
