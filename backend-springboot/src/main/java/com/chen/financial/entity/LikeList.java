package com.chen.financial.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * 這個 Entity 主要用於讓 JPA 知道有 like_list 這張表的存在。
 * 我們的所有操作都將透過 Stored Procedure 進行，而不是 JPA 的標準 save(), findById() 等方法。
 */
@Entity
@Data
@Table(name = "like_list")
public class LikeList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sn;
    private String userId;
    private Integer productId;
    private Integer quantity;
    private String debitAccount;
}