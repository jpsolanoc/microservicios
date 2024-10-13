package com.jpsolanoc.transactions.repository.search;

import com.jpsolanoc.transactions.dto.MovementDTOSearch;
import com.jpsolanoc.transactions.entity.Account;
import com.jpsolanoc.transactions.entity.Movements;
import com.jpsolanoc.transactions.util.Util;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReporteMovimientosSearch implements Specification<Movements> {

    private final MovementDTOSearch dtoSearch;

    public ReporteMovimientosSearch(MovementDTOSearch dtoSearch) {
        this.dtoSearch = dtoSearch;
    }

    @Override
    public Predicate toPredicate(Root<Movements> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        Join<Movements, Account> cuentaJoin = root.join("account");
        predicates.add(criteriaBuilder.equal(cuentaJoin.get("clientId"), dtoSearch.getClientId()));

        if (dtoSearch.getDateInit() != null && dtoSearch.getDateEnd() != null) {
            Date from = Util.addHourOfDate(dtoSearch.getDateInit(), 0, 0, 0);
            Date to = Util.addHourOfDate(dtoSearch.getDateEnd(), 23, 59, 59);
            predicates.add(criteriaBuilder.between(root.get("createAt"), from, to));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
