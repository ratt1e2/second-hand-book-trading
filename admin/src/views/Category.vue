
<template>
  <div class="category-container">
    <el-card class="category-card">
      <template #header>
        <div class="card-header">
          <span>分类管理</span>
          <el-button type="primary" @click="openAddDialog">添加分类</el-button>
        </div>
      </template>
      
      <!-- 分类列表 -->
      <div class="table-container">
        <el-table :data="categoryList" style="width: 100%">
          <el-table-column prop="id" label="分类ID" width="80"></el-table-column>
          <el-table-column prop="name" label="分类名称"></el-table-column>
          <el-table-column prop="parentId" label="父分类ID" width="100">
            <template #default="scope">
              {{ scope.row.parentId === 0 ? '一级分类' : scope.row.parentId }}
            </template>
          </el-table-column>
          <el-table-column prop="sort" label="排序" width="80"></el-table-column>
          <el-table-column prop="status" label="状态" width="100">
            <template #default="scope">
              <el-tag :type="scope.row.status === 1 ? 'success' : 'danger'">
                {{ scope.row.status === 1 ? '启用' : '禁用' }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="createdAt" label="创建时间" width="180">
            <template #default="scope">
              {{ formatDate(scope.row.createdAt) }}
            </template>
          </el-table-column>
          <el-table-column label="操作" width="200">
            <template #default="scope">
              <el-button type="primary" size="small" @click="openEditDialog(scope.row)">编辑</el-button>
              <el-button
                :type="scope.row.status === 1 ? 'danger' : 'success'"
                size="small"
                @click="toggleStatus(scope.row)"
              >
                {{ scope.row.status === 1 ? '禁用' : '启用' }}
              </el-button>
              <el-button type="danger" size="small" @click="handleDeleteCategory(scope.row.id)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-card>
    
    <!-- 添加/编辑分类对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogType === 'add' ? '添加分类' : '编辑分类'"
      width="500px"
    >
      <el-form :model="formData" label-width="80px">
        <el-form-item label="分类名称" required>
          <el-input v-model="formData.name" placeholder="请输入分类名称"></el-input>
        </el-form-item>
        <el-form-item label="父分类">
          <el-select v-model="formData.parentId" placeholder="请选择父分类">
            <el-option label="一级分类" value="0"></el-option>
            <el-option
              v-for="category in parentCategoryList"
              :key="category.id"
              :label="category.name"
              :value="category.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="排序">
          <el-input-number v-model="formData.sort" :min="0" :max="9999"></el-input-number>
        </el-form-item>
        <el-form-item label="状态">
          <el-switch v-model="formData.status" :active-value="1" :inactive-value="0"></el-switch>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSubmit">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue';
import dayjs from 'dayjs';
import { getCategoryList, addCategory, updateCategory, deleteCategory, updateCategoryStatus } from '@/api/category';

const categoryList = ref([]);

const parentCategoryList = computed(() => {
  return categoryList.value.filter(item => item.parentId === 0);
});

const dialogVisible = ref(false);
const dialogType = ref('add');

const formData = reactive({
  id: '',
  name: '',
  parentId: 0,
  sort: 0,
  status: 1,
});

const formatDate = (date) => {
  return dayjs(date).format('YYYY-MM-DD HH:mm:ss');
};

const fetchCategoryList = async () => {
  const res = await getCategoryList();
  categoryList.value = res.data;
};

const openAddDialog = () => {
  dialogType.value = 'add';
  formData.id = '';
  formData.name = '';
  formData.parentId = 0;
  formData.sort = 0;
  formData.status = 1;
  dialogVisible.value = true;
};

const openEditDialog = (category) => {
  dialogType.value = 'edit';
  formData.id = category.id;
  formData.name = category.name;
  formData.parentId = category.parentId;
  formData.sort = category.sort;
  formData.status = category.status;
  dialogVisible.value = true;
};

const toggleStatus = async (category) => {
  await updateCategoryStatus({ categoryId: category.id, status: category.status === 1 ? 0 : 1 });
  fetchCategoryList();
};

const handleDeleteCategory = async (categoryId) => {
  await deleteCategory({ categoryId });
  fetchCategoryList();
};

const handleSubmit = async () => {
  if (dialogType.value === 'add') {
    await addCategory(formData);
  } else {
    await updateCategory(formData);
  }
  dialogVisible.value = false;
  fetchCategoryList();
};

onMounted(() => {
  fetchCategoryList();
});
</script>

<style scoped>
.category-container {
  padding: 20px;
}

.category-card {
  margin-bottom: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.table-container {
  margin-top: 20px;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}
</style>
