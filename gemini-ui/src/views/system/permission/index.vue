<script setup>
import PermissionDialog from './permission-dialog'
import PaginationTable from '@/components/pagination-table'
import { PermissionApi } from '@/api'
import { dayjs } from 'element-plus'

const dialogRef = ref()
const queryFormRef = ref()
const queryForm = ref({
  permission: undefined,
  status: undefined
})
const tableData = ref([])
const loading = ref(false)
const pageInfo = ref({
  total: 0,
  pageNo: 1,
  pageSize: 10
})

function loadRecords() {
  loading.value = true
  PermissionApi.getPermissionList({ ...queryForm.value, ...pageInfo.value })
    .then(res => {
      tableData.value = res.records
      pageInfo.value.total = res.total
    })
    .finally(() => {
      loading.value = false
    })
}

function handleQuery() {
  queryForm.value.pageNo = 1
  loadRecords()
}

function handleReset() {
  queryFormRef.value.resetFields()
}

function handleAdd() {
  dialogRef.value.openDialog(null)
}

async function handleEdit(id) {
  const roleInfo = await PermissionApi.getPermissionInfo(id)
  dialogRef.value.openDialog(roleInfo)
}

async function handleRemove(id) {
  await PermissionApi.removePermission(id)
  ElMessage.success('删除成功')
  loadRecords()
}

loadRecords()
</script>

<template>
  <el-form :inline="true" :model="queryForm" ref="queryFormRef">
    <el-form-item label="权限" prop="permission">
      <el-input v-model="queryForm.permission" placeholder="请输入权限标识符" clearable style="width: 170px" />
    </el-form-item>
    <el-form-item label="权限状态" prop="status">
      <el-select v-model="queryForm.status" placeholder="权限状态" clearable style="width: 170px">
        <el-option label="启用" value="1" />
        <el-option label="禁用" value="0" />
      </el-select>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="handleQuery">
        <template #icon>
          <i-bi:search />
        </template>
        查询
      </el-button>
      <el-button @click="handleReset">
        <template #icon>
          <i-bi:arrow-repeat />
        </template>
        重置
      </el-button>
    </el-form-item>
  </el-form>

  <el-space mb-5>
    <el-button type="primary" plain @click="handleAdd">
      <template #icon>
        <i-bi:plus-lg />
      </template>
      新增
    </el-button>
  </el-space>

  <PaginationTable :tableData="tableData" :loading="loading" :pageInfo="pageInfo">
    <el-table-column prop="id" label="ID" />
    <el-table-column prop="permission" label="权限" />
    <el-table-column prop="status" label="状态">
      <template #default="scope">
        <el-tag type="success" v-if="scope.row.status == 1">启用</el-tag>
        <el-tag type="danger" v-else>禁用</el-tag>
      </template>
    </el-table-column>
    <el-table-column prop="createdAt" label="创建时间" width="200">
      <template #default="scope">
        {{ scope.row.createdAt ? dayjs(scope.row.createdAt).format('YYYY-MM-DD HH:mm:ss') : undefined }}
      </template>
    </el-table-column>
    <el-table-column label="操作" align="center" width="100" fixed="right">
      <template #default="scope">
        <div flex justify-evenly>
          <el-tooltip content="修改" placement="top">
            <el-button link type="primary" @click="handleEdit(scope.row.id)">
              <template #icon>
                <i-bi:highlighter />
              </template>
            </el-button>
          </el-tooltip>

          <el-popconfirm
            title="确认删除权限?"
            confirm-button-text="确认"
            cancel-button-text="取消"
            @confirm="handleRemove(scope.row.id)"
          >
            <template #reference>
              <div>
                <el-tooltip content="删除" placement="top">
                  <el-button link type="primary">
                    <template #icon>
                      <i-bi:trash />
                    </template>
                  </el-button>
                </el-tooltip>
              </div>
            </template>
          </el-popconfirm>
        </div>
      </template>
    </el-table-column>
  </PaginationTable>

  <PermissionDialog ref="dialogRef" @refresh="loadRecords" />
</template>
