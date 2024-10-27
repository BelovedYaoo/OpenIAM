<script lang="ts" setup>
import { onBeforeMount, Ref, ref } from 'vue';
import CustomDataTable from '@/components/CustomDataTable.vue';
import { DataTableRowReorderEvent } from 'primevue/datatable';
import { ColumnProps } from 'primevue/column';
import request from '@/service/request';
import { AxiosResponse } from 'axios';
import { responseToastConfig } from '@/service/globalQuote';
import { useToast } from 'primevue/usetoast';
import { customTableState, useCustomTableStore } from '@/service/store';
import { storeToRefs } from 'pinia';

const toast = useToast();

onBeforeMount(() => {
    dataInit(); // 确保数据加载完成后再挂载
});

const store = useCustomTableStore();
const { selectedRecords } = storeToRefs<customTableState>(store);

// 数据初始化
const tableData = ref<Account[]>([]);
const dataInit = () => {
    request({
        url: '/acc/queryAll',
        method: 'GET'
    }).then((response: AxiosResponse) => {
        tableData.value = response.data.data as Array<Account>;
    });
};

// 删除逻辑
const deleteRecord = (record: Ref<BaseFiled>) => {
    request({
        url: '/acc/delete',
        method: 'POST',
        data: [record.value.baseId]
    }).then((response: AxiosResponse) => {
        toast.add(responseToastConfig(response));
        dataInit();
    });
};

// 行选中删除
const deleteRecords = () => {
    request({
        url: '/acc/delete',
        method: 'POST',
        data: selectedRecords.value.map((r: BaseFiled) => r.baseId)
    }).then((response: AxiosResponse) => {
        toast.add(responseToastConfig(response));
        dataInit();
        selectedRecords.value = [];
    });
};

// 行重新排序事件
const onRowReorder = (event: DataTableRowReorderEvent) => {
    const { dragIndex, dropIndex } = event;
    request({
        url: '/acc/reorder',
        method: 'POST',
        params: {
            leftTarget: tableData.value[dragIndex].orderNum,
            rightTarget: tableData.value[dropIndex].orderNum
        }
    }).then((response: AxiosResponse) => {
        toast.add(responseToastConfig(response));
    });
    tableData.value = event.value;
};

// 字段列表
const filedList = ref<Array<ColumnProps>>([
    { field: 'openId', header: 'OpenID', style: 'width:20%;min-width:10rem;' },
    { field: 'phone', header: '手机号', style: 'width:20%;min-width:10rem;' },
    { field: 'email', header: '邮箱', style: 'width:20%;min-width:10rem;' },
    { field: 'nickname', header: '昵称', style: 'width:10%;min-width:7rem;' },
]);
</script>

<template>
    <CustomDataTable :on-row-delete="deleteRecord" :on-row-modify="onRowReorder" :on-row-reorder="onRowReorder"
                     :on-row-select-delete="deleteRecords" :table-data="tableData" table-name="账户">
        <template #column>
            <Column v-for="filed in filedList"
                    :key="filed.field"
                    :field="filed.field"
                    :header="filed.header"
                    :headerStyle="filed?.style"
                    :reorderableColumn="false"
                    sortable=""></Column>
        </template>
    </CustomDataTable>
</template>

<style scoped>
</style>