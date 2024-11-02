<script lang="ts" setup>
import { onMounted, ref } from 'vue';
import { useToast } from 'primevue/usetoast';
import cookie from 'js-cookie';
import { addClassById, getParameterByName, globalConfig, responseToastConfig } from '@/service/globalQuote';
import request from '@/service/request';
import router from '@/service/router';
import LogoSvg from '@/components/LogoSvg.vue';
import YiYan from '@/components/YiYan.vue';
import { AxiosResponse } from 'axios';

const toast = useToast();

const openId = ref('');
const password = ref('');
const remember = ref(false);
const checked = ref(false);

const passwordIsFocus = ref(false);
const openIdIsFocus = ref(false);

const errorToast = (className, summary, detail) => {
    toast.add({
        severity: 'error',
        summary: summary,
        detail: detail,
        life: 3000
    });
    addClassById(className, 'p-invalid');
    checked.value = false;
};

const checkRegisterAccountInfo = () => {
    if (openId.value === '') {
        errorToast('openId', '你有地方没填写喔~', '账号不能为空');
    }

    if (password.value === '') {
        errorToast('password', '你有地方没填写喔~', '密码不能为空');
    }
};

onMounted(() => {
    const tokenValue = cookie.get(globalConfig.appTokenName);
    if (tokenValue === '' || tokenValue === null || tokenValue === undefined) {
        return;
    }
    code();
});

const login = () => {
    checked.value = true;
    checkRegisterAccountInfo();
    if (!checked.value) {
        return;
    }
    request({
        method: 'POST',
        url: '/oauth2/doLogin',
        params: {
            username: openId.value,
            // pwd: btoa(sha256().update(password.value).digest('hex'))
            password: password.value
        }
    }).then((res) => {
        toast.add(responseToastConfig(res));
        if (res.data.code === 200 && res.data.data.tokenValue !== null) {
            // token存入cookie
            cookie.set(globalConfig.appTokenName, res.data.data.tokenValue);
            // 页面跳转
            code();
        }
    });
};

const code = () => {
    alert('code');
    request({
        method: 'POST',
        url: 'http://openiam.top:8090/oauth2/authorize',
        params: {
            response_type: getParameterByName('response_type'),
            client_id: getParameterByName('client_id'),
            redirect_uri: getParameterByName('redirect_uri'),
            scope: 'oidc'
        },
    }).then((res: AxiosResponse) => {
        console.log(res.data);
        if (res.data.code !== 901) {
            window.location.href = res.data.data;
        }
    });
};
</script>

<template>
    <div
        class="surface-ground flex align-items-center justify-content-center min-h-screen min-w-screen overflow-hidden">
        <div class="flex flex-column align-items-center justify-content-center">
            <div
                style="border-radius: 56px; padding: 0.3rem; background: linear-gradient(180deg, var(--primary-color) 10%, rgba(33, 150, 243, 0) 30%)">
                <div class="w-full surface-card pt-7 pb-8 px-5 sm:px-8" style="border-radius: 53px">
                    <div class="text-center mb-5">
                        <LogoSvg displayHeight="70" marginBottom="mb-1"/>
                        <div class="text-900 text-3xl font-bold mb-3">{{ globalConfig.appName }}</div>
                        <div style="width: 330px; height: 20px">
                            <YiYan/>
                        </div>
                    </div>
                    <div class="pt-3">
                        <span class="p-float-label mb-5">
                            <InputText id="openId" v-model="openId" class="w-full p-3"
                                       @blur="openIdIsFocus = false" @focus="openIdIsFocus = true"/>
                            <label
                                :class="{ 'text-600': openIdIsFocus || openId.length > 0, 'text-500': !openIdIsFocus }"
                                class="font-bold ml-1" for="username">Open ID</label>
                        </span>
                        <span class="p-float-label mb-4">
                            <Password
                                id="password"
                                v-model="password"
                                :feedback="false"
                                :toggleMask="true"
                                class="w-full"
                                inputClass="w-full p-3 font-bold"
                                @blur="passwordIsFocus = false"
                                @focus="passwordIsFocus = true"
                            ></Password>
                            <label
                                :class="{ 'text-600': passwordIsFocus || password.length > 0, 'text-500': !passwordIsFocus }"
                                class="font-bold ml-1" for="password">Password</label>
                        </span>

                        <div class="flex align-items-center justify-content-between pt-3 mb-3 gap-5">
                            <div class="flex align-items-center">
                                <Checkbox id="remember" v-model="remember" binary class="mr-2"></Checkbox>
                                <label for="remember">记住我</label>
                            </div>
                            <a class="font-medium no-underline ml-2 text-right cursor-pointer"
                               style="color: var(--primary-color)">游客访问</a>
                        </div>
                        <Button class="w-full p-3 text-xl" label="登录" @click="login()"></Button>
                        <div class="flex align-items-center justify-content-between pt-3 mt-0 gap-5">
                            <a class="font-medium no-underline ml-2 text-right cursor-pointer"
                               style="color: var(--primary-color)">忘记密码</a>
                            <a class="font-medium no-underline ml-2 text-right cursor-pointer"
                               style="color: var(--primary-color)"
                               @click="router.push({ path: '/auth/register' })">注册账号</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped></style>
