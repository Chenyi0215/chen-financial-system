# chen金融商品喜好紀錄系統 


### 資料庫設定
1.  開啟您的 MySQL 客戶端 (如 MySQL Workbench)。
2.  建立一個新的資料庫 (Schema)，名稱為 `chen_financial`。
3.  執行專案根目錄下 `/DB/DDL.sql` 腳本，以建立所有需要的資料表和預存程序。
4.  (可選) 執行 `/DB/DML.sql` 腳本，以新增幾筆初始的測試資料。

### 後端服務
成功啟動後，後端服務將運行在 `http://localhost:8080`。

### 啟動前端服務

1.  使用終端機 (Terminal) 進入前端專案目錄。
    # 請注意！不是 frontend-vue，而是它裡面的 vue-project
2.  ```bash
    cd frontend-vue/vue-project
    ```
3.  啟動前端開發伺服器：
    ```bash
    npm run dev
    ```
4.  成功啟動後，請在瀏覽器中開啟 `http://localhost:5173/` 來查看網站。

