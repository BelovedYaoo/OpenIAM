<script lang="ts" setup>
import { onBeforeMount, ref } from 'vue';
import CustomDataTable from '@/components/CustomDataTable.vue';
import { DataTableRowReorderEvent } from 'primevue/datatable';
import { ColumnProps } from 'primevue/column';
import request from '@/service/request';
import { AxiosResponse } from 'axios';
import { responseToastConfig } from '@/service/globalQuote';
import { useToast } from 'primevue/usetoast';

const toast = useToast();

onBeforeMount(() => {
    dataInit(); // 确保数据加载完成后再挂载
});

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
const onRowDelete = (records: BaseFiled[]) => {
    request({
        url: '/acc/delete',
        method: 'POST',
        data: records.map(r => r.baseId)
    }).then((response: AxiosResponse) => {
        toast.add(responseToastConfig(response));
        dataInit();
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

// 刷新逻辑
const onTableDataRefresh = () => {
    dataInit();
};

// 顺序交换逻辑
const onOrderSwap = (swapRecords: BaseFiled[]) => {
    request({
        url: '/acc/orderSwap',
        method: 'POST',
        params: {
            leftTargetBaseId: swapRecords[0].baseId,
            leftTargetOrderNum: swapRecords[0].orderNum,
            rightTargetBaseId: swapRecords[1].baseId,
            rightTargetOrderNum: swapRecords[1].orderNum
        }
    }).then((response: AxiosResponse) => {
        toast.add(responseToastConfig(response));
        dataInit();
    });
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
    <CustomDataTable :on-order-swap="onOrderSwap"
                     :on-row-delete="onRowDelete"
                     :on-row-modify="onRowReorder"
                     :on-row-reorder="onRowReorder"
                     :on-table-data-refresh="onTableDataRefresh"
                     :table-data="tableData"
                     table-name="账户">
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