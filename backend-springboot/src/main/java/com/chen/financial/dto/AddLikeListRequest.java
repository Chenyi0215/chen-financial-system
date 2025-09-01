package com.chen.financial.dto;

import lombok.Data;

@Data
public class AddLikeListRequest {
    private String userId;
    private Integer productId;
    private Integer quantity;
    private String debitAccount;
}