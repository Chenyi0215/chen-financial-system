package com.chen.financial.controller;

import com.chen.financial.dto.LikeListQueryDTO;
import com.chen.financial.service.LikeListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.chen.financial.dto.AddLikeListRequest;
import com.chen.financial.dto.UpdateLikeListRequest;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * @RestController 是一個組合註解，包含 @Controller 和 @ResponseBody。
 * 它告訴 Spring 這個類別負責處理 HTTP 請求，並且方法的回傳值會被自動轉換成 JSON 格式。
 */
@RestController
// @RequestMapping 定義了這個 Controller 中所有 API 的基礎路徑
@RequestMapping("/api/v1/likes")
// @CrossOrigin 允許來自指定來源的跨域請求，這裡是為了讓前端(localhost:5173)能順利存取
@CrossOrigin(origins = "http://localhost:5173")
public class LikeListController {

    private final LikeListService likeListService;

    // 同樣使用建構子注入來引入 LikeListService
    public LikeListController(LikeListService likeListService) {
        this.likeListService = likeListService;
    }

    /**
     * @GetMapping("/user/{userId}") 定義了處理 HTTP GET 請求的方法。
     * 完整 URL 將會是: http://localhost:8080/api/v1/likes/user/{userId}
     * 例如: http://localhost:8080/api/v1/likes/user/A123456789
     * @PathVariable 會將 URL 路徑中的 {userId} 值，綁定到方法的參數 String userId 上。
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LikeListQueryDTO>> getLikesByUserId(@PathVariable String userId) {
        // 1. 呼叫 Service 層的方法來執行業務邏輯
        List<LikeListQueryDTO> result = likeListService.getLikeListByUserId(userId);

        // 2. 使用 ResponseEntity 來包裝回應結果，並回傳 HTTP 狀態碼 200 (OK)
        return ResponseEntity.ok(result);
    }

    /**
     * @PostMapping 用於處理 HTTP POST 請求，通常用於「新增」資源。
     * 完整 URL: POST http://localhost:8080/api/v1/likes
     * @RequestBody 會將請求的 JSON body 轉換成 AddLikeListRequest 物件。
     */
    @PostMapping
    public ResponseEntity<Void> addLike(@RequestBody AddLikeListRequest request) {
        likeListService.createLikeListItem(request);
        // 回傳 201 Created 狀態碼，表示資源成功建立
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * @PutMapping 用於處理 HTTP PUT 請求，通常用於「更新」整個資源。
     * 完整 URL: PUT http://localhost:8080/api/v1/likes/{sn} (例如 /api/v1/likes/1)
     */
    @PutMapping("/{sn}")
    public ResponseEntity<Void> updateLike(@PathVariable Long sn, @RequestBody UpdateLikeListRequest request) {
        likeListService.updateLikeListItem(sn, request);
        // 回傳 200 OK 狀態碼，表示成功
        return ResponseEntity.ok().build();
    }

    /**
     * @DeleteMapping 用於處理 HTTP DELETE 請求，通常用於「刪除」資源。
     * 完整 URL: DELETE http://localhost:8080/api/v1/likes/{sn} (例如 /api/v1/likes/1)
     */
    @DeleteMapping("/{sn}")
    public ResponseEntity<Void> deleteLike(@PathVariable Long sn) {
        likeListService.deleteLikeListItem(sn);
        // 回傳 204 No Content 狀態碼，表示成功刪除且沒有回傳內容
        return ResponseEntity.noContent().build();
    }
}