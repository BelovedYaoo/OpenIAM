<script lang="ts" setup>
import { ref } from 'vue';
import CustomDataTable from '@/components/CustomDataTable.vue';
import { ColumnProps } from 'primevue/column';

// 字段列表
const filedList = ref<Array<ColumnProps>>([
    { field: 'openId', header: 'OpenID', style: 'width:20%;min-width:10rem;' },
    { field: 'phone', header: '手机号', style: 'width:20%;min-width:10rem;' },
    { field: 'email', header: '邮箱', style: 'width:20%;min-width:10rem;' },
    { field: 'nickname', header: '昵称', style: 'width:10%;min-width:7rem;' },
]);
</script>

<template>
    <CustomDataTable table-name="账户" :data-url="'/acc/queryAll'" :filed-list="filedList"/>
</template>

<style scoped>
</style>