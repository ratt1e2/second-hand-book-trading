
<template>
  <div class="order-container">
    <el-card class="order-card">
      <template #header>
        <div class="card-header">
          <span>订单管理</span>
        </div>
      </template>
      
      <!-- 搜索和筛选 -->
      <div class="search-container">
        <el-form :inline="true" :model="searchForm" class="search-form">
          <el-form-item label="订单号">
            <el-input v-model="searchForm.orderNo" placeholder="请输入订单号" style="width: 200px;"></el-input>
          </el-form-item>
          <el-form-item label="买家手机号">
            <el-input v-model="searchForm.buyerPhone" placeholder="请输入买家手机号" style="width: 200px;"></el-input>
          </el-form-item>
          <el-form-item label="订单状态">
            <el-select v-model="searchForm.status" placeholder="请选择订单状态" style="width: 150px;">
              <el-option label="全部" value=""></el-option>
              <el-option label="待支付" value="0"></el-option>
              <el-option label="待发货" value="1"></el-option>
              <el-option label="待收货" value="2"></el-option>
              <el-option label="已完成" value="3"></el-option>
              <el-option label="已取消" value="4"></el-option>
              <el-option label="退款中" value="5"></el-option>
              <el-option label="已退款" value="6"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleSearch">搜索</el-button>
            <el-button @click="resetForm">重置</el-button>
          </el-form-item>
        </el-form>
      </div>
      
      <!-- 订单列表 -->
      <div class="table-container">
        <el-table :data="orderList" style="width: 100%">
          <el-table-column prop="orderNo" label="订单号" width="200"></el-table-column>
          <el-table-column prop="userId" label="买家ID" width="100"></el-table-column>
          <el-table-column prop="sellerId" label="卖家ID" width="100"></el-table-column>
          <el-table-column prop="totalAmount" label="总金额" width="120">
            <template #default="scope">
              ¥{{ scope.row.totalAmount }}
            </template>
          </el-table-column>
          <el-table-column prop="actualAmount" label="实付金额" width="120">
            <template #default="scope">
              ¥{{ scope.row.actualAmount }}
            </template>
          </el-table-column>
          <el-table-column prop="status" label="订单状态" width="120">
            <template #default="scope">
              <el-tag :type="getStatusType(scope.row.status)">
                {{ getStatusText(scope.row.status) }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="createdAt" label="创建时间" width="180">
            <template #default="scope">
              {{ formatDate(scope.row.createdAt) }}
            </template>
          </el-table-column>
          <el-table-column label="操作" width="150">
            <template #default="scope">
              <el-button type="primary" size="small" @click="viewDetail(scope.row)">查看详情</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      
      <!-- 分页 -->
      <div class="pagination-container">
        <el-pagination
          v-model:current-page="currentPage"
          v-model:page-size="pageSize"
          :page-sizes="[10, 20, 50, 100]"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-card>
    
    <!-- 订单详情对话框 -->
    <el-dialog
      v-model="dialogVisible"
      title="订单详情"
      width="800px"
    >
      <div v-if="currentOrder" class="order-detail">
        <div class="order-detail-header">
          <h3>订单信息</h3>
          <el-tag :type="getStatusType(currentOrder.status)">
            {{ getStatusText(currentOrder.status) }}
          </el-tag>
        </div>
        <div class="order-detail-info">
          <p><strong>订单号：</strong>{{ currentOrder.orderNo }}</p>
          <p><strong>买家ID：</strong>{{ currentOrder.userId }}</p>
          <p><strong>卖家ID：</strong>{{ currentOrder.sellerId }}</p>
          <p><strong>总金额：</strong>¥{{ currentOrder.totalAmount }}</p>
          <p><strong>实付金额：</strong>¥{{ currentOrder.actualAmount }}</p>
          <p><strong>创建时间：</strong>{{ formatDate(currentOrder.createdAt) }}</p>
          <p v-if="currentOrder.paymentTime"><strong>支付时间：</strong>{{ formatDate(currentOrder.paymentTime) }}</p>
          <p v-if="currentOrder.shippingTime"><strong>发货时间：</strong>{{ formatDate(currentOrder.shippingTime) }}</p>
          <p v-if="currentOrder.confirmTime"><strong>确认收货时间：</strong>{{ formatDate(currentOrder.confirmTime) }}</p>
          <p v-if="currentOrder.cancelTime"><strong>取消时间：</strong>{{ formatDate(currentOrder.cancelTime) }}</p>
          <p v-if="currentOrder.remark"><strong>备注：</strong>{{ currentOrder.remark }}</p>
        </div>
        <div class="order-detail-content">
          <h4>订单商品</h4>
          <el-table :data="currentOrder.items" style="width: 100%">
            <el-table-column label="商品信息">
              <template #default="scope">
                <div style="display: flex; align-items: center;">
                  <el-image
                    :src="scope.row.bookCover || 'https://via.placeholder.com/80x120'"
                    fit="cover"
                    style="width: 60px; height: 90px; margin-right: 12px;"
                  />
                  <div>
                    <p style="font-weight: bold;">{{ scope.row.bookTitle }}</p>
                    <p style="color: #606266;">{{ scope.row.bookAuthor }}</p>
                  </div>
                </div>
              </template>
            </el-table-column>
            <el-table-column prop="price" label="单价" width="100">
              <template #default="scope">
                ¥{{ scope.row.price }}
              </template>
            </el-table-column>
            <el-table-column prop="quantity" label="数量" width="80"></el-table-column>
            <el-table-column label="小计" width="100">
              <template #default="scope">
                ¥{{ (scope.row.price * scope.row.quantity).toFixed(2) }}
              </template>
            </el-table-column>
          </el-table>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue';
import dayjs from 'dayjs';
import { getOrderList, getOrderDetail } from '@/api/order';

const searchForm = reactive({
  orderNo: '',
  buyerPhone: '',
  status: ''
});

const currentPage = ref(1);
const pageSize = ref(10);
const total = ref(0);

const orderList = ref([]);

const dialogVisible = ref(false);
const currentOrder = ref(null);

const getStatusText = (status) => {
  const statusMap = {
    0: '待支付',
    1: '待发货',
    2: '待收货',
    3: '已完成',
    4: '已取消',
    5: '退款中',
    6: '已退款'
  };
  return statusMap[status] || '未知';
};

const getStatusType = (status) => {
  const typeMap = {
    0: 'warning',
    1: 'info',
    2: 'primary',
    3: 'success',
    4: 'danger',
    5: 'warning',
    6: 'danger'
  };
  return typeMap[status] || 'info';
};

const formatDate = (date) => {
  return dayjs(date).format('YYYY-MM-DD HH:mm:ss');
};

const fetchOrderList = async () => {
  const res = await getOrderList({
    page: currentPage.value,
    size: pageSize.value,
    ...searchForm
  });
  orderList.value = res.data.records;
  total.value = res.data.total;
};

const handleSearch = () => {
  fetchOrderList();
};

const resetForm = () => {
  searchForm.orderNo = '';
  searchForm.buyerPhone = '';
  searchForm.status = '';
  fetchOrderList();
};

const viewDetail = async (order) => {
  const res = await getOrderDetail({ orderId: order.id });
  currentOrder.value = res.data;
  dialogVisible.value = true;
};

const handleSizeChange = (size) => {
  pageSize.value = size;
  fetchOrderList();
};

const handleCurrentChange = (current) => {
  currentPage.value = current;
  fetchOrderList();
};

onMounted(() => {
  fetchOrderList();
});
</script>

<style scoped>
.order-container {
  padding: 20px;
}

.order-card {
  margin-bottom: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.search-container {
  margin-bottom: 20px;
}

.search-form {
  display: flex;
  align-items: center;
  gap: 16px;
}

.table-container {
  margin-bottom: 20px;
}

.pagination-container {
  display: flex;
  justify-content: flex-end;
}

.order-detail {
  padding: 20px;
}

.order-detail-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding-bottom: 10px;
  border-bottom: 1px solid #e4e7ed;
}

.order-detail-header h3 {
  margin: 0;
  color: #303133;
}

.order-detail-info {
  margin-bottom: 20px;
  padding: 10px;
  background-color: #f9fafc;
  border-radius: 4px;
}

.order-detail-info p {
  margin: 8px 0;
  color: #606266;
}

.order-detail-content {
  border-top: 1px solid #e4e7ed;
  padding-top: 20px;
}

.order-detail-content h4 {
  margin-bottom: 16px;
  color: #303133;
}
</style>
