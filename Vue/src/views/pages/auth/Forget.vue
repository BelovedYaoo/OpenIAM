<script setup>
import { ref, watch } from 'vue';
import { useRouter } from 'vue-router';
import { useToast } from 'primevue/usetoast';
import { SHA256 } from 'crypto-js';
import { addClassById, globalConfig, responseToastConfig } from '@/service/globalQuote';
import request from '@/service/request';
import LogoSvg from '@/components/LogoSvg.vue';

const router = useRouter();
const toast = useToast();
const accountLoginId = ref('');
const accountLoginPassword = ref('');
const accountPhone = ref('');
const accountEmail = ref('');
const verifyCode = ref('');
const checked = ref(false);
const agreed = ref(false);
const usePhone = ref(false);

const accountLoginIdRegex = /[a-zA-Z0-9]{5,15}$/;
const accountLoginPasswordRegex = /^.{5,20}$/;
const accountPhoneRegex = /^1[0-9]{10}$/;
const accountEmailRegex = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;

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

watch(verifyCode, () => {
    verifyCode.value = verifyCode.value.replaceAll(/\D/g, '');
});

const checkRegisterAccountInfo = () => {
    if (accountLoginId.value === '') {
        errorToast('accountLoginId', '你有地方没填写喔~', '账号不能为空');
    } else if (!accountLoginIdRegex.test(accountLoginId.value)) {
        errorToast('accountLoginId', '账号不合规', '最短5位，最长15位，只允许使用大小写字母与数字');
    }

    if (accountLoginPassword.value === '') {
        errorToast('accountLoginPassword', '你有地方没填写喔~', '密码不能为空');
    } else if (!accountLoginPasswordRegex.test(accountLoginPassword.value)) {
        errorToast('accountLoginPassword', '密码不合规', '最短5位，最长20位');
    }

    if (verifyCode.value === '') {
        errorToast('verifyCode', '你有地方没填写喔~', '验证码不能为空');
    } else if (verifyCode.value.length !== 6) {
        errorToast('verifyCode', '验证码错误', '请检查验证码是否正确');
    }

    if (!agreed.value) {
        errorToast('agreed', '未同意用户协议', '请阅读并同意用户协议');
    }
};

const registerAccount = () => {
    checked.value = true;
    verifyData();
    checkRegisterAccountInfo();
    if (!checked.value) {
        return;
    }

    request({
        method: 'POST',
        url: '/auth/accountRegister',
        data: {
            usePhone: usePhone.value,
            verifyCode: verifyCode.value,
            accountLoginId: accountLoginId.value,
            accountLoginPassword: btoa(SHA256(accountLoginPassword.value).toString()),
            accountPhone: accountPhone.value,
            accountEmail: accountEmail.value
        }
    }).then((response) => {
        toast.add(responseToastConfig(response));
    });
};

const verifyData = () => {
    if (usePhone.value) {
        if (accountPhone.value === '') {
            errorToast('accountPhone', '你有地方没填写喔~', '手机号不能为空');
        } else if (!accountPhoneRegex.test(accountPhone.value)) {
            errorToast('accountPhone', '手机号不合规', '仅支持中国大陆(+86)，请检查手机号是否正确');
        }
    } else {
        console.log(2);
        if (accountEmail.value === '') {
            errorToast('accountEmail', '你有地方没填写喔~', '邮箱不能为空');
        } else if (!accountEmailRegex.test(accountEmail.value)) {
            errorToast('accountEmail', '邮箱不合规', '请检查邮箱格式是否正确');
        }
    }
};

const getVerifyCode = () => {
    checked.value = true;
    verifyData();
    if (!checked.value) {
        return;
    }

    request({
        method: 'POST',
        url: '/auth/getVerifyCode',
        data: {
            usePhone: usePhone.value,
            accountPhone: accountPhone.value,
            accountEmail: accountEmail.value
        }
    }).then((response) => {
        toast.add(responseToastConfig(response));
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
                    <div class="flex flex-row text-left">
                        <LogoSvg/>
                        <div class="text-left mb-5 ml-4">
                            <div class="w-auto text-900 text-xl font-bold mb-2">{{ globalConfig.appName }}</div>
                            <div style="width: 256px; height: 20px">
                                <span class="text-600 font-bold ayou"
                                      style="transition: opacity 0.5s">Let‘s get started</span>
                            </div>
                        </div>
                    </div>

                    <div class="pt-3">
                        <span class="p-float-label mb-5">
                            <InputText id="accountLoginId" v-model="accountLoginId" class="w-full p-3"/>
                            <label class="text-600 font-bold ml-1" for="username">Account ID</label>
                        </span>

                        <span class="p-float-label mb-5">
                            <Password id="accountLoginPassword" v-model="accountLoginPassword" :toggleMask="true"
                                      class="w-full" inputClass="w-full p-3 font-bold" mediumLabel="适中"
                                      prompt-label="输入您的密码" strongLabel="安全" weakLabel="过于简单">
                                <template #header>
                                    <label class="text-600 font-bold pb-2">我们不会存储您的明文密码</label>
                                    <br/>
                                    <label class="text-600 font-bold">您的密码将在本地经过加密与二次编码后上传</label>
                                </template>
                                <template #footer>
                                    <Divider class="my-2"/>
                                    <p class="mb-0">密码建议</p>
                                    <ul class="pl-3 ml-2 mt-2 mb-0" style="line-height: 1.5">
                                        <li>至少一个小写字母</li>
                                        <li>至少一个大写字母</li>
                                        <li>至少一个数字</li>
                                        <li>最小8个字符</li>
                                    </ul>
                                </template>
                            </Password>
                            <label class="text-600 font-bold ml-1" for="accountLoginPassword">Password</label>
                        </span>

                        <div class="p-inputgroup mb-5" style="height: 47px">
                            <span class="p-float-label">
                                <InputText v-if="!usePhone" id="accountEmail" v-model="accountEmail"
                                           class="p-3 text-700 font-bold"/>
                                <InputText v-else id="accountPhone" v-model="accountPhone"
                                           class="p-3 text-700 font-bold"/>
                                <label v-if="!usePhone" class="text-600 font-bold ml-1" for="verifyCode">Email</label>
                                <label v-else class="text-600 font-bold ml-1" for="verifyCode">Phone</label>
                            </span>
                            <Button :label="usePhone ? '使用 Email' : '使用手机号'" class="w-5" outlined
                                    @click="usePhone = !usePhone"/>
                        </div>

                        <div class="p-inputgroup mb-4" style="height: 47px">
                            <span class="p-float-label">
                                <InputText id="verifyCode" v-model="verifyCode" class="p-3 text-700 font-bold"
                                           maxlength="6"/>
                                <label class="text-600 font-bold ml-1" for="verifyCode">verifyCode</label>
                            </span>
                            <Button class="w-5" label="获取验证码" outlined @click="getVerifyCode()"/>
                        </div>

                        <div class="flex align-items-center justify-content-between pt-1 mb-3 gap-5">
                            <div class="flex align-items-center">
                                <Checkbox id="agreed" v-model="agreed" binary class="mr-2"></Checkbox>
                                <label for="agreed">同意用户协议</label>
                            </div>
                            <a class="font-medium no-underline ml-2 text-right cursor-pointer"
                               style="color: var(--primary-color)"
                               @click="router.push({ path: '/auth/login' })">返回登录</a>
                        </div>
                        <Button class="w-full p-3 text-xl" label="注册" @click="registerAccount()"></Button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
