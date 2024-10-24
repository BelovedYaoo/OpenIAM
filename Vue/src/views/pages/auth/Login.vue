<script setup lang="ts">
import { onMounted, onUnmounted, reactive, ref, watch } from 'vue';
import { useToast } from 'primevue/usetoast';
import axios from 'axios';
import cookie from 'js-cookie';
import { sha256 } from 'hash.js';
import { addClassById, globalConfig, responseToastConfig } from '@/service/globalQuote';
import request from '@/service/request';
import router from '@/service/router';
import LogoSvg from '@/components/LogoSvg.vue';

let timer = reactive({});

const toast = useToast();

const openId = ref('');
const password = ref('');
const yiyan = ref('永远都有更好，但眼下便是最好');
const apiList = ref(['/vvhan/text/love', '/yiyan', '/tenapi/yiyan', '/uapis/say']);
const apiUse = ref('');
const apiAvailability = ref(10);
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

const login = () => {
    checked.value = true;
    checkRegisterAccountInfo();
    if (!checked.value) {
        return;
    }
    request({
        method: 'POST',
        url: '/auth/accountLogin',
        data: {
            openId: openId.value,
            password: btoa(sha256().update(password.value).digest('hex'))
        }
    }).then((res) => {
        toast.add(responseToastConfig(res));
        if (res.data.code === 200 && res.data.data.tokenValue !== null) {
            // token存入cookie
            cookie.set(globalConfig.appTokenName, res.data.data.tokenValue);
            // 页面跳转
            router.push({
                path: '/'
            });
        }
    });
};

// 挂载！
onMounted(() => {
    // 初始化一言
    apiUse.value = apiList.value[0];
    updateYiyan();
    // 定时执行
    timer = setInterval(() => {
        updateYiyan();
    }, 6000);
});

// 取消挂载！
onUnmounted(() => {
    clearTimer();
});

// 清除一言计时器
const clearTimer = () => {
    if (timer) {
        clearInterval(timer);
        timer = null;
    }
};

// 一言渐变更新
let updateYiyan = () => {
    // 获取类名含有ayou的元素
    const span = document.querySelector('.ayou');
    // 获取一言
    axios
        .request({
            method: 'GET',
            url: apiUse.value
        })
        .then((res) => {
            // 递归检查
            // 状态码异常
            // 当api接口参数被调整时，请求中的状态码也可能是200，但是实际没有一言数据
            if (res.status !== 200 || (res.data.code && res.data.code !== 200)) {
                switchApi();
                return updateYiyan();
            }
            // 一言过长，会影响页面显示
            if (res.data.length > 35) {
                return updateYiyan();
            }
            // 为ayou元素添加渐隐效果
            span.style.opacity = '0';
            // 延时执行，让渐变更平滑
            setTimeout(() => {
                yiyan.value = res.data;
                // 为ayou元素添加渐显效果
                span.style.opacity = '1';
            }, 500);
        })
        .catch(() => {
            switchApi();
            return updateYiyan();
        });
};

// Api切换
const switchApi = () => {
    console.warn('Api切换！');
    const index = apiList.value.indexOf(apiUse.value) + 1;
    if (apiList.value.length === index) {
        // Api可用性保障，确保不会因为所有Api均不可用而导致死循环
        apiAvailability.value -= 2;
        apiUse.value = apiList.value[0];
    } else {
        apiUse.value = apiList.value[index];
    }
    console.warn(apiUse.value);
};

// Api可用性监听
watch(apiAvailability, (newValue) => {
    if (newValue === 0) {
        console.warn('API 可用性已降至 0');
        // 中断一言更新函数
        updateYiyan = null;
        // 清除一言定时器
        clearTimer();
        // 固定一言显示
        yiyan.value = '永远都有更好，但眼下便是最好';
        // 获取类名含有ayou的元素
        const span = document.querySelector('.ayou');
        // 为ayou元素添加渐显效果
        span.style.opacity = '1';
    }
});
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
              <span class="transition: opacity 0.5s text-600 font-bold ayou" style="transition: opacity 0.5s">{{
                      yiyan
                  }}</span>
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
