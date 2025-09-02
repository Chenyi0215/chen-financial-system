<template>
  <div class="modal-backdrop">
    <div class="modal-content">
      <h3>確認刪除</h3>
      <p>您確定要刪除以下這筆喜好紀錄嗎？此操作無法復原。</p>

      <div v-if="itemToDelete" class="item-details">
        <div><strong>商品名稱:</strong> {{ itemToDelete.productName }}</div>
        <div><strong>產品價格:</strong> {{ formatCurrency(itemToDelete.price) }}</div>
        <div><strong>手續費率:</strong> {{ (itemToDelete.feeRate * 100).toFixed(2) }}%</div>
      </div>

      <div class="form-actions">
        <button type="button" class="btn-cancel" @click="close">取消</button>
        <button type="button" class="btn-confirm-delete" @click="confirmDelete">確認刪除</button>
      </div>
    </div>
  </div>
</template>

<script setup>
const props = defineProps({
  itemToDelete: {
    type: Object,
    required: true
  }
});

const emit = defineEmits(['close', 'confirm-delete']);

const close = () => {
  emit('close');
};

const confirmDelete = () => {
  emit('confirm-delete', props.itemToDelete.sn); // 將要刪除的 sn 傳遞出去
};

const formatCurrency = (value) => {
  if (value === null || value === undefined) return 'N/A';
  return new Intl.NumberFormat('zh-TW', {
    style: 'currency',
    currency: 'TWD',
    minimumFractionDigits: 2,
    maximumFractionDigits: 2
  }).format(value);
};
</script>

<style scoped>
.modal-backdrop {
  position: fixed; top: 0; left: 0; width: 100%; height: 100%;
  background-color: rgba(0, 0, 0, 0.6);
  display: flex; justify-content: center; align-items: center; z-index: 1000;
}
.modal-content {
  background-color: white; padding: 2rem; border-radius: 8px;
  width: 90%; max-width: 450px; box-shadow: 0 5px 15px rgba(0,0,0,0.3);
}
h3 { margin-top: 0; }
p { margin-bottom: 20px; }
.item-details {
  background-color: #f8f9fa;
  border: 1px solid #dee2e6;
  border-radius: 4px;
  padding: 15px;
  margin-bottom: 20px;
}
.item-details div {
  margin-bottom: 8px;
}
.item-details div:last-child {
  margin-bottom: 0;
}
.form-actions {
  display: flex; justify-content: flex-end; gap: 1rem;
}
.form-actions button {
  padding: 10px 20px; border-radius: 5px; border: none; cursor: pointer;
}
.btn-cancel {
  background-color: #6c757d; color: white;
}
.btn-confirm-delete {
  background-color: #dc3545; color: white;
}
</style>
