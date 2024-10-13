package com.jpsolanoc.transactions;

import com.jpsolanoc.transactions.dto.AccountDTO;
import com.jpsolanoc.transactions.dto.CustomerDTO;
import com.jpsolanoc.transactions.dto.MovementDTOSearch;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Resource {
    public static AccountDTO cuentaDTO(){
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setNumberAccount(String.valueOf(ThreadLocalRandom.current().nextLong(1000000000L, 10000000000L)));
        accountDTO.setType("Ahorros");
        accountDTO.setInitBalance(BigDecimal.valueOf(1000.00));
        accountDTO.setState(true);
        accountDTO.setClientId(1001);
        return accountDTO;
    }

    public static List<CustomerDTO> clienteDTOS(){
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(1L);
        customerDTO.setName("John Solano");
        customerDTO.setState(true);
        customerDTO.setPassword("1234");
        customerDTO.setAddress("Loja");
        customerDTO.setGender("Masculino");
        customerDTO.setPhone("+593979256768");
        customerDTOS.add(new CustomerDTO());
        return customerDTOS;
    }

    public static MovementDTOSearch movimientosDTOSearch(){
        MovementDTOSearch movementDTOSearch = new MovementDTOSearch();
        movementDTOSearch.setDateInit(new Date());
        movementDTOSearch.setDateEnd(new Date());
        movementDTOSearch.setClientId(1L);
        return movementDTOSearch;
    }
}
