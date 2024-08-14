import {AxiosResponse} from 'axios';

export const responseToastConfig = (response:AxiosResponse<any>) => {
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
}

export const globalConfig: AppConfig = {
    appName: 'OpenIAM',
};