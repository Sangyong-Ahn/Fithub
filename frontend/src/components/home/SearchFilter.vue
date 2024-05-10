<template>
  <!-- 1. 운동 카테고리 설정하는 영역 -->
  <div>
    <p class="d-flex m-3">1. 운동 선택</p>
    <div class="exercise-grid">
      <div class="exercise-item" v-for="idx in 20" :key="idx">
        <input type="radio" class="btn-check" :id="'btnradio' + idx" name="exercise" v-model="selectedExercise" :value="idx">
        <label class="btn btn-outline-secondary" :for="'btnradio' + idx">
          <img src="@/assets/exicon.png"> <!-- 이미지 추가 -->
        </label>
      </div>
    </div>
    <p class="d-flex m-3">선택된 운동: {{ selectedExercise }}</p>
  </div>

  <div class="d-flex">
    <!-- 2. 날짜 시간 설정하는 영역 -->
    <div class="w-50">
      <p class="d-flex m-3">2. 시간대 선택</p>
      <div class="d-flex flex-wrap">
        <div class="d-flex" v-for="(day, index) in days" :key="index">
          <input type="checkbox" class="btn-check" :id="'btncheckbox' + index" name="day" v-model="isSelected[index]" :value="day">
          <label class="btn btn-outline-secondary" :for="'btncheckbox' + index">{{ day }}</label>
        </div>
      </div>

      <!-- 날짜 체크용 (나중에 지우삼) -->
      <div class="d-flex flex-wrap">
        <div class="d-flex m-2" v-for="(day, index) in days" :key="index">
          <p>{{ isSelected[index] }}</p>
        </div>
      </div>
      
      

    </div>

    <!-- 3. 거리 범위 설정하는 영역 -->
    <div class="w-50">
      <p class="d-flex m-3">3. 거리 범위 선택</p>
    </div>
  </div>
  
</template>

<script setup>
import { ref } from 'vue';

const selectedExercise = ref(0);

const days = ref(['일', '월', '화', '수', '목', '금', '토']);
const isSelected = ref([false, false, false, false, false, false, false])

</script>

<style scoped>
img {
  width: 40px;
}
.btn-outline-secondary {
  padding: 3px;
  margin: 15px;
  border-radius: 0;
}
.exercise-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(80px, 1fr));
}
.exercise-item {
  width: 100%;
}
</style>
