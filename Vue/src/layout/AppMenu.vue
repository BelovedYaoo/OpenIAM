<script setup lang="ts">
import { ref } from 'vue';

import AppMenuItem from './AppMenuItem.vue';

const model = ref([
    {
        label: 'Home',
        items: [
            {
                label: 'Dashboard', icon: 'pi pi-fw pi-home', to: '/'
            }
        ]
    },
    {
        label: 'CONTROL',
        items: [
            {
                label: '用户管理',
                icon: 'pi pi-fw pi-user',
                items: [
                    {
                        label: '用户列表',
                        icon: 'pi pi-fw pi-users',
                        to: '/control/userControl'
                    }
                ]
            },
            {
                label: '应用管理',
                mdi: 'mdi:table',
                style: {width:'18px',height:'17px'},
                items: [
                    {
                        label: '应用列表',
                        mdi: 'mdi:table',
                        style: {width:'18px',height:'17px'},
                        to: '/control/userControl'
                    }
                ]
            }
        ]
    },
    {
        label: 'Pages',
        icon: 'pi pi-fw pi-briefcase',
        to: '/pages',
        items: [
            {
                label: 'Auth',
                icon: 'pi pi-fw pi-user',
                items: [
                    {
                        label: 'InsideLogin',
                        icon: 'pi pi-fw pi-sign-in',
                        to: '/auth/insideLogin'
                    },
                    {
                        label: 'Login',
                        icon: 'pi pi-fw pi-sign-in',
                        to: '/auth/login'
                    },
                    {
                        label: 'Register',
                        icon: 'pi pi-fw pi-sign-in',
                        to: '/auth/register'
                    },
                    {
                        label: 'Forget',
                        icon: 'pi pi-fw pi-sign-in',
                        to: '/auth/forget'
                    }
                ]
            },
            {
                label: 'Not Found',
                icon: 'pi pi-fw pi-exclamation-circle',
                to: '/notfound'
            },
            {
                label: 'Error',
                icon: 'pi pi-fw pi-times-circle',
                to: '/error'
            },
            {
                label: 'Access Denied',
                icon: 'pi pi-fw pi-lock',
                to: '/access'
            }
        ]
    }
]);
</script>

<template>
    <ul class="layout-menu">
        <template v-for="(item, i) in model" :key="item">
            <app-menu-item :index="i" :item="item"></app-menu-item>
        </template>
    </ul>
</template>

<style lang="scss" scoped></style>
