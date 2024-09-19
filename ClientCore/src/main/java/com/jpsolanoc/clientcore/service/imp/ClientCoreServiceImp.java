package com.jpsolanoc.clientcore.service.imp;

import com.jpsolanoc.clientcore.dto.ClienteDTO;
import com.jpsolanoc.clientcore.dto.ResponseGeneric;
import com.jpsolanoc.clientcore.entity.Cliente;
import com.jpsolanoc.clientcore.repository.ClienteRepository;
import com.jpsolanoc.clientcore.service.ClientCoreService;
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
public class ClientCoreServiceImp implements ClientCoreService {
    ModelMapper modelMapper;
    private final ClienteRepository clienteRepository;
    private static final String CLIENTE = "Cliente";
    @Autowired
    public ClientCoreServiceImp(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
        this.modelMapper = new ModelMapper();
    }
    @Override
    public ResponseEntity<Object> crearUpdateCliente(ClienteDTO clienteDTO)  {
        if (Objects.nonNull(clienteDTO.getId())){
            Cliente clienteSearch = searchData(clienteDTO.getId(), clienteRepository, CLIENTE);
            BeanUtils.copyProperties(clienteDTO, clienteSearch, "id");
            clienteRepository.save(clienteSearch);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseGeneric("Cliente actualizado exitosamente."));
        }
        Cliente cliente = modelMapper.map(clienteDTO, Cliente.class);
        clienteRepository.save(cliente);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseGeneric("Cliente creado exitosamente."));

    }
    @Override
    public ResponseEntity<Object> deleteCliente(Long id) {
        clienteRepository.delete(searchData(id,clienteRepository,CLIENTE));
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseGeneric("Cliente con "+id+ " eliminado del sistema."));
    }
    @Override
    public ResponseEntity<Object> getCliente(Long id) {
        Cliente cliente=searchData(id,clienteRepository,CLIENTE);
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(cliente, ClienteDTO.class));
    }
    private <T> T searchData(Long id, JpaRepository<T,Long> repository,String tipo){
        return repository.findById(id).orElseThrow(
                ()->new EntityNotFoundException(tipo+" no encontrado."));
    }

    @Override
    public ResponseEntity<Object> getClientes(List<Long> id) {
        List<Cliente> clienteRepositoryAllById = clienteRepository.findAllById(id);
        List<ClienteDTO> clienteDTOS = new ArrayList<>();
        clienteRepositoryAllById.forEach(cliente -> clienteDTOS.add(modelMapper.map(cliente, ClienteDTO.class)));
        return ResponseEntity.status(HttpStatus.OK).body(clienteDTOS);
    }
}
