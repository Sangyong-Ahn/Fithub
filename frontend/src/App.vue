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
import { useRoute } from 'vue-router';
import { watch } from 'vue';

const route = useRoute();

watch(route, (newRoute, oldRoute)=>{
  window.scrollTo(0,0);
})


</script>

<style scoped>

</style>