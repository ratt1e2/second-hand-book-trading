<template>
  <div class="book-audit-container">
    <el-card class="book-audit-card">
      <template #header>
        <div class="card-header">
          <span>书籍审核</span>
        </div>
      </template>
      
      <!-- 搜索和筛选 -->
      <div class="search-container">
        <el-form :inline="true" :model="searchForm" class="search-form">
          <el-form-item label="书名">
            <el-input v-model="searchForm.title" placeholder="请输入书名" style="width: 200px;"></el-input>
          </el-form-item>
          <el-form-item label="作者">
            <el-input v-model="searchForm.author" placeholder="请输入作者" style="width: 200px;"></el-input>
          </el-form-item>
          <el-form-item label="ISBN">
            <el-input v-model="searchForm.isbn" placeholder="请输入ISBN" style="width: 200px;"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleSearch">搜索</el-button>
            <el-button @click="resetForm">重置</el-button>
          </el-form-item>
        </el-form>
      </div>
      
      <!-- 书籍列表 -->
      <div class="table-container">
        <el-table :data="bookList" style="width: 100%">
          <el-table-column label="封面" width="100">
            <template #default="scope">
              <el-image
                :src="scope.row.coverImage || 'https://via.placeholder.com/80x120'"
                fit="cover"
                style="width: 80px; height: 120px;"
              />
            </template>
          </el-table-column>
          <el-table-column prop="title" label="书名"></el-table-column>
          <el-table-column prop="author" label="作者" width="120"></el-table-column>
          <el-table-column prop="isbn" label="ISBN" width="150"></el-table-column>
          <el-table-column prop="price" label="价格" width="100">
            <template #default="scope">
              ¥{{ scope.row.price }}
            </template>
          </el-table-column>
          <el-table-column prop="condition" label="新旧程度" width="120">
            <template #default="scope">
              <el-tag>
                {{ getConditionText(scope.row.condition) }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="createdAt" label="发布时间" width="180">
            <template #default="scope">
              {{ formatDate(scope.row.createdAt) }}
            </template>
          </el-table-column>
          <el-table-column label="操作" width="240">
            <template #default="scope">
              <el-button type="primary" size="small" @click="auditPass(scope.row)" style="margin-right: 8px;">通过</el-button>
              <el-button type="danger" size="small" @click="auditReject(scope.row)" style="margin-right: 8px;">驳回</el-button>
              <el-button type="info" size="small" @click="viewDetail(scope.row)">查看详情</el-button>
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
    
    <!-- 书籍详情对话框 -->
    <el-dialog
      v-model="dialogVisible"
      title="书籍详情"
      width="800px"
    >
      <div v-if="currentBook" class="book-detail">
        <div class="book-detail-header">
          <el-image
            :src="currentBook.coverImage || 'https://via.placeholder.com/120x180'"
            fit="cover"
            style="width: 120px; height: 180px; margin-right: 20px;"
          />
          <div class="book-detail-info">
            <h3>{{ currentBook.title }}</h3>
            <p>作者：{{ currentBook.author }}</p>
            <p>ISBN：{{ currentBook.isbn }}</p>
            <p>价格：¥{{ currentBook.price }}</p>
            <p>原价：¥{{ currentBook.originalPrice }}</p>
            <p>新旧程度：{{ getConditionText(currentBook.condition) }}</p>
            <p>发布时间：{{ formatDate(currentBook.createdAt) }}</p>
          </div>
        </div>
        <div class="book-detail-content">
          <h4>书籍描述</h4>
          <p>{{ currentBook.description }}</p>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import dayjs from 'dayjs'

// 搜索表单
const searchForm = reactive({
  title: '',
  author: '',
  isbn: ''
})

// 分页参数
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(100)

// 书籍列表数据
const bookList = ref([
  {
    id: 1,
    title: 'Java核心技术',
    author: 'Cay S. Horstmann',
    isbn: '9787111612908',
    price: 89.9,
    originalPrice: 128,
    condition: 1,
    coverImage: '',
    description: 'Java核心技术，全面介绍Java语言的基础知识和高级特性',
    createdAt: '2024-01-01 10:00:00'
  },
  {
    id: 2,
    title: 'JavaScript高级程序设计',
    author: 'Matt Frisbie',
    isbn: '9787115524229',
    price: 79.9,
    originalPrice: 109,
    condition: 2,
    coverImage: '',
    description: 'JavaScript高级程序设计，深入理解JavaScript的核心概念和应用',
    createdAt: '2024-01-02 11:00:00'
  }
])

// 对话框状态
const dialogVisible = ref(false)
const currentBook = ref(null)

// 获取新旧程度文本
const getConditionText = (condition) => {
  const conditionMap = {
    1: '九成新',
    2: '八成新',
    3: '七成新',
    4: '六成新',
    5: '五成新及以下'
  }
  return conditionMap[condition] || '未知'
}

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
  searchForm.title = ''
  searchForm.author = ''
  searchForm.isbn = ''
  handleSearch()
}

// 审核通过
const auditPass = (book) => {
  // 这里需要调用后端API更新书籍状态
  console.log('审核通过:', book.id)
  // 从列表中移除
  const index = bookList.value.findIndex(item => item.id === book.id)
  if (index !== -1) {
    bookList.value.splice(index, 1)
  }
}

// 审核驳回
const auditReject = (book) => {
  // 这里需要调用后端API更新书籍状态
  console.log('审核驳回:', book.id)
  // 从列表中移除
  const index = bookList.value.findIndex(item => item.id === book.id)
  if (index !== -1) {
    bookList.value.splice(index, 1)
  }
}

// 查看详情
const viewDetail = (book) => {
  currentBook.value = book
  dialogVisible.value = true
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
.book-audit-container {
  padding: 20px;
}

.book-audit-card {
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

.book-detail {
  padding: 20px;
}

.book-detail-header {
  display: flex;
  margin-bottom: 20px;
}

.book-detail-info {
  flex: 1;
}

.book-detail-info h3 {
  margin-bottom: 16px;
  color: #303133;
}

.book-detail-info p {
  margin-bottom: 8px;
  color: #606266;
}

.book-detail-content {
  border-top: 1px solid #e4e7ed;
  padding-top: 20px;
}

.book-detail-content h4 {
  margin-bottom: 12px;
  color: #303133;
}

.book-detail-content p {
  line-height: 1.6;
  color: #606266;
}
</style>
