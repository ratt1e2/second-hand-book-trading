<template>
  <div class="user-container">
    <el-card class="user-card">
      <template #header>
        <div class="card-header">
          <span>用户管理</span>
        </div>
      </template>
      
      <!-- 搜索和筛选 -->
      <div class="search-container">
        <el-form :inline="true" :model="searchForm" class="search-form">
          <el-form-item label="手机号">
            <el-input v-model="searchForm.phone" placeholder="请输入手机号" style="width: 200px;"></el-input>
          </el-form-item>
          <el-form-item label="昵称">
            <el-input v-model="searchForm.nickname" placeholder="请输入昵称" style="width: 200px;"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleSearch">搜索</el-button>
            <el-button @click="resetForm">重置</el-button>
          </el-form-item>
        </el-form>
      </div>
      
      <!-- 用户列表 -->
      <div class="table-container">
        <el-table :data="userList" style="width: 100%">
          <el-table-column prop="id" label="用户ID" width="80"></el-table-column>
          <el-table-column label="头像" width="80">
            <template #default="scope">
              <el-avatar :size="40" :src="scope.row.avatar || ''">
                {{ scope.row.nickname?.charAt(0) || '用' }}
              </el-avatar>
            </template>
          </el-table-column>
          <el-table-column prop="nickname" label="昵称"></el-table-column>
          <el-table-column prop="phone" label="手机号"></el-table-column>
          <el-table-column prop="role" label="角色" width="100">
            <template #default="scope">
              <el-tag :type="scope.row.role === 1 ? 'primary' : 'info'">
                {{ scope.row.role === 1 ? '管理员' : '普通用户' }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="status" label="状态" width="100">
            <template #default="scope">
              <el-tag :type="scope.row.status === 1 ? 'success' : 'danger'">
                {{ scope.row.status === 1 ? '正常' : '禁用' }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="createdAt" label="注册时间" width="180">
            <template #default="scope">
              {{ formatDate(scope.row.createdAt) }}
            </template>
          </el-table-column>
          <el-table-column label="操作" width="150">
            <template #default="scope">
              <el-button
                type="text"
                :type="scope.row.status === 1 ? 'danger' : 'success'"
                @click="toggleStatus(scope.row)"
              >
                {{ scope.row.status === 1 ? '禁用' : '解禁' }}
              </el-button>
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
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import dayjs from 'dayjs'

// 搜索表单
const searchForm = reactive({
  phone: '',
  nickname: ''
})

// 分页参数
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(100)

// 用户列表数据
const userList = ref([
  {
    id: 1,
    nickname: '管理员',
    phone: '13800138000',
    avatar: '',
    role: 1,
    status: 1,
    createdAt: '2024-01-01 00:00:00'
  },
  {
    id: 2,
    nickname: '张三',
    phone: '13800138001',
    avatar: '',
    role: 0,
    status: 1,
    createdAt: '2024-01-02 12:00:00'
  },
  {
    id: 3,
    nickname: '李四',
    phone: '13800138002',
    avatar: '',
    role: 0,
    status: 0,
    createdAt: '2024-01-03 18:00:00'
  }
])

// 格式化日期
const formatDate = (date) => {
  return dayjs(date).format('YYYY-MM-DD HH:mm:ss')
}

// 搜索
const handleSearch = () => {
  // 这里需要调用后端API获取数据
  console.log('搜索条件:', searchForm)
}

// 重置表单
const resetForm = () => {
  searchForm.phone = ''
  searchForm.nickname = ''
  handleSearch()
}

// 切换用户状态
const toggleStatus = (user) => {
  // 这里需要调用后端API更新用户状态
  user.status = user.status === 1 ? 0 : 1
  console.log('切换用户状态:', user.id, user.status)
}

// 分页处理
const handleSizeChange = (size) => {
  pageSize.value = size
  handleSearch()
}

const handleCurrentChange = (current) => {
  currentPage.value = current
  handleSearch()
}

// 初始化数据
onMounted(() => {
  handleSearch()
})
</script>

<style scoped>
.user-container {
  padding: 20px;
}

.user-card {
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
</style>
