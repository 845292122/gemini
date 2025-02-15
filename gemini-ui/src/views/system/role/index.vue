<script setup>
import PaginationTable from '@/components/pagination-table'
import RoleDialog from './role-dialog'
import { RoleApi } from '@/api'
import { dayjs } from 'element-plus'

const dialogRef = ref()
const queryFormRef = ref()
const queryForm = ref({
  name: undefined,
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
  RoleApi.getRoleList({ ...queryForm.value, ...pageInfo.value })
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
  const roleInfo = await RoleApi.getRoleInfo(id)
  dialogRef.value.openDialog(roleInfo)
}

async function handleRemove(id) {
  await RoleApi.removeRole(id)
  ElMessage.success('删除成功')
  loadRecords()
}

loadRecords()
</script>

<template>
  <el-form :inline="true" :model="queryForm" ref="queryFormRef">
    <el-form-item label="名称" prop="name">
      <el-input v-model="queryForm.name" placeholder="请输入用户名称" clearable style="width: 170px" />
    </el-form-item>
    <el-form-item label="用户状态" prop="status">
      <el-select v-model="queryForm.status" placeholder="用户状态" clearable style="width: 170px">
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
    <el-table-column prop="name" label="名称" />
    <el-table-column prop="key" label="权限标识" />
    <el-table-column prop="status" label="状态">
      <template #default="scope">
        <el-tag type="success" v-if="scope.row.status === 1">启用</el-tag>
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
            title="确认删除角色?"
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
  <RoleDialog ref="dialogRef" @refresh="loadRecords" />
</template>
