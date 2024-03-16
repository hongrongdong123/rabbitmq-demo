package com.example.reliability.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Orders {
    private String id;
    private String orderName;
    private BigDecimal orderMoney;
    private Date orderTime;

}
