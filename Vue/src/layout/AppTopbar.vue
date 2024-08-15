<script setup>
import { computed, onBeforeUnmount, onMounted, ref } from 'vue';
import { useLayout } from '@/service/layout';
import { globalConfig } from '@/service/globalQuote';

const { switchDarkTheme, layoutConfig, onMenuToggle } = useLayout();

const outsideClickListener = ref(null);
const topbarMenuActive = ref(false);

onMounted(() => {
    bindOutsideClickListener();
});

onBeforeUnmount(() => {
    unbindOutsideClickListener();
});

const onTopBarMenuButton = () => {
    topbarMenuActive.value = !topbarMenuActive.value;
};
const topbarMenuClasses = computed(() => {
    return {
        'layout-topbar-menu-mobile-active': topbarMenuActive.value
    };
});

const bindOutsideClickListener = () => {
    if (!outsideClickListener.value) {
        outsideClickListener.value = (event) => {
            if (isOutsideClicked(event)) {
                topbarMenuActive.value = false;
            }
        };
        document.addEventListener('click', outsideClickListener.value);
    }
};
const unbindOutsideClickListener = () => {
    if (outsideClickListener.value) {
        document.removeEventListener('click', outsideClickListener);
        outsideClickListener.value = null;
    }
};
const isOutsideClicked = (event) => {
    if (!topbarMenuActive.value) return;

    const sidebarEl = document.querySelector('.layout-topbar-menu');
    const topbarEl = document.querySelector('.layout-topbar-menu-button');

    return !(sidebarEl.isSameNode(event.target) || sidebarEl.contains(event.target) || topbarEl.isSameNode(event.target) || topbarEl.contains(event.target));
};
const logoUrl = computed(() => {
    return `images/${layoutConfig.darkTheme.value ? 'day' : 'night'}.svg`;
});
</script>

<template>
    <div class="layout-topbar">
        <div class="layout-topbar-logo" @click="onMenuToggle()">
            <img alt="logo" src="/images/logo.svg"/>
            <span class="pl-2">{{ globalConfig.appName }}</span>
        </div>

        <button class="p-link layout-menu-button layout-topbar-button ayou" @click="onMenuToggle()">
            <i class="pi pi-bars"></i>
        </button>

        <button class="p-link layout-topbar-menu-button layout-topbar-button" @click="onTopBarMenuButton()">
            <i class="pi pi-ellipsis-v"></i>
        </button>

        <div :class="topbarMenuClasses" class="layout-topbar-menu">
            <Button class="p-2 layout-menu-button" rounded style="width: 41px; height: 41px" text
                    @click="switchDarkTheme()">
                <img :src="logoUrl" alt="s" height="25" width="25"/>
            </Button>
            <Button aria-label="Bookmark" class="layout-menu-button" icon="pi pi-bookmark" rounded severity="secondary" size="large"
                    text/>
            <Button aria-label="Search" class="layout-menu-button" icon="pi pi-search" rounded severity="success" size="large"
                    text/>
        </div>
    </div>
</template>

<style lang="scss" scoped></style>
