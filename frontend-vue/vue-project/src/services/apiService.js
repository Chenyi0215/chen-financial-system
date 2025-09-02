import axios from 'axios';


const apiClient = axios.create({
  baseURL: 'http://localhost:8080/api/v1', // 後端 API 基礎路徑
  headers: {
    'Content-Type': 'application/json'
  }
});


export default {
  // 取得喜好清單
  getLikes(userId) {
    return apiClient.get(`/likes/user/${userId}`);
  },

  // 新增喜好項目
  addLike(data) {
    return apiClient.post('/likes', data);
  },

  // 更新喜好項目
  updateLike(sn, data) {
    return apiClient.put(`/likes/${sn}`, data);
  },

  // 刪除喜好項目
  deleteLike(sn) {
    return apiClient.delete(`/likes/${sn}`);
  },
  getAllProducts() {
    return apiClient.get('/products');
  }
};
