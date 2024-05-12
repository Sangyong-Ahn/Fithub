<template>
  <div>
    <div class="custom-container text-center">
      <HeaderNav />
    </div>
      <RouterView />
  </div>
</template>

<script setup>
import HeaderNav from '@/components/header/HeaderNav.vue';

import { RouterView } from 'vue-router';
</script>

<style scoped>

</style>