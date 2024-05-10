<template>
  <div class="custom-container text-center">
    <HeaderNav />
    <RouterView />
  </div>
</template>

<script setup>
import HeaderNav from './components/common/HeaderNav.vue';

import { RouterView } from 'vue-router';
</script>

<style scoped>
.custom-container {
  max-width: 1000px; /* 원하는 최대 너비로 설정 */
  margin-left: auto;
  margin-right: auto;
}
</style>