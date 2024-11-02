<script lang="ts" setup>
import { useToast } from 'primevue/usetoast';
import { getParameterByName, globalConfig } from '@/service/globalQuote';
import request from '@/service/request';
import LogoSvg from '@/components/LogoSvg.vue';
import { AxiosResponse } from 'axios';

const toast = useToast();

const doConfirm = () => {
    request({
        method: 'POST',
        url: '/oauth2/doConfirm',
        params: {
            client_id: '1001',
            scope: getParameterByName('scope'),
            // 以下四个参数必须一起出现
            build_redirect_uri: true,
            response_type: 'code',
            redirect_uri: getParameterByName('redirect_uri'),
            state: '',
        },
    }).then((res: AxiosResponse) => {
        console.log(res);
        if (res.data.code === 200) {
            location.href = res.data.redirect_uri;
        }
        // window.location.href = res.data.data;
    });
};

const doDenied = () => {

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
                            <label class="font-bold ml-1 text-600">授权确认</label>
                        </div>
                    </div>
                    <div class="pt-3">
                        <div class="flex flex-column">
                            <label class="font-bold ml-1 text-600">应用ID：{{ getParameterByName('clientId') }}</label>
                            <label class="font-bold ml-1 text-600">授权范围：{{ getParameterByName('scope') }}</label>
                        </div>

                        <div class="flex align-items-center justify-content-between pt-3 mb-3 gap-5">
                            <Button class="w-full p-3 text-xl" label="确认" @click="doConfirm()"></Button>
                            <Button class="w-full p-3 text-xl" label="拒绝" @click="doDenied()"></Button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped></style>
