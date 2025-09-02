package com.chen.financial.dto;

import java.math.BigDecimal;

/**
 * 這是一個 "Projection Interface"，專門用來接收 Stored Procedure
 * sp_get_like_list_by_user 的查詢結果。
 * Spring Data JPA 會自動將查詢結果的欄位對應到這些 get 方法。
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