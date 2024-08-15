import { AxiosResponse } from 'axios';
import { ToastMessageOptions } from 'primevue/toast';

/**
 * 将Axios请求响应体转换为PrimeVue的Toast配置
 * @param response Axios请求响应体
 */
export const responseToastConfig = (response: AxiosResponse<any>): ToastMessageOptions => {
    return {
        severity: response.data.code === 200 ? 'success' : 'error',
        summary: response.data.message,
        detail: response.data.description,
        life: 3000
    };
};

/**
 * 将类名添加到指定id的元素上，并设置延时移除
 * @param id 需要添加的元素id名
 * @param className 被添加的类名
 * @param second 延时时长(默认5秒延时)
 */
export const addClassById = (id: string, className: string, second: number = 5): void => {
    const element: HTMLElement | null = document.getElementById(id);
    if (element) {
        element.classList.add(className);
        setTimeout((): void => {
            element.classList.remove(className);
        }, second * 1000);
    }
};

/**
 * 全局配置接口
 */
export interface AppConfig {
    appName: string;
    appTokenName: string;
}

/**
 * 全局配置
 */
export const globalConfig: AppConfig = {
    // 应用名称
    appName: 'OpenIAM',
    // Token名称
    appTokenName: 'openToken'
};