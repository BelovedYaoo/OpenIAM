/// <reference types="vite/client" />

declare module '*.vue' {
    import {defineComponent} from 'vue';
    const Component: ReturnType<typeof defineComponent>;
    export default Component;
}

declare module '@/service/request' {
    export default function request(config: any): Promise<any>;
}

declare module 'js-cookie' {
    export function set(key: string, value: string): void;
    export function get(key: string): string;
    export function remove(key: string): void;
}