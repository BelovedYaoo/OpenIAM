<script lang="ts" setup>
import { onMounted, onUnmounted, reactive, ref, watch } from 'vue';
import axios from 'axios';

let timer = reactive({});

const yiyan = ref('永远都有更好，但眼下便是最好');
const apiList = ref(['/vvhan/text/love', '/yiyan', '/tenapi/yiyan', '/uapis/say']);
const apiUse = ref('');
const apiAvailability = ref(10);

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
    // 获取类名含有yiyan的元素
    const span = document.querySelector('.yiyan');
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
            // 为yiyan元素添加渐隐效果
            span.style.opacity = '0';
            // 延时执行，让渐变更平滑
            setTimeout(() => {
                yiyan.value = res.data;
                // 为yiyan元素添加渐显效果
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
        // 获取类名含有yiyan的元素
        const span = document.querySelector('.yiyan');
        // 为yiyan元素添加渐显效果
        span.style.opacity = '1';
    }
});
</script>

<template>
    <span class="text-600 font-bold yiyan" style="transition: opacity 0.5s">{{
        yiyan
    }}</span>
</template>

<style lang="scss" scoped>

</style>