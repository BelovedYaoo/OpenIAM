import { defineStore } from 'pinia';
import { storeState } from '@/service/store';

export const useCounterStore = defineStore('main', {
    state: (): storeState => {
        return {
            windowWidth: 1920,
            windowHeight: 1080
        };
    },
    actions: {},
});