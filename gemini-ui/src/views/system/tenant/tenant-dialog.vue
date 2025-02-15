<script setup>
import { TenantApi } from '@/api'

defineExpose({ openDialog })
const emit = defineEmits(['refresh'])

const dialogFlag = ref(false)
const formRef = ref()
const formModal = ref({
  id: undefined,
  name: undefined,
  contact: undefined,
  phone: undefined,
  company: undefined,
  licenseNumber: undefined,
  address: undefined,
  remark: undefined,
  isTrial: undefined,
  trialStartDate: undefined,
  trialEndDate: undefined,
  startDate: undefined,
  endDate: undefined,
  accountCount: undefined,
  status: 1
})

function closeDialog() {
  dialogFlag.value = false
}

function openDialog(data) {
  formModal.value = data ?? { status: 1 }
  dialogFlag.value = true
}

async function onSubmit() {
  if (formModal.value.id) {
    await TenantApi.modifyTenant(formModal.value)
    ElMessage.success('修改成功')
  } else {
    await TenantApi.createTenant(formModal.value)
    ElMessage.success('添加成功')
  }
  emit('refresh')
  closeDialog()
}
</script>

<template>
  <el-dialog
    v-model="dialogFlag"
    title="用户信息"
    width="450"
    append-to-body
    :close-on-clikc-modal="false"
    :close-on-press-escape="false"
    @closed="closeDialog"
  >
    <el-form :modal="formModal" ref="formRef" label-width="120px">
      <el-form-item prop="name" label="租户名称">
        <el-input v-model="formModal.name" placeholder="请输入租户名称" />
      </el-form-item>
      <el-form-item prop="contact" label="联系人">
        <el-input v-model="formModal.contact" placeholder="请输入联系人" />
      </el-form-item>
      <el-form-item prop="phone" label="手机号">
        <el-input v-model="formModal.phone" placeholder="请输入手机号" />
      </el-form-item>
      <el-form-item prop="company" label="公司名称">
        <el-input v-model="formModal.company" placeholder="请输入公司名称" />
      </el-form-item>
      <el-form-item prop="licenseNumber" label="社会信用代码">
        <el-input v-model="formModal.licenseNumber" placeholder="请输入社会信用代码" />
      </el-form-item>
      <el-form-item prop="address" label="地址">
        <el-input v-model="formModal.address" placeholder="请输入地址" />
      </el-form-item>
      <el-form-item prop="remark" label="备注">
        <el-input v-model="formModal.remark" placeholder="请输入备注" />
      </el-form-item>
      <el-form-item prop="isTrial" label="试用状态">
        <el-radio-group v-model="formModal.isTrial">
          <el-radio :value="1">是</el-radio>
          <el-radio :value="0">否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item prop="trialStartDate" label="试用开始时间">
        <el-date-picker v-model="formModal.trialStartDate" type="datetime" placeholder="请选择日期" />
      </el-form-item>
      <el-form-item prop="trialEndDate" label="试用结束时间">
        <el-date-picker v-model="formModal.trialEndDate" type="datetime" placeholder="请选择日期" />
      </el-form-item>
      <el-form-item prop="startDate" label="开始时间">
        <el-date-picker v-model="formModal.startDate" type="datetime" placeholder="请选择日期" />
      </el-form-item>
      <el-form-item prop="endDate" label="结束时间">
        <el-date-picker v-model="formModal.endDate" type="datetime" placeholder="请选择日期" />
      </el-form-item>
      <el-form-item prop="accountCount" label="账号数量限制">
        <el-input v-model="formModal.accountCount" placeholder="请输入账号数量限制" />
      </el-form-item>
      <el-form-item prop="status" label="租户状态">
        <el-radio-group v-model="formModal.status">
          <el-radio :value="1">启用</el-radio>
          <el-radio :value="0">禁用</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="closeDialog">取 消</el-button>
      <el-button type="primary" @click="onSubmit">确 定</el-button>
    </template>
  </el-dialog>
</template>
