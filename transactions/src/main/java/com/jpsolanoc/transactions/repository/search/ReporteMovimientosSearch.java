package com.jpsolanoc.transactions.repository.search;

import com.jpsolanoc.transactions.dto.MovimientosDTOSearch;
import com.jpsolanoc.transactions.entity.Cuenta;
import com.jpsolanoc.transactions.entity.Movimientos;
import com.jpsolanoc.transactions.util.Util;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReporteMovimientosSearch implements Specification<Movimientos> {

    private final MovimientosDTOSearch dtoSearch;

    public ReporteMovimientosSearch(MovimientosDTOSearch dtoSearch) {
        this.dtoSearch = dtoSearch;
    }

    @Override
    public Predicate toPredicate(Root<Movimientos> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        Join<Movimientos, Cuenta> cuentaJoin = root.join("cuenta");
        predicates.add(criteriaBuilder.equal(cuentaJoin.get("idCliente"), dtoSearch.getClienteId()));
        if (dtoSearch.getFechaInicio()!= null && dtoSearch.getFechaFin() != null) {
            Date from = Util.addOneDay(dtoSearch.getFechaInicio());
            Date to = Util.addOneDay(dtoSearch.getFechaFin());
            predicates.add(criteriaBuilder.between(root.get("createAt"),
                    Util.addHourOfDate(from,0,0,0),
                    Util.addHourOfDate(to,23,59,59)));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
