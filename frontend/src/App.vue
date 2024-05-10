<template>
  <div id="app">
    <div class="custom-container text-center">
      <HeaderNav />
    </div>
      <RouterView />
  </div>
</template>

<script setup>
import HeaderNav from './components/common/HeaderNav.vue';

import { RouterView } from 'vue-router';
</script>

<style scoped>
@app {
}
</style>