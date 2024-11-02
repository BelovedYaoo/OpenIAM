import axios, { AxiosInstance } from 'axios';
import cookie from 'js-cookie';
import router from './router';
import { getParameterByName, globalConfig } from './globalQuote.ts';

export const url: string = 'http://openiam.top:8090';
// export const url: string = 'http://192.168.1.100:8090';

// Axios 实例
const service: AxiosInstance = axios.create({
    baseURL: url,
    timeout: 10000 // 请求超时时间
});

// 设置cross跨域 并设置访问权限 允许跨域携带cookie信息,使用JWT可关闭
service.defaults.withCredentials = false;

// 请求拦截器
service.interceptors.request.use(
    (config) => {
        const tokenValue = cookie.get(globalConfig.appTokenName);
        // 将cookie中的token设置在请求头中
        if (tokenValue !== '' && tokenValue !== null && tokenValue !== undefined) {
            config.headers['token'] = tokenValue;
        }
        config.headers['Content-Type'] = 'application/json';
        return config;
    },
    (err) => {
        return Promise.reject(err);
    }
);

// 响应拦截器
service.interceptors.response.use(
    (res) => {
        // 获取后端返回的状态码
        const code = res.data.code;
        // 会话失效
        if (code === 901) {
            // 清除token
            // cookie.remove(globalConfig.appTokenName);
            // 页面跳转
            router.push({
                path: '/auth/confirm',
                query: {
                    clientId: res.data.data.clientId,
                    scope: res.data.data.scope,
                    redirect_uri: getParameterByName('redirect_uri')
                }
            });
        }
        return res;
    },
    (error) => {
        return Promise.reject(error);
    }
);

export default service;
