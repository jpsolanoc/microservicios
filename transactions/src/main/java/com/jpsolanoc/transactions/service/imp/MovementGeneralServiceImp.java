package com.jpsolanoc.transactions.service.imp;

import com.jpsolanoc.transactions.app.MicroServerClientCore;
import com.jpsolanoc.transactions.dto.*;
import com.jpsolanoc.transactions.entity.Account;
import com.jpsolanoc.transactions.entity.Movements;
import com.jpsolanoc.transactions.exceptions.SaldoInsuficienteException;
import com.jpsolanoc.transactions.repository.CuentaRepository;
import com.jpsolanoc.transactions.repository.MovimientosRepository;
import com.jpsolanoc.transactions.repository.search.ReporteMovimientosSearch;
import com.jpsolanoc.transactions.service.MovementGeneralService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@Validated
public class MovementGeneralServiceImp implements MovementGeneralService {

    private final CuentaRepository cuentaRepository;
    private final MovimientosRepository movimientosRepository;
    @Autowired
    private MicroServerClientCore microServerClientCore;
    private static final String CUENTA= "Cuenta";
    private static final String MOVIMIENTO= "Movimiento";
    ModelMapper modelMapper;
    @Autowired
    public MovementGeneralServiceImp(CuentaRepository cuentaRepository, MovimientosRepository movimientosRepository) {
        this.cuentaRepository = cuentaRepository;
        this.movimientosRepository = movimientosRepository;
        modelMapper = new ModelMapper();
    }

    @Override
    public ResponseEntity<Object> createOrUpdateAccount(AccountDTO cuentaDTO) {
        if (Objects.nonNull(cuentaDTO.getId())){
            Account cuentaSearch = searchData(cuentaDTO.getId(), cuentaRepository, CUENTA);
            BeanUtils.copyProperties(cuentaDTO, cuentaSearch, "id","number_cuenta");
            cuentaRepository.save(cuentaSearch);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseGeneric(CUENTA +" actualizado exitosamente."));
        }
        Account cuenta = modelMapper.map(cuentaDTO, Account.class);
        cuentaRepository.save(cuenta);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseGeneric(CUENTA + " creado exitosamente."));
    }

    @Override
    public ResponseEntity<Object> deleteAccount(Long id) {
        cuentaRepository.delete(searchData(id,cuentaRepository,CUENTA));
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseGeneric(CUENTA+" con id "+id+ " eliminado del sistema."));
    }

    @Override
    public ResponseEntity<Object> getAccountForId(Long id) {
        Account cliente=searchData(id,cuentaRepository,CUENTA);
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(cliente, AccountDTO.class));
    }

    @Override
    @Transactional
    public ResponseEntity<Object> createOrUpdateMovement(MovementDTO movementDTO) {
        Account cuenta = updateSaldo(movementDTO.getAccount(), movementDTO.getValue());
        if (Objects.nonNull(movementDTO.getId())){
            Movements movimientosSearch = searchData(movementDTO.getId(), movimientosRepository, MOVIMIENTO);
            BeanUtils.copyProperties(movementDTO, movimientosSearch, "id");
            movimientosSearch.setAccount(cuenta);
            movimientosSearch.setBalance(cuenta.getInitBalance());
            movimientosRepository.save(movimientosSearch);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseGeneric(MOVIMIENTO +" actualizado exitosamente."));
        }
        Movements movimientos = modelMapper.map(movementDTO, Movements.class);
        movimientos.setAccount(cuenta);
        movimientos.setBalance(cuenta.getInitBalance());
        movimientosRepository.save(movimientos);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseGeneric(MOVIMIENTO + " creado exitosamente."));
    }

    @Override
    public ResponseEntity<Object> deleteMovement(Long id) {
        Movements movimientos = searchData(id, movimientosRepository, MOVIMIENTO);
        updateSaldo(movimientos.getAccount().getId(),movimientos.getValue().negate());
        movimientosRepository.delete(movimientos);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseGeneric(MOVIMIENTO+" con id "+id+ " eliminado del sistema."));
    }

    @Override
    public ResponseEntity<Object> getMovementForId(Long id) {
        Movements movimientos=searchData(id,movimientosRepository,MOVIMIENTO);
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(movimientos, MovementDTO.class));
    }

    private <T> T searchData(Long id, JpaRepository<T,Long> repository, String tipo){
        return repository.findById(id).orElseThrow(
                ()->new EntityNotFoundException(tipo+" no encontrado."));
    }

    public Account updateSaldo(Long idCuenta, BigDecimal value){
        Account cuenta = searchData(idCuenta, cuentaRepository, CUENTA);
        BigDecimal saldoActual = cuenta.getInitBalance();
        if (value.compareTo(BigDecimal.ZERO) < 0 && value.abs().compareTo(saldoActual)>0) {
            throw new SaldoInsuficienteException("Saldo insuficiente para realizar la operación.");
        }
        BigDecimal saldoFinal = saldoActual.add(value);
        cuenta.setInitBalance(saldoFinal);
        return cuentaRepository.save(cuenta);
    }

    @Override
    public ResponseEntity<Object> reporteMovement(MovementDTOSearch movementDTOSearch, Pageable pageable) {
        Pageable pageableD = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
        Specification<Movements> specification = new ReporteMovimientosSearch(movementDTOSearch);
        Page<Movements> allSearchFactura = movimientosRepository.findAll(specification, pageableD);
        Stream<Movements> movimientosStream = allSearchFactura.get();
        Object [] data = getIdClientes(movimientosStream);
        List<Long> idClientes = (List<Long>) data[0];
        List<ResponseMovementDTO> responseMovementDTO = (List<ResponseMovementDTO>) data[1];
        List<CustomerDTO> clienteDTOS = microServerClientCore.sendPostCliente(idClientes);
        responseMovementDTO.forEach(reportemovimiento-> clienteDTOS.forEach(cliente->{
            if (reportemovimiento.getCliente().equals(cliente.getId().toString())){
                reportemovimiento.setCliente(cliente.getName());
            }
        }));
        MovementReportDTO movimientosReportDTO = new MovementReportDTO();
        movimientosReportDTO.setResponseMovementDTO(responseMovementDTO);
        movimientosReportDTO.setTotal(movimientosRepository.count(specification));
        return ResponseEntity.status(HttpStatus.OK).body(movimientosReportDTO);
    }

    private  Object [] getIdClientes(Stream<Movements> movimientosStream){
        Object[] objects = new Object[2];
        List<Long> idClientes = new ArrayList<>();
        List<ResponseMovementDTO> responseMovementDTO = new ArrayList<>();
        movimientosStream.forEach(movimientos -> {
            if(!idClientes.contains(movimientos.getAccount().getClientId().longValue())){
                idClientes.add(movimientos.getAccount().getClientId().longValue());
            }
            responseMovementDTO.add(new ResponseMovementDTO(movimientos));
        });
        objects[0]=idClientes;
        objects[1]=responseMovementDTO;
        return objects;
    }
}
