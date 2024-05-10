<template>
  <!-- 1. 운동 카테고리 설정하는 영역 -->
  <div>
    <p class="d-flex m-3">1. 운동 선택</p>
    <div class="exercise-grid">
      <div class="exercise-item" v-for="category in store.categoryList" :key="category.id">
        <input type="radio" class="btn-check" :id="'btnradio' + category.id" name="exercise" v-model="selectedExercise" :value="category.id">
        <label class="btn btn-outline-secondary" :for="'btnradio' + category.id">
          <img :src="getImagePath(category.id)">
        </label>
        <p style="font-size:12px">{{ category.name }}</p>
      </div>
    </div>
    <p class="d-flex m-3">선택된 운동: {{ selectedExercise }}</p>
  </div>

  <div class="d-flex">
    <!-- 2. 날짜 시간 설정하는 영역 -->
    <div class="w-50">
      <p class="d-flex m-3">2. 시간대 선택</p>
      <div class="d-flex justify-content-around flex-wrap">
        <div class="d-flex" v-for="(day, index) in days" :key="index">
          <input type="checkbox" class="btn-check" :id="'btncheckbox' + index" name="day" v-model="isSelected[index]" :value="day">
          <label class="btn btn-outline-secondary" :for="'btncheckbox' + index">{{ day }}</label>
        </div>
      </div>
      
      <!-- 시간 입력 -->
      <div class="d-flex justify-content-around flex-wrap">

        <!-- 시작 시간 입력 -->
        <div class="m-1">
          <div class="m-2">시작 시간</div>
          <input type="time" class="form-control" v-model="startTime">
        </div>
        <!-- 종료 시간 입력 -->
        <div class="m-1">
          <div class="m-2">종료 시간</div></br>
          <input type="time" class="form-control" v-model="endTime">
        </div>

      </div>
    </div>

    <!-- 3. 거리 범위 설정하는 영역 -->
    <div class="w-50">
      <p class="d-flex m-3">3. 거리 범위 선택</p>
    </div>
  </div>

  <button class="btn btn-secondary mt-4" type="submit">검색하기</button>
  
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useCategoryStore } from "@/stores/categoryStore";

const store = useCategoryStore()
onMounted(() => {
    store.getCategoryList()
})
const getImagePath = (idx) => `src/assets/sports/${idx}.png`;

const selectedExercise = ref(0);

const days = ref(['일', '월', '화', '수', '목', '금', '토']);
const isSelected = ref([false, false, false, false, false, false, false])
const startTime = ref('');
const endTime = ref('');

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
