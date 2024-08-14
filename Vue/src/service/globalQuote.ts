import {AxiosResponse} from 'axios';
import {ToastMessageOptions} from 'primevue/toast';

export const responseToastConfig = (response:AxiosResponse<any>):ToastMessageOptions => {
    if (response.data.code === 200) {
        return {
            severity: 'success',
            summary: response.data.message,
            detail: response.data.description,
            life: 3000
        };
    } else {
        return {
            severity: 'error',
            summary: response.data.message,
            detail: response.data.description,
            life: 3000
        };
    }
};

export const addClassById = (id:string, className:string, second = 5) => {
    const element = document.getElementById(id);
    if (element) {
        element.classList.add(className);
        setTimeout(() => {
            element.classList.remove(className);
        }, second * 1000); // 延时5秒
    }
};

export interface AppConfig {
    appName: string;
    appTokenName: string;
}

export const globalConfig: AppConfig = {
    appName: 'OpenIAM',
    appTokenName: 'openToken'
};