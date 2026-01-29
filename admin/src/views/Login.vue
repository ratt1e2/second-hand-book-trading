
<template>
  <div class="login-container">
    <el-card class="login-card">
      <template #header>
        <div class="login-title">
          <h2>旧书交易管理系统</h2>
          <p>管理员登录</p>
        </div>
      </template>
      
      <el-form :model="loginForm" :rules="rules" ref="loginFormRef" class="login-form">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="loginForm.username" placeholder="请输入用户名" prefix-icon="User"></el-input>
        </el-form-item>
        
        <el-form-item label="密码" prop="password">
          <el-input v-model="loginForm.password" type="password" placeholder="请输入密码" prefix-icon="Lock" show-password></el-input>
        </el-form-item>
        
        <el-form-item>
          <el-button type="primary" @click="handleLogin" :loading="loading" class="login-button">登录</el-button>
        </el-form-item>
        
        <div class="register-link">
          <p>还没有账号？ <router-link to="/register">立即注册</router-link></p>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue';
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import { login } from '@/api/user';

const router = useRouter();
const loginFormRef = ref(null);
const loading = ref(false);

const loginForm = reactive({
  username: '',
  password: ''
});

const rules = reactive({
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
});

const handleLogin = async () => {
  if (!loginFormRef.value) return;
  await loginFormRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true;
      try {
        const res = await login(loginForm);
        // 假设后端返回的数据在 res.data 中，包含 token 和用户信息
        localStorage.setItem('token', res.data.token); 
        localStorage.setItem('user', JSON.stringify(res.data.user));
        ElMessage.success('登录成功');
        router.push('/'); // 跳转到首页或仪表盘
      } catch (error) {
        // ElMessage.error('登录失败，请检查用户名和密码');
        console.error(error);
      } finally {
        loading.value = false;
      }
    }
  });
};
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f5f7fa;
}

.login-card {
  width: 450px;
}

.login-title {
  text-align: center;
}

.login-title h2 {
  margin-bottom: 8px;
  color: #303133;
}

.login-title p {
  color: #909399;
  font-size: 14px;
}

.login-form {
  padding: 0 20px;
}

.login-button {
  width: 100%;
}

.register-link {
  margin-top: 16px;
  text-align: center;
  font-size: 14px;
  color: #606266;
}

.register-link a {
  color: #409eff;
  text-decoration: none;
}
/* 确保输入框宽度一致 */
.login-form .el-form-item .el-input {
    width: 100%;
}
</style>
