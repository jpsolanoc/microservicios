package com.jpsolanoc.clientcore;

import com.jpsolanoc.clientcore.dto.CustomerDTO;

import java.util.concurrent.ThreadLocalRandom;

public class Resource {
    public static CustomerDTO clienteDTO(Long id){
        CustomerDTO clienteDTO = new CustomerDTO();
        clienteDTO.setId(id);
        clienteDTO.setName("John Solano");
        clienteDTO.setGender("Masculino");
        clienteDTO.setAge(37);
        clienteDTO.setDni(String.valueOf(ThreadLocalRandom.current().nextLong(1000000000L, 10000000000L)));
        clienteDTO.setAddress("Loja");
        clienteDTO.setPhone("+593979256768");
        clienteDTO.setPassword("1234");
        clienteDTO.setState(true);
        return clienteDTO;
    }
}
