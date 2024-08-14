<script setup>
import {globalConfig} from '../service/globalQuote';
</script>

<template>
    <div class="layout-footer">
        <img src="/images/logo.svg" alt="Logo" height="20" class="mr-2" />
        by
        <span class="font-medium ml-2">{{ globalConfig.appName }}</span>
    </div>
</template>
<style lang="scss" scoped></style>
