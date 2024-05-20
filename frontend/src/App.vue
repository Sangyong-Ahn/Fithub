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
import { useUserStore } from '@/stores/userStore';

import { RouterView } from 'vue-router';
import { useRoute } from 'vue-router';
import { onMounted, watch } from 'vue';

const route = useRoute();
const userStore = useUserStore();
userStore.loadSessionStorage();

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

.btn-check:checked+.btn, .btn.active, .btn.show, .btn:first-child:active, :not(.btn-check)+.btn:active .btn:hover {
  border-color: lightgreen;
  background-color: gray;

  color:white;
}

.btn:hover {
  border-color: lightgreen;
}


.bg-light {
  background-color: black !important;
}

@font-face {
    font-family: 'KCC-Hanbit';
    src: url('https://fastly.jsdelivr.net/gh/projectnoonnu/2403-2@1.0/KCC-Hanbit.woff2') format('woff2');
    font-weight: normal;
    font-style: normal;
}

.bg-white {
  border: 1px solid lightgreen !important;
}

.bg-white, .bg-white > * {
  background-color: black !important
}

body {
  font-family: 'KCC-Hanbit';
}

.form-control:focus, .form-check-input:focus {
  border-color: lightgreen;
  box-shadow: none;
} 

.form-check-input:checked {
  border-color: lightgreen !important;
}

input {
  background-color: black !important;
}

body {
  background-color: rgb(18, 18, 18) !important;
}

th {
  background-color: var(--bs-body-bg);
}
td {
  background-color: black;
}

th, td {
  border: 1px solid #ddd;
  padding: 8px;
}

</style>
