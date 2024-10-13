package com.jpsolanoc.transactions.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MovementReportDTO {
    private List<ResponseMovementDTO> responseMovementDTO;
    private long total;
}
