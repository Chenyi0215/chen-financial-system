package com.chen.financial.dto;

import java.math.BigDecimal;

/**
 * 這是一個 "Projection Interface"，專門用來接收 Stored Procedure
 * sp_get_like_list_by_user 的查詢結果。
 */

public interface LikeListQueryDTO {
    Long getSn();
    Integer getProductId();
    Integer getQuantity();
    String getProductName();
    BigDecimal getPrice();
    BigDecimal getFeeRate();
    String getDebitAccount();
    String getEmail();
    BigDecimal getTotalFee();
    BigDecimal getTotalAmount();
}