package com.chen.financial.service;

import com.chen.financial.dto.LikeListQueryDTO;
import com.chen.financial.repository.LikeListRepository;
import com.chen.financial.dto.AddLikeListRequest;
import com.chen.financial.dto.UpdateLikeListRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @Service 告訴 Spring 框架，這個類別是一個業務邏輯元件。
 */
@Service
public class LikeListService {

    private final LikeListRepository likeListRepository;

    /**
     * 這是建構子注入 (Constructor Injection)。
     * Spring 會自動把 LikeListRepository 的實例 "注入" 進來。
     * 這是目前官方最推薦的依賴注入方式。
     */
    public LikeListService(LikeListRepository likeListRepository) {
        this.likeListRepository = likeListRepository;
    }

    /**
     * 處理查詢喜好清單的業務邏輯。
     * 目前我們的邏輯很簡單：直接呼叫 Repository 的方法。
     * 未來可以在這裡加入更複雜的邏輯，例如權限檢查。
     * @param userId 使用者 ID
     * @return 查詢到的喜好清單
     */
    @Transactional(readOnly = true)
    public List<LikeListQueryDTO> getLikeListByUserId(String userId) {
        // 直接將 Repository 查詢到的結果回傳
        return likeListRepository.findLikeListByUserId(userId);
    }

    /**
     * 新增一筆喜好紀錄。
     * @Transactional 這個註解非常重要。它能確保方法內的資料庫操作要麼全部成功，要麼全部失敗回滾。
     * 雖然我們這裡只呼叫一個方法，但這是個好習慣。
     */
    @Transactional
    public void createLikeListItem(AddLikeListRequest request) {
        likeListRepository.addLikeList(
                request.getUserId(),
                request.getProductId(),
                request.getQuantity(),
                request.getDebitAccount()
        );
    }

    /**
     * 更新一筆喜好紀錄。
     */
    @Transactional
    public void updateLikeListItem(Long sn, UpdateLikeListRequest request) {
        // 在這裡可以加上檢查，例如 sn 是否存在
        likeListRepository.updateLikeList(
                sn,
                request.getProductId(),
                request.getQuantity(),
                request.getDebitAccount()
        );
    }

    /**
     * 刪除一筆喜好紀錄。
     */
    @Transactional
    public void deleteLikeListItem(Long sn) {
        likeListRepository.deleteLikeList(sn);
    }
}