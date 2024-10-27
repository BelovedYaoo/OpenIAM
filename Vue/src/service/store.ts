import { defineStore } from 'pinia';
import { customTableState, storeState } from '@/service/store';

export const useCounterStore = defineStore('main', {
    state: (): storeState => {
        return {
            windowWidth: 1920,
            windowHeight: 1080
        };
    },
    actions: {},
});

export const useCustomTableStore = defineStore('customTable', {
    state: (): customTableState => {
        return {
            contextMenuSelection: null,
            selectedRecords: []
        };
    },
    actions: {
    },
});