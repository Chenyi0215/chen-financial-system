<template>
  <div class="modal-backdrop" @click.self="close">
    <div class="modal-content">
      <h3>{{ isEditMode ? '修改' : '新增' }}喜好商品</h3>
      <form @submit.prevent="submitForm">
        <div class="form-group">
          <label for="product">金融商品:</label>
          <select id="product" v-model="formData.productId" :disabled="isEditMode">
            <option v-for="p in products" :key="p.productId" :value="p.productId">
              {{ p.productName }}
            </option>
          </select>
        </div>
        <div v-if="selectedProduct" class="product-details">
          <span>價格: {{ formatCurrency(selectedProduct.price, false) }}</span>
          <span>手續費率: {{ (selectedProduct.feeRate * 100).toFixed(2) }}%</span>
        </div>
        <div class="form-group">
          <label for="quantity">購買數量:</label>
          <input type="number" id="quantity" v-model.number="formData.quantity" min="1" required>
        </div>
        <div class="form-group">
          <label for="account">扣款帳號:</label>
          <input type="text" id="account" v-model="formData.debitAccount" required>
        </div>

        <div v-if="errorMessage" class="error-message">{{ errorMessage }}</div>

        <div class="form-actions">
          <button type="button" @click="close">取消</button>
          <button type="submit" :disabled="isSubmitting">
            {{ isSubmitting ? '儲存中...' : (isEditMode ? '確認修改' : '確認新增') }}
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref, watch, computed, onMounted } from 'vue';
import apiService from '@/services/apiService';

// 4. 接收從父元件傳來的 prop，名為 itemToEdit
const props = defineProps({
  itemToEdit: {
    type: Object,
    default: null // 預設為 null，代表是新增模式
  }
});

const emit = defineEmits(['close', 'submitted']);

const products = ref([]); // 新增這個 ref 來儲存所有商品

// 在元件掛載時，從 API 獲取所有商品列表
onMounted(async () => {
  try {
    const response = await apiService.getAllProducts();
    products.value = response.data;
  } catch (error) {
    console.error("無法獲取商品列表:", error);
    errorMessage.value = "無法獲取商品列表";
  }
});

// 5. 根據是否有傳入 itemToEdit 來判斷當前是否為編輯模式
const isEditMode = computed(() => !!props.itemToEdit);

// 計算屬性，用於根據 formData.productId 找到完整的商品物件
const selectedProduct = computed(() => {
  if (!formData.value.productId || products.value.length === 0) {
    return null;
  }
  return products.value.find(p => p.productId === formData.value.productId);
});

const formData = ref({});
const isSubmitting = ref(false);
const errorMessage = ref('');

// 6. 使用 watch 監聽 itemToEdit 的變化
watch(() => props.itemToEdit, (newItem) => {
  if (newItem) {
    // 如果是編輯模式，用傳入的資料填滿表單
    formData.value = {
      // 這裡需要從 itemToEdit 中提取 productId, quantity, debitAccount
      // 但我們從 GET API 拿到的資料沒有 productId，這是一個小問題
      // 暫時我們先假設可以拿到
      productId: newItem.productId || 1, // 暫時寫死
      quantity: newItem.quantity || 1,
      debitAccount: newItem.debitAccount
    };
  } else {
    // 如果是新增模式，給表單預設值
    formData.value = {
      userId: 'A123456789',
      productId: 1,
      quantity: 100,
      debitAccount: '1111999666'
    };
  }
}, { immediate: true }); // immediate: true 讓它在元件建立時就立刻執行一次

const close = () => {
  emit('close');
};

const submitForm = async () => {
  isSubmitting.value = true;
  errorMessage.value = '';
  try {
    if (isEditMode.value) {
      // 7. 如果是編輯模式，呼叫 update API
      await apiService.updateLike(props.itemToEdit.sn, formData.value);
    } else {
      // 否則，呼叫 add API
      await apiService.addLike(formData.value);
    }
    emit('submitted');
  } catch (error) {
    console.error('儲存失敗:', error);
    errorMessage.value = '儲存失敗，請稍後再試。';
  } finally {
    isSubmitting.value = false;
  }
};

const formatCurrency = (value, showSymbol = true) => {
  if (value === null || value === undefined) return showSymbol ? 'TWD 0.00' : '0.00';
  const options = {
    style: 'currency',
    currency: 'TWD',
    minimumFractionDigits: 2,
    maximumFractionDigits: 2
  };
  if (!showSymbol) {
    // 如果不要貨幣符號，就用純數字格式
    return new Intl.NumberFormat('en-US', {
      minimumFractionDigits: 2,
      maximumFractionDigits: 2
    }).format(value);
  }
  return new Intl.NumberFormat('zh-TW', options).format(value);
};
</script>

<style scoped>
.product-details {
  padding: 10px;
  margin-bottom: 1.5rem;
  background-color: #f8f9fa;
  border: 1px solid #dee2e6;
  border-radius: 4px;
  display: flex;
  justify-content: space-around;
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
}
.modal-content {
  background-color: white;
  padding: 2rem;
  border-radius: 8px;
  width: 90%;
  max-width: 500px;
}
.form-group {
  margin-bottom: 1.5rem;
}
.form-group label {
  display: block;
  margin-bottom: 0.5rem;
}
.form-group input,
.form-group select {
  width: 100%;
  padding: 0.5rem;
  font-size: 1rem;
  border: 1px solid #ccc;
  border-radius: 4px;
}
.form-actions {
  display: flex;
  justify-content: flex-end;
  gap: 1rem;
  margin-top: 2rem;
}
.form-actions button {
  padding: 0.5rem 1.5rem;
}
.error-message {
  color: red;
  margin-top: 1rem;
}
</style>
