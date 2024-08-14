import axios from 'axios';
import cookie from 'js-cookie';
import router from './router';
import {globalConfig} from './globalQuote.ts';

export const url = 'http://localhost:8090';

// Axios 实例
const service = axios.create({
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
        config.headers['Content-Type'] = 'application/x-www-form-urlencoded';
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
        if (code === 701) {
            // 清除token
            cookie.remove(globalConfig.appTokenName);
            // 页面跳转
            router.push({
                path: '/auth/login',
                query: {
                    code: code,
                    description: res.data.description,
                    message: res.data.message
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
