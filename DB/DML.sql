
-- 清空舊資料，避免重複新增
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE `like_list`;
TRUNCATE TABLE `user`;
TRUNCATE TABLE `product`;
SET FOREIGN_KEY_CHECKS = 1;

-- 新增1位使用者
INSERT INTO `user` (`user_id`, `user_name`, `email`, `account`) VALUES
    ('A123456789', '王明', 'wang@email.com', '1111999666');

-- 新增3個金融商品
INSERT INTO `product` (`product_name`, `price`, `fee_rate`) VALUES
                                                                ('陳家精選全球科技基金', 15.75, 0.0150),
                                                                ('陳家亞洲高收益債券', 10.20, 0.0120),
                                                                ('陳家黃金ETF', 180.50, 0.0050);

-- 新增2筆喜好清單紀錄(測試）
-- 王明 購買了 100 單位 "陳家精選全球科技基金"
INSERT INTO `like_list` (`user_id`, `product_id`, `quantity`, `debit_account`) VALUES
    ('A123456789', 1, 100, '1111999666');

-- 王明 購買了 50 單位 "陳家黃金ETF"
INSERT INTO `like_list` (`user_id`, `product_id`, `quantity`, `debit_account`) VALUES
    ('A123456789', 3, 50, '1111999666');