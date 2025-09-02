package com.chen.financial.entity;

import jakarta.persistence.*;
import lombok.Data;

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