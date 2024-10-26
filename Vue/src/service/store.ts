import { defineStore } from 'pinia';
import { storeState } from '@/service/store';

export const useCounterStore = defineStore('main', {
    state: (): storeState => {
        return {
            windowWidth: 0,
            windowHeight: 0
        };
    },
    actions: {},
});