<script setup>import { ref, inject } from 'vue';
import { ElMessage } from 'element-plus';
import { loginapi, addloginapi } from '@/api/loginapi.js';
import { useRouter } from 'vue-router';
import { ElLoading } from 'element-plus'
import "@/assets/login.css";
const router = useRouter();
const isLoginMode = ref(true);
const options = {
  lock: true,
  text: 'Loading',
  background: 'rgba(0, 0, 0, 0.7)',
}
const formData = ref({
  username: '',
  email: '',
  password: '',
  confirmPassword: ''
});
const loading = ref(false);
const switchMode = () => {
  isLoginMode.value = !isLoginMode.value;
  formData.value = {
    username: '',
    email: '',
    password: '',
    confirmPassword: ''
  };
};
const validateForm = () => {
  if (isLoginMode.value) {
    if (!formData.value.username) {
      ElMessage.error('请输入用户名');
      return false;
    }
    if (!formData.value.password) {
      ElMessage.error('请输入密码');
      return false;
    }
  }
  else {
    if (!formData.value.username) {
      ElMessage.error('请输入用户名');
      return false;
    }
    if (!formData.value.email) {
      ElMessage.error('请输入邮箱');
      return false;
    }
    if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(formData.value.email)) {
      ElMessage.error('请输入有效的邮箱地址');
      return false;
    }
    if (!formData.value.password) {
      ElMessage.error('请输入密码');
      return false;
    }
    if (formData.value.password.length < 6) {
      ElMessage.error('密码长度至少6位');
      return false;
    }
    if (formData.value.password !== formData.value.confirmPassword) {
      ElMessage.error('两次输入的密码不一致');
      return false;
    }
  }
  return true;
};
const handleSubmit = async () => {
  if (!validateForm())
    return;
  loading.value = true;
  const loadingInstance = ElLoading.service(options);
  try {
    if (isLoginMode.value) {

      const response = await loginapi({
        username: formData.value.username,
        password: formData.value.password
      });
      console.log(response);
      if (response.code==1) {
      const  savetime= Date.now();
      localStorage.setItem("savetime",savetime);
        localStorage.setItem('token', response.data.token);
        ElMessage.success('登录成功');
        loadingInstance.close();
        router.push('/index');
      }


    }
    else {

      const response = await addloginapi({
        username: formData.value.username,
        email: formData.value.email,
        password: formData.value.password
      });

      if(response.code==1){
      loadingInstance.close();
      ElMessage.success('注册成功');

      switchMode();
      }
      else if(response.code==0){
        loadingInstance.close();
        ElMessage(response.mes+"重复");
      }
    }
  }
  catch (error) {

    setTimeout(() => {

      loadingInstance.close();  
      ElMessage.error(isLoginMode.value ? '登录失败，请检查用户名或密码' : '注册失败，请稍后重试');
    }, 3000);
  
    console.error(error);
  }
  finally {
    loading.value = false;
  }
};
</script>

<template>

  <div class="login-page">
    <div class="login-container">
      <div class="form-card">
        <div class="card-header">
          <h1>{{ isLoginMode ? '欢迎回来' : '创建账号' }}</h1>
          <p>{{ isLoginMode ? '请登录您的账户' : '开始您的旅程' }}</p>
        </div>


        <form @submit.prevent="handleSubmit" class="login-form">
          <div v-if="!isLoginMode" class="form-group">
            <label for="username">用户名</label>
            <input v-model="formData.username" type="text" id="username" placeholder="请输入用户名" class="form-input" />
          </div>

          <div v-if="isLoginMode" class="form-group">
            <label for="login-username">用户名</label>
            <input v-model="formData.username" type="text" id="login-username" placeholder="请输入用户名"
              class="form-input" />
          </div>

          <div v-if="!isLoginMode" class="form-group">
            <label for="register-email">邮箱</label>
            <input v-model="formData.email" type="email" id="register-email" placeholder="请输入邮箱" class="form-input" />
          </div>

          <div class="form-group">
            <label for="password">密码</label>
            <input v-model="formData.password" type="password" id="password" placeholder="请输入密码" class="form-input" />
          </div>

          <div v-if="!isLoginMode" class="form-group">
            <label for="confirmPassword">确认密码</label>
            <input v-model="formData.confirmPassword" type="password" id="confirmPassword" placeholder="请再次输入密码"
              class="form-input" />
          </div>

          <div v-if="isLoginMode" class="form-group remember-me">
            <label class="checkbox-label">
              <input type="checkbox" />
              <span>记住我</span>
            </label>
            <a href="#" class="forgot-password">忘记密码？</a>
          </div>




          <button type="submit" class="submit-btn" :disabled="loading">
            <span v-if="loading" class="loading">加载中...</span>
            <span v-else>{{ isLoginMode ? '登录' : '创建账号' }}</span>
          </button>
        </form>

        <div class="form-footer">
          <p>
            {{ isLoginMode ? '还没有账号？' : '已有账号？' }}
            <button type="button" class="switch-btn" @click="switchMode">
              {{ isLoginMode ? '立即注册' : '立即登录' }}
            </button>
          </p>
        </div>

        <div class="social-divider">
          <span class="divider-text">或</span>
        </div>

        <div class="social-login">
          <button class="social-btn google">G</button>
          <button class="social-btn facebook">F</button>
          <button class="social-btn twitter">X</button>
        </div>
      </div>

      <div class="welcome-card">
        <div class="welcome-content">
          <h2>探索无限可能</h2>
          <p>加入我们，开启全新体验</p>
          <button class="explore-btn" @click="switchMode" v-if="isLoginMode">
            创建新账号
          </button>
          <button class="explore-btn" @click="switchMode" v-else>
            登录已有账号
          </button>
        </div>
        <div class="decorative-shapes">
          <div class="shape shape-1"></div>
          <div class="shape shape-2"></div>
          <div class="shape shape-3"></div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>