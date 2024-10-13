package com.jpsolanoc.clientcore.service.imp;

import com.jpsolanoc.clientcore.dto.CustomerDTO;
import com.jpsolanoc.clientcore.dto.ResponseGeneric;
import com.jpsolanoc.clientcore.entity.Customer;
import com.jpsolanoc.clientcore.repository.CustomerRepository;
import com.jpsolanoc.clientcore.service.CustomerCoreService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Validated
public class CustomerCoreServiceImp implements CustomerCoreService {
    ModelMapper modelMapper;
    private final CustomerRepository customerRepository;
    private static final String CLIENTE = "Cliente";

    @Autowired
    public CustomerCoreServiceImp(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public ResponseEntity<Object> crearteOrUpdateCustomer(CustomerDTO customerDTO)  {
        if (Objects.nonNull(customerDTO.getId())){
            Customer customerSearch = searchData(customerDTO.getId(), customerRepository, CLIENTE);
            BeanUtils.copyProperties(customerDTO, customerSearch, "id");
            customerRepository.save(customerSearch);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseGeneric(CLIENTE+" actualizado exitosamente."));
        }
        Customer customer = modelMapper.map(customerDTO, Customer.class);
        customerRepository.save(customer);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseGeneric(CLIENTE+" creado exitosamente."));
    }

    @Override
    public ResponseEntity<Object> deleteCustomer(Long id) {
        customerRepository.delete(searchData(id, customerRepository,CLIENTE));
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseGeneric(CLIENTE+" con "+id+ " eliminado del sistema."));
    }

    @Override
    public ResponseEntity<Object> getCustomer(Long id) {
        Customer cliente=searchData(id, customerRepository,CLIENTE);
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(cliente, CustomerDTO.class));
    }

    @Override
    public ResponseEntity<Object> getCustomers(List<Long> id) {
        List<Customer> clienteRepositoryAllById = customerRepository.findAllById(id);
        List<CustomerDTO> clienteDTOS = new ArrayList<>();
        clienteRepositoryAllById.forEach(cliente -> clienteDTOS.add(modelMapper.map(cliente, CustomerDTO.class)));
        return ResponseEntity.status(HttpStatus.OK).body(clienteDTOS);
    }

    private <T> T searchData(Long id, JpaRepository<T,Long> repository,String tipo){
        return repository.findById(id).orElseThrow(
                ()->new EntityNotFoundException(tipo+" no encontrado."));
    }

}
