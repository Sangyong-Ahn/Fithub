<template>
  <!-- 1. 운동 카테고리 설정하는 영역 -->
  <div class="text-center">
    <p class="d-flex m-3">1. 운동 선택</p>
    <div class="exercise-grid">
      <div class="exercise-item" v-for="category in categoryStore.categoryList" :key="category.id">
        <input type="radio" class="btn-check" :id="'btnradio' + category.id" name="exercise" v-model="categoryId" :value="category.id">
        <label class="btn btn-outline-secondary" :for="'btnradio' + category.id">
          <img :src="getImagePath(category.id)">
        </label>
        <p style="font-size:12px">{{ category.name }}</p>
      </div>
    </div>
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

  <button class="btn btn-secondary mt-4 d-block mx-auto" @click="programSearch" type="submit">검색하기</button>
  
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useCategoryStore } from "@/stores/categoryStore";
import { useProgramStore } from '@/stores/programStore'

// 필터 내용에 관한것
const categoryStore = useCategoryStore()
onMounted(() => {
  categoryStore.getCategoryList()
})
const getImagePath = (idx) => `src/assets/sports/${idx}.png`;



const days = ref(['일', '월', '화', '수', '목', '금', '토']);
const isSelected = ref([false, false, false, false, false, false, false])
const startTime = ref('00:00');
const endTime = ref('23:59');
  

// 보낼 필터 객체에 대한것
const programStore = useProgramStore()

const categoryId = ref(0);
const order = ref("createdAt")
const direction = ref("asc")

const programSearch = function () {
  programStore.programSearch({
      categoryId: categoryId.value,
      sunday: isSelected.value[0],
      monday: isSelected.value[1],
      tuesday: isSelected.value[2],
      wednesday: isSelected.value[3],
      thursday: isSelected.value[4],
      friday: isSelected.value[5],
      saturday: isSelected.value[6],
      startTime: startTime.value+":00",
      endTime: endTime.value+":00",
      order: order.value,
      direction: direction.value
  })
}
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
