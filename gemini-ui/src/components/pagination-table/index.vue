<script setup>
const props = defineProps({
  tableData: Array,
  loading: Boolean,
  pageInfo: Object
})

const emit = defineEmits(['selection-event'])

const headerStyle = {
  background: '#f8f8f9'
}

/** 多选事件 */
function handleSelectionChange(selection) {
  const selectIds = selection.map(item => item.id)
  emit('selection-event', selectIds)
}
</script>

<template>
  <el-table
    :data="props.tableData"
    v-loading="props.loading"
    @selection-change="handleSelectionChange"
    :header-cell-style="headerStyle"
  >
    <slot></slot>
  </el-table>
  <el-pagination
    mt-2
    float-right
    v-if="props.pageInfo.total > 0"
    background
    layout="prev, pager, next"
    :total="props.pageInfo.total"
    :current-page="props.pageInfo.pageNo"
    :page-count="props.pageInfo.pageSize"
  />
</template>
