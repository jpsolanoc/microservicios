package com.jpsolanoc.transactions.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MovimientosReportDTO {
    private List<ResponseMovementDTO> responseMovementDTO;
    private long total;
}
