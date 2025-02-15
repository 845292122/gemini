<script setup>
import { UserApi } from '@/api'

defineExpose({ openDialog })
const emit = defineEmits(['refresh'])

const dialogFlag = ref(false)
const formRef = ref()
const formModal = ref({})

function closeDialog() {
  dialogFlag.value = false
}

function openDialog(data) {
  formModal.value = data ?? { status: 1 }
  dialogFlag.value = true
}

async function onSubmit() {
  if (formModal.value.id) {
    await UserApi.modifyUser(formModal.value)
    ElMessage.success('修改成功')
  } else {
    await UserApi.createUser(formModal.value)
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
    <el-form :modal="formModal" ref="formRef" label-width="80px">
      <el-form-item prop="name" label="名称">
        <el-input v-model="formModal.name" placeholder="请输入用户名称" />
      </el-form-item>
      <el-form-item prop="phone" label="手机号">
        <el-input v-model="formModal.phone" placeholder="请输入手机号" />
      </el-form-item>
      <el-form-item prop="status" label="状态">
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
