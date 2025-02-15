<script setup>
import { AccountApi } from '@/api/system/account'

const tableColumn = [
  {
    label: 'ID',
    tooltip: '账户唯一ID',
    width: 100,
    prop: 'id',
    hideInSearch: true,
    tableColumnProps: {
      fixed: 'left'
    }
  },
  {
    label: '联系人',
    width: 120,
    prop: 'contact'
  },
  {
    label: '手机号',
    width: 120,
    prop: 'phone',
    hideInSearch: true
  },
  {
    label: '公司名',
    width: 120,
    prop: 'company'
  },
  {
    label: '机构编码',
    width: 120,
    prop: 'licenseNumber',
    hideInSearch: true
  },
  {
    label: '地址',
    width: 120,
    prop: 'address',
    hideInSearch: true
  },
  {
    label: '业务类型',
    width: 120,
    valueType: 'select',
    prop: 'bizType',
    options: [
      {
        label: '餐饮',
        value: '1',
        color: 'yellow'
      },
      {
        label: '口腔诊所',
        value: '2',
        color: 'blue'
      }
    ]
  },
  {
    label: '备注',
    width: 120,
    prop: 'remark',
    tableColumnProps: {
      showOverflowTooltip: true
    },
    hideInSearch: true
  },
  {
    label: '管理员账号',
    width: 100,
    prop: 'isAdmin',
    valueType: 'tag',
    fieldProps: val => (val === 1 ? { type: 'success' } : { type: 'info' }),
    hideInSearch: true
  },
  {
    label: '状态',
    width: 100,
    prop: 'status',
    valueType: 'select',
    options: [
      {
        label: '未开始',
        value: '0',
        color: 'gray'
      },
      {
        label: '试用中',
        value: '1',
        color: 'blue'
      },
      {
        label: '试用结束',
        value: '2',
        color: 'yellow'
      },
      {
        label: '使用中',
        value: '3',
        color: 'blue'
      },
      {
        label: '已过期',
        value: '4',
        color: 'red'
      }
    ]
  },
  {
    label: '试用开始时间',
    width: 200,
    prop: 'trialStartDate',
    valueType: 'date-picker',
    hideInSearch: true
  },
  {
    label: '试用结束时间',
    width: 200,
    prop: 'trialEndDate',
    valueType: 'date-picker',
    hideInSearch: true
  },
  {
    label: '订阅开始时间',
    width: 120,
    prop: 'startDate',
    valueType: 'date-picker',
    hideInSearch: true
  },
  {
    label: '订阅结束时间',
    width: 120,
    prop: 'endDate',
    valueType: 'date-picker',
    hideInSearch: true
  }
]
const state = reactive({
  query: {
    contact: undefined,
    company: undefined,
    bizType: undefined,
    status: undefined
  },
  selectIds: []
})

function handleSelect(data) {
  state.selectedIds = [...data].map(item => item.id)
}

async function loadRecords(params) {
  const { records, total } = await AccountApi.page(params)
  return { data: records, total }
}

async function handleBatchDelete() {}
async function handleCreate() {}
</script>

<!-- 表单搜索: 联系人、公司、业务类型、状态 -->
<!-- 数据表格 -->
<template>
  <PlusPage
    ref="plusPageInstance"
    :columns="tableColumn"
    :params="state.query"
    :request="loadRecords"
    :search="{ labelWidth: '80px', colProps: { span: 8 } }"
    :table="{ isSelection: true, onSelectionChange: handleSelect }"
  >
    <template #table-title>
      <el-row class="button-row">
        <el-button type="primary" @click="handleCreate"> 添加 </el-button>
        <el-button type="danger" @click="handleBatchDelete"> 批量删除 </el-button>
      </el-row>
    </template>
  </PlusPage>
</template>
