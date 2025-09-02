import axios from 'axios';

// 建立一個 axios 的實例，並預先設定好後端 API 的基礎路徑
const apiClient = axios.create({
  baseURL: 'http://localhost:8080/api/v1', // 我們的後端 API 基礎路徑
  headers: {
    'Content-Type': 'application/json'
  }
});

// 匯出一個物件，裡面包含了我們所有要呼叫的 API 方法
export default {
  // 取得喜好清單
  getLikes(userId) {
    return apiClient.get(`/likes/user/${userId}`);
  },

  // 新增喜好項目 (我們稍後會用到)
  addLike(data) {
    return apiClient.post('/likes', data);
  },

  // 更新喜好項目 (我們稍後會用到)
  updateLike(sn, data) {
    return apiClient.put(`/likes/${sn}`, data);
  },

  // 刪除喜好項目 (我們稍後會用到)
  deleteLike(sn) {
    return apiClient.delete(`/likes/${sn}`);
  },
  getAllProducts() {
    return apiClient.get('/products');
  }
};
