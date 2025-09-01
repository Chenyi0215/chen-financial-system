package com.chen.financial.repository;

import com.chen.financial.dto.LikeListQueryDTO;
import com.chen.financial.entity.LikeList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeListRepository extends JpaRepository<LikeList, Long> {

    /**
     * 呼叫 sp_get_like_list_by_user 這個 Stored Procedure。
     * procedureName 必須和 DDL.sql 中的名稱完全一樣。
     * Spring Data JPA 會自動將結果封裝成 List<LikeListQueryDTO>。
     * @param userId 傳入的 p_user_id 參數
     * @return 查詢結果列表
     */
    @Procedure(procedureName = "sp_get_like_list_by_user")
    List<LikeListQueryDTO> findLikeListByUserId(@Param("p_user_id") String userId);

    /**
     * 呼叫 sp_add_like_list 新增一筆喜好紀錄。
     * @param p_user_id 使用者 ID
     * @param p_product_id 產品 ID
     * @param p_quantity 數量
     * @param p_debit_account 扣款帳號
     */
    @Procedure(procedureName = "sp_add_like_list")
    void addLikeList(
            @Param("p_user_id") String p_user_id,
            @Param("p_product_id") Integer p_product_id,
            @Param("p_quantity") Integer p_quantity,
            @Param("p_debit_account") String p_debit_account
    );

    /**
     * 呼叫 sp_update_like_list 更新指定的喜好紀錄。
     * @param p_sn 紀錄的流水號 (Primary Key)
     * @param p_product_id 產品 ID
     * @param p_quantity 數量
     * @param p_debit_account 扣款帳號
     */
    @Procedure(procedureName = "sp_update_like_list")
    void updateLikeList(
            @Param("p_sn") Long p_sn,
            @Param("p_product_id") Integer p_product_id,
            @Param("p_quantity") Integer p_quantity,
            @Param("p_debit_account") String p_debit_account
    );

    /**
     * 呼叫 sp_delete_like_list 刪除指定的喜好紀錄。
     * @param p_sn 紀錄的流水號 (Primary Key)
     */
    @Procedure(procedureName = "sp_delete_like_list")
    void deleteLikeList(@Param("p_sn") Long p_sn);
}