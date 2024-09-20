package com.jpsolanoc.transactions.service.imp;

import com.jpsolanoc.transactions.app.MicroServerClientCore;
import com.jpsolanoc.transactions.dto.*;
import com.jpsolanoc.transactions.entity.Cuenta;
import com.jpsolanoc.transactions.entity.Movimientos;
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
    public ResponseEntity<Object> createOrUpdateCuenta(CuentaDTO cuentaDTO) {
        if (Objects.nonNull(cuentaDTO.getId())){
            Cuenta cuentaSearch = searchData(cuentaDTO.getId(), cuentaRepository, CUENTA);
            BeanUtils.copyProperties(cuentaDTO, cuentaSearch, "id","number_cuenta");
            cuentaRepository.save(cuentaSearch);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseGeneric(CUENTA +" actualizado exitosamente."));
        }
        Cuenta cuenta = modelMapper.map(cuentaDTO, Cuenta.class);
        cuentaRepository.save(cuenta);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseGeneric(CUENTA + " creado exitosamente."));
    }

    @Override
    public ResponseEntity<Object> deleteCuenta(Long id) {
        cuentaRepository.delete(searchData(id,cuentaRepository,CUENTA));
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseGeneric(CUENTA+" con id "+id+ " eliminado del sistema."));
    }

    @Override
    public ResponseEntity<Object> getCuentaForId(Long id) {
        Cuenta cliente=searchData(id,cuentaRepository,CUENTA);
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(cliente, CuentaDTO.class));
    }

    @Override
    @Transactional
    public ResponseEntity<Object> createOrUpdateMoviminetos(MovimientosDTO movimientosDTO) {
        Cuenta cuenta = updateSaldo(movimientosDTO.getCuenta(),movimientosDTO.getValue());
        if (Objects.nonNull(movimientosDTO.getId())){
            Movimientos movimientosSearch = searchData(movimientosDTO.getId(), movimientosRepository, MOVIMIENTO);
            BeanUtils.copyProperties(movimientosDTO, movimientosSearch, "id");
            movimientosSearch.setCuenta(cuenta);
            movimientosSearch.setBalance(cuenta.getInitBalance());
            movimientosRepository.save(movimientosSearch);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseGeneric(MOVIMIENTO +" actualizado exitosamente."));
        }
        Movimientos movimientos = modelMapper.map(movimientosDTO, Movimientos.class);
        movimientos.setCuenta(cuenta);
        movimientos.setBalance(cuenta.getInitBalance());
        movimientosRepository.save(movimientos);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseGeneric(MOVIMIENTO + " creado exitosamente."));
    }

    @Override
    public ResponseEntity<Object> deleteMovimientos(Long id) {
        Movimientos movimientos = searchData(id, movimientosRepository, MOVIMIENTO);
        updateSaldo(movimientos.getCuenta().getId(),movimientos.getValue().negate());
        movimientosRepository.delete(movimientos);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseGeneric(MOVIMIENTO+" con id "+id+ " eliminado del sistema."));
    }

    @Override
    public ResponseEntity<Object> getMovimientosForId(Long id) {
        Movimientos movimientos=searchData(id,movimientosRepository,MOVIMIENTO);
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(movimientos, MovimientosDTO.class));
    }

    private <T> T searchData(Long id, JpaRepository<T,Long> repository, String tipo){
        return repository.findById(id).orElseThrow(
                ()->new EntityNotFoundException(tipo+" no encontrado."));
    }

    public Cuenta updateSaldo(Long idCuenta, BigDecimal value){
        Cuenta cuenta = searchData(idCuenta, cuentaRepository, CUENTA);
        BigDecimal saldoActual = cuenta.getInitBalance();
        if (value.compareTo(BigDecimal.ZERO) < 0 && value.abs().compareTo(saldoActual)>0) {
            throw new SaldoInsuficienteException("Saldo insuficiente para realizar la operación.");
        }
        BigDecimal saldoFinal = saldoActual.add(value);
        cuenta.setInitBalance(saldoFinal);
        return cuentaRepository.save(cuenta);
    }

    @Override
    public ResponseEntity<Object> reporteMovimientos(MovimientosDTOSearch movimientosDTOSearch, Pageable pageable) {
        Pageable pageableD = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
        Specification<Movimientos> specification = new ReporteMovimientosSearch(movimientosDTOSearch);
        Page<Movimientos> allSearchFactura = movimientosRepository.findAll(specification, pageableD);
        Stream<Movimientos> movimientosStream = allSearchFactura.get();
        Object [] data = getIdClientes(movimientosStream);
        List<Long> idClientes = (List<Long>) data[0];
        List<ResponseMovementDTO> responseMovementDTO = (List<ResponseMovementDTO>) data[1];
        List<ClienteDTO> clienteDTOS = microServerClientCore.sendPostCliente(idClientes);
        responseMovementDTO.forEach(reportemovimiento-> clienteDTOS.forEach(cliente->{
            if (reportemovimiento.getCliente().equals(cliente.getId().toString())){
                reportemovimiento.setCliente(cliente.getName());
            }
        }));
        MovimientosReportDTO movimientosReportDTO = new MovimientosReportDTO();
        movimientosReportDTO.setResponseMovementDTO(responseMovementDTO);
        movimientosReportDTO.setTotal(movimientosRepository.count(specification));
        return ResponseEntity.status(HttpStatus.OK).body(movimientosReportDTO);
    }

    private  Object [] getIdClientes(Stream<Movimientos> movimientosStream){
        Object[] objects = new Object[2];
        List<Long> idClientes = new ArrayList<>();
        List<ResponseMovementDTO> responseMovementDTO = new ArrayList<>();
        movimientosStream.forEach(movimientos -> {
            if(!idClientes.contains(movimientos.getCuenta().getIdCliente().longValue())){
                idClientes.add(movimientos.getCuenta().getIdCliente().longValue());
            }
            responseMovementDTO.add(new ResponseMovementDTO(movimientos));
        });
        objects[0]=idClientes;
        objects[1]=responseMovementDTO;
        return objects;
    }
}
