package com.jpsolanoc.clientcore;

import com.jpsolanoc.clientcore.dto.ClienteDTO;

public class Resource {
    public static ClienteDTO clienteDTO(Long id){
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(id);
        clienteDTO.setName("John Solano");
        clienteDTO.setGender("Masculino");
        clienteDTO.setAge(37);
        clienteDTO.setDni("123456789");
        clienteDTO.setAddress("Loja");
        clienteDTO.setPhone("+593979256768");
        clienteDTO.setPassword("1234");
        clienteDTO.setClientId(1L);
        clienteDTO.setState(true);
        return clienteDTO;
    }
}
