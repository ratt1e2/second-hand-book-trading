
<template>
  <div class="register-container">
    <el-card class="register-card">
      <template #header>
        <div class="register-title">
          <h2>旧书交易管理系统</h2>
          <p>管理员注册</p>
        </div>
      </template>
      
      <el-form :model="registerForm" :rules="rules" ref="registerFormRef" class="register-form">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="registerForm.username" placeholder="请输入用户名" prefix-icon="User"></el-input>
        </el-form-item>
        
        <el-form-item label="密码" prop="password">
          <el-input v-model="registerForm.password" type="password" placeholder="请输入密码" prefix-icon="Lock" show-password></el-input>
        </el-form-item>
        
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input v-model="registerForm.confirmPassword" type="password" placeholder="请确认密码" prefix-icon="Check" show-password></el-input>
        </el-form-item>
        
        <el-form-item>
          <el-button type="primary" class="register-button" @click="handleRegister" :loading="loading">注册</el-button>
        </el-form-item>
        
        <div class="login-link">
          <p>已有账号？ <router-link to="/login">立即登录</router-link></p>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue';
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import { register } from '@/api/user';

const router = useRouter();
const registerFormRef = ref(null);
const loading = ref(false);

const registerForm = reactive({
  username: '',
  password: '',
  confirmPassword: ''
});

const validatePass = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请输入密码'));
  } else {
    if (registerForm.confirmPassword !== '') {
      if (!registerFormRef.value) return;
      registerFormRef.value.validateField('confirmPassword');
    }
    callback();
  }
};
const validatePass2 = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请再次输入密码'));
  } else if (value !== registerForm.password) {
    callback(new Error("两次输入的密码不一致!"));
  } else {
    callback();
  }
};

const rules = reactive({
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
  ],
  password: [{ validator: validatePass, trigger: 'blur' }],
  confirmPassword: [{ validator: validatePass2, trigger: 'blur' }]
});

const handleRegister = async () => {
  if (!registerFormRef.value) return;
  await registerFormRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true;
      try {
        await register({ username: registerForm.username, password: registerForm.password });
        ElMessage.success('注册成功！即将跳转到登录页。');
        setTimeout(() => {
          router.push('/login');
        }, 2000);
      } catch (error) {
        console.error(error);
      } finally {
        loading.value = false;
      }
    }
  });
};
</script>

<style scoped>
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f5f7fa;
}

.register-card {
  width: 450px;
}

.register-title {
  text-align: center;
}

.register-title h2 {
  margin-bottom: 8px;
  color: #303133;
}

.register-title p {
  color: #909399;
  font-size: 14px;
}

.register-form {
  padding: 0 20px;
}

.register-button {
  width: 100%;
}

.login-link {
  margin-top: 16px;
  text-align: center;
  font-size: 14px;
  color: #606266;
}

.login-link a {
  color: #409eff;
  text-decoration: none;
}

/* 确保输入框宽度一致 */
.register-form .el-form-item .el-input {
    width: 100%;
}
</style>
