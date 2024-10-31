import { createRouter, createWebHashHistory } from 'vue-router';
import AppLayout from '@/layout/AppLayout.vue';
import { globalConfig } from './globalQuote.ts';
import cookie from 'js-cookie';

const router = createRouter({
    history: createWebHashHistory(),
    routes: [
        {
            path: '/',
            component: AppLayout,
            children: [
                {
                    path: '/',
                    name: 'dashboard',
                    component: () => import('@/views/Dashboard.vue')
                },
                {
                    path: '/control',
                    children: [
                        {
                            path: 'userControl',
                            name: 'userControl',
                            component: () => import('@/views/control/UserControl.vue')
                        },
                    ]
                },
            ]
        },
        {
            path: '/auth',
            children: [
                {
                    path: 'insideLogin',
                    name: 'insideLogin',
                    component: () => import('@/views/pages/auth/InsideLogin.vue')
                },
                {
                    path: 'login',
                    name: 'login',
                    component: () => import('@/views/pages/auth/Login.vue')
                },
                {
                    path: 'register',
                    name: 'register',
                    component: () => import('@/views/pages/auth/Register.vue')
                },
            ]
        },
        {
            path: '/notfound',
            name: 'notfound',
            component: () => import('@/views/pages/NotFound.vue')
        },
        {
            path: '/access',
            name: 'accessDenied',
            component: () => import('@/views/pages/Access.vue')
        },
        {
            path: '/error',
            name: 'error',
            component: () => import('@/views/pages/Error.vue')
        },
        {
            path: '/:pathMatch(.*)*',
            name: 'notFound',
            component: () => import('@/views/pages/NotFound.vue')
        }
    ]
});

// 路由守卫
router.beforeEach((to, from, next) => {
    // 如果访问的是登录页、注册页、忘记密码页，直接放行
    if (to.name === 'login' || to.name === 'register' || to.name === 'forget') {
        return next();
    }

    // 未登录用户重定向到登录页
    const tokenValue = cookie.get(globalConfig.appTokenName);
    if (tokenValue === '' || tokenValue === null || tokenValue === undefined) {
        return next({ name: 'login' });
    }

    next();
});

export default router;
