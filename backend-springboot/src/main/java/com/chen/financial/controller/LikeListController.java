package com.chen.financial.controller;

import com.chen.financial.dto.LikeListQueryDTO;
import com.chen.financial.service.LikeListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.chen.financial.dto.AddLikeListRequest;
import com.chen.financial.dto.UpdateLikeListRequest;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController

@RequestMapping("/api/v1/likes")
// @CrossOrigin 允許來自指定來源的跨域請求，這裡是為了讓前端(localhost:5173)能順利存取
@CrossOrigin(origins = "http://localhost:5173")
public class LikeListController {

    private final LikeListService likeListService;

    // 同樣使用建構子注入來引入 LikeListService
    public LikeListController(LikeListService likeListService) {
        this.likeListService = likeListService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LikeListQueryDTO>> getLikesByUserId(@PathVariable String userId) {
        // 1. 呼叫 Service 層的方法來執行業務邏輯
        List<LikeListQueryDTO> result = likeListService.getLikeListByUserId(userId);

        // 2. 使用 ResponseEntity 來包裝回應結果，並回傳 HTTP 狀態碼 200 (OK)
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<Void> addLike(@RequestBody AddLikeListRequest request) {
        likeListService.createLikeListItem(request);
        // 回傳 201 Created 狀態碼，表示資源成功建立
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{sn}")
    public ResponseEntity<Void> updateLike(@PathVariable Long sn, @RequestBody UpdateLikeListRequest request) {
        likeListService.updateLikeListItem(sn, request);
        // 回傳 200 OK 狀態碼，表示成功
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{sn}")
    public ResponseEntity<Void> deleteLike(@PathVariable Long sn) {
        likeListService.deleteLikeListItem(sn);
        // 回傳 204 No Content 狀態碼，表示成功刪除且沒有回傳內容
        return ResponseEntity.noContent().build();
    }
}