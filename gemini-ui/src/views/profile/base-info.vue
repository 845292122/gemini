<script setup>
import { useAuthStore } from '@/store'
import { cloneDeep } from 'lodash'

const authStore = useAuthStore()
const { info } = authStore
const infoForm = ref({
  phone: undefined
})
function initInfo() {
  infoForm.value = cloneDeep(info)
}
initInfo()
</script>

<template>
  <div px-5 pt-1>
    <span text-5 font-bold>基础信息</span>
    <el-form :model="infoForm" label-position="top" mt-5 w-md>
      <el-form-item label="手机号" prop="phone">
        <el-input v-model="infoForm.phone" />
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input type="textarea" :rows="1" v-model="infoForm.remark" />
      </el-form-item>
      <el-form-item label="联系人" prop="contact">
        <el-input v-model="infoForm.contact" />
      </el-form-item>
      <el-form-item label="店铺名称" prop="merchantName">
        <el-input v-model="infoForm.merchantName" />
      </el-form-item>
    </el-form>
    <el-button type="primary"> 更新信息 </el-button>
  </div>
</template>

<style lang="scss" scoped></style>
