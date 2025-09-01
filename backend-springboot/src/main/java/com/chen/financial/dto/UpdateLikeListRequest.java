package com.chen.financial.dto;

import lombok.Data;

@Data
public class UpdateLikeListRequest {
    private Integer productId;
    private Integer quantity;
    private String debitAccount;
}