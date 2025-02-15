<script setup>
import { PermissionApi } from '@/api'

defineExpose({ openDialog })
const emit = defineEmits(['refresh'])

const dialogFlag = ref(false)
const formRef = ref()
const formModal = ref({
  id: undefined,
  permission: undefined,
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
    await PermissionApi.modifyPermission(formModal.value)
    ElMessage.success('修改成功')
  } else {
    await PermissionApi.createPermission(formModal.value)
    ElMessage.success('添加成功')
  }
  emit('refresh')
  closeDialog()
}
</script>

<template>
  <el-dialog
    v-model="dialogFlag"
    title="权限信息"
    width="450"
    append-to-body
    :close-on-clikc-modal="false"
    :close-on-press-escape="false"
    @closed="closeDialog"
  >
    <el-form :modal="formModal" ref="formRef" label-width="80px">
      <el-form-item prop="permission" label="权限">
        <el-input v-model="formModal.permission" placeholder="请输入权限标识符" />
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
