<template>
  <div class="preference-dashboard">
    <div class="header">
      <h2>我的喜好清單</h2>
      <button @click="openAddModal">新增喜好商品</button>
    </div>

    <PreferenceForm
      v-if="showModal"
      :item-to-edit="currentItem"
      @close="closeModal"
      @submitted="handleFormSubmit"
    />

    <DeleteConfirmationModal
      v-if="showDeleteModal"
      :item-to-delete="itemToDelete"
      @close="closeDeleteModal"
      @confirm-delete="handleConfirmDelete"
    />
    <div v-if="loading">正在努力載入中...</div>
    <div v-else-if="error" class="error-message">
      糟糕！載入資料時發生錯誤: {{ error }}
    </div>

    <div v-else-if="preferences.length > 0" class="table-scroller">
      <table class="compact">
        <thead>
        <tr>
          <th class="col-product-name">商品名稱</th>
          <th class="col-quantity">購買數量</th>
          <th class="col-account">扣款帳號</th>
          <th class="col-fee">總手續費用</th>
          <th class="col-amount">預計扣款總金額</th>
          <th class="col-email">使用者信箱</th>
          <th class="col-actions">操作</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="item in preferences" :key="item.sn">
          <td class="col-product-name" :title="item.productName">{{ item.productName }}</td>
          <td class="col-quantity">{{ item.quantity }}</td>
          <td class="col-account" :title="item.debitAccount">{{ item.debitAccount }}</td>
          <td class="col-fee">{{ formatCurrency(item.totalFee) }}</td>
          <td class="col-amount">{{ formatCurrency(item.totalAmount) }}</td>
          <td class="col-email" :title="item.email">{{ item.email }}</td>
          <td class="col-actions">
            <button class="action-btn edit" @click="openEditModal(item)">修改</button>
            <button class="action-btn delete" @click="openDeleteModal(item)">刪除</button>
          </td>
        </tr>
        </tbody>
      </table>
    </div>

    <div v-else>
      您目前沒有任何喜好的金融商品。
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import apiService from '@/services/apiService';
import PreferenceForm from '@/components/PreferenceForm.vue';
import DeleteConfirmationModal from '@/components/DeleteConfirmationModal.vue';

const preferences = ref([]); // 用於存放從 API 取得的喜好清單陣列
const loading = ref(true);    // 用於判斷是否正在載入中，預設為 true
const error = ref(null);      // 用於存放錯誤訊息
const currentUserId = 'A123456789'; // 為了測試，暫時寫死的範例使用者 ID

// 控制新增/修改視窗的狀態
const showModal = ref(false);
const currentItem = ref(null);

// 控制刪除確認視窗的狀態
const showDeleteModal = ref(false);
const itemToDelete = ref(null);

const openAddModal = () => {
  currentItem.value = null;
  showModal.value = true;
};

const openEditModal = (item) => {
  currentItem.value = item;
  showModal.value = true;
};

const closeModal = () => {
  showModal.value = false;
  currentItem.value = null;
};

const handleFormSubmit = () => {
  closeModal();
  loadPreferences();
};

const openDeleteModal = (item) => {
  itemToDelete.value = item; showDeleteModal.value = true;
};
const closeDeleteModal = () => {
  showDeleteModal.value = false; itemToDelete.value = null;
};

const handleConfirmDelete = async (sn) => {
  try {
    await apiService.deleteLike(sn);
    alert('刪除成功！');
    closeDeleteModal();
    await loadPreferences();
  } catch (err) {
    console.error('刪除失敗:', err);
    alert('刪除失敗，請稍後再試。');
  }
};

const loadPreferences = async () => {
  try {
    error.value = null;
    loading.value = true;
    const response = await apiService.getLikes(currentUserId);
    preferences.value = response.data;
  } catch (err) {
    console.error('查詢喜好清單失敗:', err);
    error.value = err.message || '無法載入資料';
  } finally {
    loading.value = false;
  }
};

//    這能確保在 Vue 元件被掛載到網頁畫面上之後，自動執行一次 loadPreferences() 函式
onMounted(() => {
  loadPreferences();
});

// 用於將數字格式化為漂亮的貨幣字串
const formatCurrency = (value) => {
  if (value === null || value === undefined) return 'TWD 0.00';
  return new Intl.NumberFormat('zh-TW', {
    style: 'currency',
    currency: 'TWD',
    minimumFractionDigits: 2,
    maximumFractionDigits: 2
  }).format(value);
};
</script>

<style scoped>
.preference-dashboard {
  padding: 20px;
  max-width: 95%;
  margin: 0 auto;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.header button {
  background-color: #007bff;
  color: white;
  border: none;
  padding: 10px 15px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 1rem;
  transition: background-color 0.2s;
}

.header button:hover {
  background-color: #0056b3;
}

.table-scroller {
  overflow-x: auto; /* 實現橫向捲動的關鍵 */
  border-radius: 8px;
}

table.compact {
  width: 100%;
  border-collapse: separate;
  border-spacing: 0;
  table-layout: fixed;     /* 固定欄寬運作的關鍵 */
  min-width: 980px;       /* 防止欄位在窄螢幕被壓爛，可依實際調整 */
  font-size: 0.95rem;
}

thead th {
  position: sticky;        /* 捲動時表頭固定 */
  top: 0;
  z-index: 1;
  background: #f8f9fa;
  color: #343a40;
  font-weight: 600;
  text-align: left;
  padding: 12px 15px;
  border-bottom: 2px solid #dee2e6;
  white-space: nowrap;
}

tbody tr {
  transition: background-color 0.15s ease;
}

tbody tr:hover {
  background-color: #f1f7ff;
}

td {
  padding: 12px 15px;
  border-bottom: 1px solid #e9ecef;
  vertical-align: middle;
  /* 單行顯示 + 超長省略號 */
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

/* 欄寬配置 */
.col-product-name { width: 280px; }
.col-quantity     { width: 100px; text-align: right; }
.col-account      { width: 150px; }
.col-fee,
.col-amount       { width: 150px; text-align: right; }
.col-email        { width: 220px; }
.col-actions      { width: 200px; text-align: center; }

.action-btn {
  padding: 6px 14px;
  margin: 0 4px;
  border: none;
  border-radius: 4px;
  color: white;
  cursor: pointer;
  font-size: 0.9rem;
  transition: opacity 0.2s;
}
.action-btn:hover {
  opacity: 0.85;
}
.edit {
  background-color: #ffc107;
}
.delete {
  background-color: #dc3545;
}
.error-message {
  color: #dc3545;
  font-weight: bold;
}

.modal-backdrop {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

</style>
