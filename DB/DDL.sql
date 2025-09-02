-- 刪除舊的資料表 (如果存在)，方便重新建立
DROP TABLE IF EXISTS `like_list`;
DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `product`;

-- 刪除舊的預存程序 (如果存在)
DROP PROCEDURE IF EXISTS sp_add_like_list;
DROP PROCEDURE IF EXISTS sp_get_like_list_by_user;
DROP PROCEDURE IF EXISTS sp_delete_like_list;
DROP PROCEDURE IF EXISTS sp_update_like_list;


-- =================================================================
-- 建立資料表 (Tables)
-- =================================================================

-- 使用者資料表
CREATE TABLE `user` (
                        `user_id` VARCHAR(20) NOT NULL COMMENT '使用者 ID',
                        `user_name` VARCHAR(50) NOT NULL COMMENT '使用者名稱',
                        `email` VARCHAR(100) NOT NULL COMMENT '使用者電子郵件',
                        `account` VARCHAR(20) NOT NULL COMMENT '扣款帳號',
                        PRIMARY KEY (`user_id`),
                        UNIQUE KEY `uk_email` (`email`)
) COMMENT='使用者資料';

-- 產品資料表
CREATE TABLE `product` (
                           `product_id` INT NOT NULL AUTO_INCREMENT COMMENT '產品流水號',
                           `product_name` VARCHAR(100) NOT NULL COMMENT '產品名稱',
                           `price` DECIMAL(18, 4) NOT NULL COMMENT '產品價格',
                           `fee_rate` DECIMAL(5, 4) NOT NULL COMMENT '手續費率 (例如 0.01 代表 1%)',
                           PRIMARY KEY (`product_id`)
) COMMENT='產品資料';

-- 喜好清單資料表
CREATE TABLE `like_list` (
                             `sn` BIGINT NOT NULL AUTO_INCREMENT COMMENT '流水序號',
                             `user_id` VARCHAR(20) NOT NULL COMMENT '使用者 ID (FK)',
                             `product_id` INT NOT NULL COMMENT '產品 ID (FK)',
                             `quantity` INT NOT NULL COMMENT '購買數量',
                             `debit_account` VARCHAR(20) NOT NULL COMMENT '預計扣款帳號',
                             `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '建立時間',
                             PRIMARY KEY (`sn`),
                             FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`),
                             FOREIGN KEY (`product_id`) REFERENCES `product`(`product_id`)
) COMMENT='喜好清單';


-- =================================================================
-- 建立預存程序 (Stored Procedures)
-- =================================================================

-- 新增喜好商品
DELIMITER $$
CREATE PROCEDURE sp_add_like_list(
    IN p_user_id VARCHAR(20),
    IN p_product_id INT,
    IN p_quantity INT,
    IN p_debit_account VARCHAR(20)
)
BEGIN
INSERT INTO like_list (user_id, product_id, quantity, debit_account)
VALUES (p_user_id, p_product_id, p_quantity, p_debit_account);
SELECT LAST_INSERT_ID() as new_sn;
END$$
DELIMITER ;

-- 根據使用者查詢喜好清單 (包含計算)
DELIMITER $$
CREATE PROCEDURE sp_get_like_list_by_user(
    IN p_user_id VARCHAR(20)
)
    READS SQL DATA
BEGIN
SELECT
    ll.sn,
    ll.product_id AS productId,
    ll.quantity,
    p.product_name AS productName,
    p.price,
    p.fee_rate AS feeRate,
    ll.debit_account AS debitAccount,
    u.email,
    (p.price * ll.quantity * p.fee_rate) AS totalFee,
    (p.price * ll.quantity * (1 + p.fee_rate)) AS totalAmount
FROM like_list ll
         JOIN user u ON ll.user_id = u.user_id
         JOIN product p ON ll.product_id = p.product_id
WHERE ll.user_id = p_user_id;
END$$
DELIMITER ;

-- 刪除喜好商品
DELIMITER $$
CREATE PROCEDURE sp_delete_like_list(
    IN p_sn BIGINT
)
BEGIN
DELETE FROM like_list WHERE sn = p_sn;
END$$
DELIMITER ;

-- 更新喜好商品
DELIMITER $$
CREATE PROCEDURE sp_update_like_list(
    IN p_sn BIGINT,
    IN p_product_id INT,
    IN p_quantity INT,
    IN p_debit_account VARCHAR(20)
)
BEGIN
UPDATE like_list
SET
    product_id = p_product_id,
    quantity = p_quantity,
    debit_account = p_debit_account
WHERE sn = p_sn;
END$$
DELIMITER ;