<template>
  <div class="app-container">
    <!-- 登录和注册页面不显示导航 -->
    <template v-if="!isAuthPage">
      <el-container style="height: 100vh;">
        <!-- 侧边栏 -->
        <el-aside width="200px" style="background-color: #303133;">
          <div class="logo" style="height: 60px; line-height: 60px; text-align: center; color: white; font-size: 18px;">
            旧书交易管理系统
          </div>
          <el-menu
            :default-active="activeMenu"
            class="el-menu-vertical-demo"
            background-color="#303133"
            text-color="#fff"
            active-text-color="#409EFF"
            router
          >
            <el-menu-item index="/dashboard">
              <el-icon><PieChart /></el-icon>
              <span>数据概览</span>
            </el-menu-item>
            <el-menu-item index="/user">
              <el-icon><User /></el-icon>
              <span>用户管理</span>
            </el-menu-item>
            <el-menu-item index="/book/audit">
              <el-icon><Document /></el-icon>
              <span>书籍审核</span>
            </el-menu-item>
            <el-menu-item index="/order">
              <el-icon><ShoppingCart /></el-icon>
              <span>订单管理</span>
            </el-menu-item>
            <el-menu-item index="/category">
              <el-icon><CollectionTag /></el-icon>
              <span>分类管理</span>
            </el-menu-item>
          </el-menu>
        </el-aside>
        
        <!-- 主内容区域 -->
        <el-container>
          <!-- 顶部导航栏 -->
          <el-header style="background-color: #fff; border-bottom: 1px solid #e4e7ed; display: flex; justify-content: space-between; align-items: center;">
            <div>
              <el-button type="text" @click="toggleSideBar">
                <el-icon><Menu /></el-icon>
              </el-button>
            </div>
            <div style="display: flex; align-items: center; gap: 16px;">
              <el-dropdown>
                <span class="el-dropdown-link">
                  {{ username }} <el-icon class="el-icon--right"><ArrowDown /></el-icon>
                </span>
                <template #dropdown>
                  <el-dropdown-menu>
                    <el-dropdown-item @click="handleProfile">个人中心</el-dropdown-item>
                    <el-dropdown-item @click="handleLogout">退出登录</el-dropdown-item>
                  </el-dropdown-menu>
                </template>
              </el-dropdown>
            </div>
          </el-header>
          
          <!-- 内容区域 -->
          <el-main>
            <router-view />
          </el-main>
        </el-container>
      </el-container>
    </template>
    
    <!-- 登录和注册页面直接显示内容 -->
    <template v-else>
      <router-view />
    </template>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { PieChart, User, Document, ShoppingCart, CollectionTag, Menu, ArrowDown } from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()
const isSideBarCollapse = ref(false)
const username = ref('管理员')

const activeMenu = computed(() => {
  return route.path
})

const isAuthPage = computed(() => {
  const authPaths = ['/login', '/register']
  return authPaths.includes(route.path)
})

const toggleSideBar = () => {
  isSideBarCollapse.value = !isSideBarCollapse.value
}

const handleProfile = () => {
  // 个人中心功能
  console.log('个人中心')
  // 这里可以跳转到个人中心页面
}

const handleLogout = () => {
  // 退出登录功能
  localStorage.removeItem('isLoggedIn')
  localStorage.removeItem('username')
  router.push('/login')
}

// 初始化时获取用户名
onMounted(() => {
  const savedUsername = localStorage.getItem('username')
  if (savedUsername) {
    username.value = savedUsername
  }
})
</script>

<style scoped>
.app-container {
  height: 100vh;
  overflow: hidden;
}

.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
}
</style>
