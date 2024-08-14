<script setup>
defineProps({
  displayHeight: {
    type: Number,
    required: false,
    default: 50
  },
  marginBottom: {
    type: String,
    required: false,
    default: 'mb-3'
  }
});
</script>

<template>
    <img alt="Image" :class="marginBottom" :height="displayHeight" src="/images/logo.svg" />
</template>

<style scoped lang="scss"></style>
