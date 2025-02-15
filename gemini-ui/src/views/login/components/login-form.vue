<script setup>
import { useAuthStore } from '@/store'
import { _localStorage, jscrypt } from '@/utils'
import { useRouter } from 'vue-router'

const authStore = useAuthStore()
const router = useRouter()
const PHONE_KEY = 'login-phone'
const PWD_KEY = 'login-pwd'
const REM_KEY = 'login-rem'
const EXPIRE_MS = 30 * 24 * 60 * 60 * 1000

const loginRef = ref()
const title = import.meta.env.VITE_APP_TITLE
const loginForm = ref({
  phone: '',
  password: '',
  rememberMe: true
})
const loading = ref(false)
const loginRules = {
  phone: [{ required: true, trigger: 'blur', message: '请输入手机号' }],
  password: [{ required: true, trigger: 'blur', message: '请输入密码' }]
}

function handleLogin() {
  loginRef.value.validate(valid => {
    if (valid) {
      loading.value = true
      // 记住我
      if (loginForm.value.rememberMe) {
        _localStorage.setItem(PHONE_KEY, loginForm.value.phone, EXPIRE_MS)
        _localStorage.setItem(PWD_KEY, jscrypt.encrypt(loginForm.value.password), EXPIRE_MS)
        _localStorage.setItem(REM_KEY, loginForm.value.rememberMe, EXPIRE_MS)
      } else {
        _localStorage.removeItem(PHONE_KEY)
        _localStorage.removeItem(PWD_KEY)
        _localStorage.removeItem(REM_KEY)
      }
      // 登录
      authStore
        .login(loginForm.value)
        .then(() => {
          ElMessage.success('登录成功')
          router.push({ path: '/', replace: true })
        })
        .catch(() => {
          loading.value = false
        })
    }
  })
}

function initLoginInfo() {
  const loginPhone = _localStorage.getItem(PHONE_KEY)
  const loginPwd = _localStorage.getItem(PWD_KEY)
  const loginRem = _localStorage.getItem(REM_KEY)
  loginForm.value = {
    phone: loginPhone === null ? loginForm.value.phone : loginPhone,
    password: loginPwd === null ? loginForm.value.password : jscrypt.decrypt(loginPwd),
    rememberMe: loginRem === null ? false : Boolean(loginRem)
  }
}

;(() => {
  initLoginInfo()
})()
</script>

<template>
  <div flex justify-center items-center h-full>
    <el-form ref="loginRef" :model="loginForm" :rules="loginRules" w-full p-60>
      <h2 text-center mb-20 text-20>{{ title }}</h2>
      <el-form-item prop="phone">
        <el-input v-model="loginForm.phone" autocomplete="off" placeholder="手机号">
          <template #prefix>
            <i-bi:person-circle />
          </template>
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input v-model="loginForm.password" type="password" placeholder="密码">
          <template #prefix>
            <i-bi:shield-lock />
          </template>
        </el-input>
      </el-form-item>
      <el-tooltip content="保存账号密码30天" placement="right" effect="light">
        <el-checkbox v-model="loginForm.rememberMe">记住密码</el-checkbox>
      </el-tooltip>
      <el-form-item>
        <el-button w-full type="primary" :loading="loading" @click="handleLogin"> 登 录 </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<style lang="scss" scoped></style>
