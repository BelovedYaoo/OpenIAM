/// <reference types="vite/client" />

declare module '*.vue' {
    import { defineComponent } from 'vue';
    const Component: ReturnType<typeof defineComponent>;
    export default Component;
}

declare module '@/service/request' {
    export default function request(config: any): Promise<any>;
}

declare module '@/service/layout' {
    export function useLayout();
}

declare module '@/service/store' {
    import { StoreGeneric } from 'pinia';
    export function useCounterStore();
    export interface storeState extends StoreGeneric {
        windowWidth: number
        windowHeight: number
    }
}

declare module '@/service/globalQuote' {
    import { AppConfig } from '@/service/globalQuote';
    export const globalConfig: AppConfig;

    export const responseToastConfig: (response: any, duringSecond: number = 3) => any;

    export const addClassById: (id: string, className: string, second: number = 5) => void;
}

declare module 'js-cookie' {
    export function set(key: string, value: string): void;

    export function get(key: string): string;

    export function remove(key: string): void;
}