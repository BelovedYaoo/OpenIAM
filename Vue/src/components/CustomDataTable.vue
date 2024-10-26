<script lang="ts" setup>
import { FilterMatchMode } from 'primevue/api';
import { computed, onBeforeMount, ref, watch } from 'vue';
import { DataTablePageEvent, DataTableRowContextMenuEvent, DataTableRowReorderEvent } from 'primevue/datatable';
import { useLayout } from '@/service/layout';
import request from '@/service/request';
import { ColumnProps } from 'primevue/column';
import { storeState, useCounterStore } from '@/service/store';
import { storeToRefs } from 'pinia';
import { AxiosResponse } from 'axios';
import { responseToastConfig } from '@/service/globalQuote';
import { useToast } from 'primevue/usetoast';

const toast = useToast();

const props = defineProps({
    tableName: {
        type: String,
        required: true,
    },
    dataUrl: {
        type: String,
        required: true
    },
    reorderUrl: {
        type: String,
        required: true
    },
    filedList: {
        type: Array<ColumnProps>,
        required: true
    }
});

// 过滤器
const keywordFilters = ref();
const initFilters = () => {
    keywordFilters.value = {
        global: { value: null, matchMode: FilterMatchMode.CONTAINS },
    };
};

// 启用排序和选中
const enableSortedAndSelected = ref(false);
const switchedAndSelectedToggle = () => {
    enableSortedAndSelected.value = !enableSortedAndSelected.value;
    selectedRecords.value = [];
};

// 选中记录
const selectedRecords = ref();

// 样式设置浮窗
const paletteOp = ref();
const paletteToggle = (event: PointerEvent) => {
    paletteOp.value.toggle(event);
};

// 表格大小选项
const sizeOptions = ref([
    { label: '较小', value: 'small', class: 'sm' },
    { label: '默认', value: 'normal', class: '' },
    { label: '较大', value: 'large', class: 'lg' }
]);

// 表格样式选项
const dataTableStyle = ref({
    size: { label: '默认', value: 'normal', class: '' },
    showGridlines: false,
    stripedRows: true,
    rowsPerPage: 10,
    currentPage: 1,
    totalPages: 0
});

// 总页数
const totalPages = ref(0);

// 总记录数
const totalRecords = ref(0);

// 监听页面数量变化
watch(() => dataTableStyle.value.rowsPerPage, (newValue, oldValue) => {
    // 根据页面数量计算总页数
    totalPages.value = Math.ceil(tableData.value.length / dataTableStyle.value.rowsPerPage);
    // 重置当前页
    dataTableStyle.value.currentPage = Math.max(Math.ceil(((dataTableStyle.value.currentPage - 1) * oldValue + 1) / newValue), 1);
});

// 挂载
onBeforeMount(() => {
    initFilters();
    dataInit();
});

// 数据初始化
const tableData = ref();
const dataInit = () => {
    request({
        url: props.dataUrl,
        method: 'GET'
    }).then((response: AxiosResponse) => {
        toast.add(responseToastConfig(response));
        tableData.value = response.data.data as Array<Account>;
        totalPages.value = Math.ceil(tableData.value.length / dataTableStyle.value.rowsPerPage);
        totalRecords.value = tableData.value.length;
    });
};

// 当通过DataTable分页器切换分页时，通知底部分页器同步
const onPageChange = (event: DataTablePageEvent) => {
    dataTableStyle.value.currentPage = event.page + 1;
};

// 从状态仓库取出窗口宽度与高度
const store = useCounterStore();
const { windowWidth, windowHeight } = storeToRefs<storeState>(store);

const { layoutState } = useLayout();

// 表格底部显示
const showFooter = computed(() => {
    return windowWidth.value > (!layoutState.staticMenuDesktopInactive.value ? 1388 : 1110);
});

// 迷你显示
const miniShow = computed(() => {
    return windowWidth.value < 563;
});

// 行重新排序事件
const onRowReorder = (event: DataTableRowReorderEvent) => {
    const { dragIndex, dropIndex } = event;
    request({
        url: props.reorderUrl,
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

// 表数据导出
const dataTable = ref();
const exportCSV = () => {
    dataTable.value.exportCSV(enableSortedAndSelected.value ? { selectionOnly: true } : undefined);
};

// 表右键菜单
const cm = ref();
const contextMenuSelection = ref();

// 菜单展开
const onRowContextMenu = (event: DataTableRowContextMenuEvent) => {
    cm.value.show(event.originalEvent);
};

// 菜单列表
const menuModel = computed(() => {
    return [
        {
            label: '修改',
            icon: 'pi pi-fw pi-pencil',
            command: () => modifyRecord(contextMenuSelection)
        },
        {
            label: '删除',
            icon: 'pi pi-fw pi-times',
            command: () => deleteRecord(contextMenuSelection)
        }
    ].concat(enableSortedAndSelected.value ? [
        {
            label: '删除选中项',
            icon: 'pi pi-fw pi-trash',
            command: () => deleteRecordsDialog.value = true
        }
    ] : []);
});

// 修改逻辑
const modifyRecord = (record: any) => {
    console.log(record.value);
};

// 删除逻辑
const deleteRecord = (record: any) => {
    tableData.value = tableData.value.filter((p: any) => p.baseId !== record.value.baseId);
    contextMenuSelection.value = null;
};

// 行选中删除
const deleteRecordsDialog = ref(false);
const deleteRecords = () => {
    console.log(selectedRecords.value);
    deleteRecordsDialog.value = false;
    dataInit();
    selectedRecords.value.length = 0;
};
</script>

<template>
    <div class="card">
        <ContextMenu ref="cm" :model="menuModel"/>
        <DataTable ref="dataTable"
                   v-model:contextMenuSelection="contextMenuSelection"
                   v-model:selection="selectedRecords"
                   :class="`p-datatable-${dataTableStyle.size.class}`"
                   :filters="keywordFilters"
                   :first="(dataTableStyle.currentPage - 1) * dataTableStyle.rowsPerPage"
                   :paginator-template="{
                       '450px': 'PrevPageLink CurrentPageReport NextPageLink',
                       '570px': 'FirstPageLink PrevPageLink CurrentPageReport NextPageLink LastPageLink'
                   }"
                   :reorderableColumns="enableSortedAndSelected"
                   :rows="dataTableStyle.rowsPerPage"
                   :scrollHeight="`${windowHeight - 400}px`"
                   :showGridlines="dataTableStyle.showGridlines"
                   :stripedRows="dataTableStyle.stripedRows"
                   :value="tableData"
                   contextMenu
                   current-page-report-template="{currentPage} / {totalPages}"
                   paginator
                   removableSort
                   scrollable
                   sortMode="multiple"
                   tableStyle="min-width: 50rem"
                   @page="onPageChange"
                   @rowContextmenu="onRowContextMenu"
                   @rowReorder="onRowReorder">
            <template #header>
                <div class="flex flex-wrap align-items-center justify-content-between gap-2">
                    <span class="text-xl text-900 font-bold">{{ props.tableName }}</span>
                    <div class="flex gap-4">
                        <span class="p-input-icon-left">
                            <i class="pi pi-search"/>
                            <InputText v-model="keywordFilters['global'].value" :class="miniShow?'w-10rem':'w-13rem'"
                                       placeholder="输入以搜索" type="text"/>
                        </span>
                        <!-- 刷新按钮 -->
                        <Button v-if="!miniShow" icon="pi pi-refresh" raised rounded @click="dataInit"/>
                        <!-- 修改按钮 -->
                        <Button v-if="!miniShow" icon="pi pi-bars" raised rounded
                                @click="switchedAndSelectedToggle"/>
                        <!-- 配置按钮 -->
                        <Button icon="pi pi-cog" raised rounded @click="paletteToggle"/>
                        <OverlayPanel ref="paletteOp">
                            <div class="flex flex-column gap-3">
                                <div class="flex flex-row gap-3">
                                    <!-- 刷新按钮 -->
                                    <Button v-if="miniShow" icon="pi pi-refresh" raised rounded @click="dataInit"/>
                                    <!-- 修改按钮 -->
                                    <Button v-if="miniShow" icon="pi pi-bars" raised rounded
                                            @click="switchedAndSelectedToggle"/>
                                    <Button icon="pi pi-upload" raised rounded
                                            @click="exportCSV"/>
                                </div>
                                <SelectButton v-model="dataTableStyle.size" :options="sizeOptions" dataKey="label"
                                              optionLabel="label"/>
                                <div class="flex align-items-center justify-content-between">
                                    <label>单元格描边：</label>
                                    <InputSwitch v-model="dataTableStyle.showGridlines"/>
                                </div>
                                <div class="flex align-items-center justify-content-between">
                                    <label>交替条纹：</label>
                                    <InputSwitch v-model="dataTableStyle.stripedRows"/>
                                </div>
                            </div>
                            <Divider v-if="!showFooter"/>
                            <div class="flex flex-column gap-3">
                                <div v-if="!showFooter" class="flex flex-column gap-3">
                                    <div>
                                        <label>单页数量：</label>
                                        <Dropdown v-model="dataTableStyle.rowsPerPage" :options="[5, 10, 20, 50]"/>
                                    </div>
                                    <div>
                                        <label>当前页数：</label>
                                        <Dropdown v-model="dataTableStyle.currentPage"
                                                  :options="Array.from({length: totalPages}, (_, i) => i + 1)"/>
                                    </div>
                                </div>
                            </div>
                        </OverlayPanel>
                    </div>
                </div>
            </template>
            <template v-if="showFooter" #paginatorstart>
                <label>当前显示第 {{ (dataTableStyle.currentPage - 1) * dataTableStyle.rowsPerPage + 1 }} 至
                    {{ Math.min(dataTableStyle.currentPage * dataTableStyle.rowsPerPage, totalRecords) }} 项，共
                    {{ totalRecords }}
                    条记录</label>
            </template>
            <template v-if="showFooter" #paginatorend>
                <div class="flex flex-row align-items-center justify-content-center">
                    <div>
                        <label>单页数量：</label>
                        <Dropdown v-model="dataTableStyle.rowsPerPage" :options="[5, 10, 20, 50]"/>
                    </div>
                    <div>
                        <label>当前页数：</label>
                        <Dropdown v-model="dataTableStyle.currentPage"
                                  :options="Array.from({length: totalPages}, (_, i) => i + 1)"/>
                    </div>
                </div>
            </template>
            <Column v-if="enableSortedAndSelected" :reorderableColumn="false" headerStyle="width:1%;min-width:1rem;"
                    rowReorder/>
            <Column v-if="enableSortedAndSelected" :reorderableColumn="false" headerStyle="width:1%;min-width:1rem;"
                    selectionMode="multiple"></Column>
            <!-- 循环渲染 -->
            <Column v-for="filed in props.filedList"
                    :key="filed.field"
                    :field="filed.field"
                    :header="filed.header"
                    :headerStyle="filed?.style"
                    :reorderableColumn="false"
                    sortable=""></Column>
        </DataTable>
        <Dialog
            v-model:visible="deleteRecordsDialog"
            class="w-auto"
            header="确认删除吗?"
            modal
        >
            <div class="flex align-items-center justify-content-left mt-3">
                <i class="pi pi-exclamation-triangle mr-3 text-3xl"/>
                <span v-if="selectedRecords"
                >共有
              <b>{{ selectedRecords.length }} </b> 条的数据将被删除，确认删除吗?</span
                >
            </div>
            <template #footer>
                <Button
                    class="p-button-text"
                    icon="pi pi-times"
                    label="没有"
                    @click="deleteRecordsDialog = false"
                />
                <Button
                    class="p-button-text"
                    icon="pi pi-check"
                    label="是的"
                    @click="deleteRecords"
                />
            </template>
        </Dialog>
    </div>
</template>

<style scoped>
.edit-button {
    width: 30px;
    height: 30px;
}
</style>