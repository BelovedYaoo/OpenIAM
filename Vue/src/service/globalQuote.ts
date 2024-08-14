import {AxiosResponse} from 'axios';
import {ToastMessageOptions} from 'primevue/toast';

export const responseToastConfig = (response: AxiosResponse<any>): ToastMessageOptions => {
  return {
    severity: response.data.code === 200 ? 'success' : 'error',
    summary: response.data.message,
    detail: response.data.description,
    life: 3000
  };
};

export const addClassById = (id: string, className: string, second: number = 5): void => {
  const element: HTMLElement | null = document.getElementById(id);
  if (element) {
    element.classList.add(className);
    setTimeout((): void => {
      element.classList.remove(className);
    }, second * 1000); // 默认延时5秒
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