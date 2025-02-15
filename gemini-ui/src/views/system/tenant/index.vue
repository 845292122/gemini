<script setup>
import PaginationTable from '@/components/pagination-table'
import TenantDialog from './tenant-dialog'
import { dayjs } from 'element-plus'
import { TenantApi } from '@/api'

const dialogRef = ref()
const queryFormRef = ref()
const queryForm = ref({
  name: undefined,
  contact: undefined,
  isTrial: undefined,
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
  TenantApi.getTenantList({ ...queryForm.value, ...pageInfo.value })
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
  const tenantInfo = await TenantApi.getRoleInfo(id)
  dialogRef.value.openDialog(tenantInfo)
}

async function handleRemove(id) {
  await TenantApi.removeTenant(id)
  ElMessage.success('删除成功')
  loadRecords()
}
loadRecords()
</script>

<template>
  <el-form :inline="true" :model="queryForm" ref="queryFormRef">
    <el-form-item label="租户名称" prop="name">
      <el-input v-model="queryForm.name" placeholder="请输入租户名称" clearable style="width: 170px" />
    </el-form-item>
    <el-form-item label="联系人" prop="contact">
      <el-input v-model="queryForm.contact" placeholder="请输入联系人" clearable style="width: 170px" />
    </el-form-item>
    <el-form-item label="试用状态" prop="status">
      <el-select v-model="queryForm.isTrial" placeholder="试用状态" clearable style="width: 170px">
        <el-option label="试用中" value="1" />
        <el-option label="试用结束" value="0" />
      </el-select>
    </el-form-item>
    <el-form-item label="租户状态" prop="status">
      <el-select v-model="queryForm.status" placeholder="租户状态" clearable style="width: 170px">
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
    <el-table-column prop="name" label="租户名称" />
    <el-table-column prop="contact" label="联系人" />
    <el-table-column prop="phone" label="手机号" />
    <el-table-column prop="company" label="公司名称" />
    <el-table-column prop="licenseNumber" label="社会信用代码" />
    <el-table-column prop="address" label="地址" />
    <el-table-column prop="remark" label="备注" />
    <el-table-column prop="isTrial" label="试用状态">
      <template #default="scope">
        <el-tag type="success" v-if="scope.row.isTrial == 1">试用中</el-tag>
        <el-tag type="danger" v-else>试用结束</el-tag>
      </template>
    </el-table-column>
    <el-table-column prop="startDate" label="开始时间" width="200">
      <template #default="scope">
        {{ scope.row.startDate ? dayjs(scope.row.startDate).format('YYYY-MM-DD HH:mm:ss') : undefined }}
      </template>
    </el-table-column>
    <el-table-column prop="endDate" label="结束时间" width="200">
      <template #default="scope">
        {{ scope.row.endDate ? dayjs(scope.row.endDate).format('YYYY-MM-DD HH:mm:ss') : undefined }}
      </template>
    </el-table-column>
    <el-table-column prop="accountCount" label="账号数量限制" />
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
  <TenantDialog ref="dialogRef" @refresh="loadRecords" />
</template>
