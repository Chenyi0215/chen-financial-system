package com.chen.financial.service;

import com.chen.financial.dto.LikeListQueryDTO;
import com.chen.financial.repository.LikeListRepository;
import com.chen.financial.dto.AddLikeListRequest;
import com.chen.financial.dto.UpdateLikeListRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class LikeListService {

    private final LikeListRepository likeListRepository;

    /**
     * Spring 會自動把 LikeListRepository 的實例 "注入" 進來。
     */
    public LikeListService(LikeListRepository likeListRepository) {
        this.likeListRepository = likeListRepository;
    }

    @Transactional(readOnly = true)
    public List<LikeListQueryDTO> getLikeListByUserId(String userId) {
        return likeListRepository.findLikeListByUserId(userId);
    }

    @Transactional
    public void createLikeListItem(AddLikeListRequest request) {
        likeListRepository.addLikeList(
                request.getUserId(),
                request.getProductId(),
                request.getQuantity(),
                request.getDebitAccount()
        );
    }

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

    @Transactional
    public void deleteLikeListItem(Long sn) {
        likeListRepository.deleteLikeList(sn);
    }
}