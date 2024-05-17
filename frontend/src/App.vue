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
import { onMounted, watch } from 'vue';

const route = useRoute();

onMounted(()=>{
  document.querySelector("html").setAttribute('data-bs-theme', 'dark')
})

watch(route, (newRoute, oldRoute)=>{
  window.scrollTo(0,0);
})


</script>

<style>
.btn {
  border-color: lightgreen;
  background-color: black;
  color:lightgreen;
}

.btn-check:checked+.btn, .btn.active, .btn.show, .btn:first-child:active, :not(.btn-check)+.btn:active {
  border-color: lightgreen;
  background-color: gray;

  color:lightgreen;
}
</style>