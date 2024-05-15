<template>
  <div v-if="mentorInfo" class="text-center border m-3 rounded-4 bg-white p-3">
    <img class="border m-3 mb-0" src="@/assets/common/thumbnail-demo.jpg" style="border-radius:50%; width:100px">
    <div class="fs-5">{{ mentorInfo.name }} 멘토</div>
    <div class="d-flex justify-content-center align-items-center">
      <div class="stars me-2">
        <span v-for="star in 5" :key="star" :class="{ 'filled': star <= mentorInfo.reviewAvgScore }">&#9733;</span>
      </div>
      <div>{{ mentorInfo.reviewAvgScore.toFixed(1) }} ({{ mentorInfo.reviews.length }})</div>
    </div>
    <div class="d-flex justify-content-center">
      <div class="border border-3 rounded-4 w-75 my-3 p-3" style="height:200px;">
        개인 소개글 나오는 부분{{ mentorInfo.content }}
      </div>
    </div>
    <div class="d-flex justify-content-center gap-5 my-3">
      <button class="btn btn-outline-secondary btn-lg px-5">후기 작성</button>
      <button class="btn btn-outline-secondary btn-lg px-5">문의하기</button>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue';
import { useProgramStore } from '@/stores/programStore';

const store = useProgramStore();

const mentorInfo = computed(() => {
  return store.program?.mentorInfo || null;
});
</script>

<style scoped>
.stars {
  color: gray;
}

.stars span {
  font-size: 20px; /* Adjust the size as needed */
}

.stars .filled {
  color: gold;
}
</style>
