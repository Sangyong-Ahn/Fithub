<template>
  <!-- 1. 운동 카테고리 설정하는 영역 -->
  <div class="text-center">
    <p class="d-flex m-3">1. 운동 선택</p>
    <div class="exercise-grid">
      <div class="exercise-item" v-for="category in categoryStore.categoryList" :key="category.id">
        <input type="radio" class="btn-check" :id="'btnradio' + category.id" name="exercise" v-model="categoryId" :value="category.id">
        <label class="btn btn-outline-secondary btn-category" :for="'btnradio' + category.id">
          <img :src="getImagePath(category.id)">
        </label>
        <p style="font-size:12px">{{ category.name }}</p>
      </div>
    </div>
  </div>

  <div class="d-flex gap-2">
    <!-- 2. 날짜 시간 설정하는 영역 -->
    <div class="w-50">
      <p class="d-flex m-3">2. 시간대 선택</p>
      <div class="container">
        <div class="row">
          <div class="col-md-3 col-lg-1 px-3 py-1" v-for="(day, index) in days" :key="index">
            <input type="checkbox" class="btn-check " :id="'btncheckbox' + index" name="day" v-model="isSelected[index]" :value="day">
            <label class="btn btn-sm btn-outline-secondary rounded-5" :for="'btncheckbox' + index">{{ day }}</label>
          </div>
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
          <div class="m-2">종료 시간</div>
          <input type="time" class="form-control" v-model="endTime">
        </div>

      </div>
    </div>

    <!-- 3. 거리 범위 설정하는 영역 -->
    <div class="w-50">
      <p class="d-flex m-3 mb-0">3. 거리 범위 선택</p>
      <small class="ms-3 mb-3">(내 위치를 기준으로 검색하시려면 로그인이 필요합니다)</small>
      <div class="mt-3 btn-group btn-group-toggle d-flex flex-wrap" data-toggle="buttons">
        <label class="btn btn-outline-secondary rounded-pill mx-2 my-1" v-for="(distance, index) in distances" :key="distance.value" :class="{ active: distance.value === selectedDistance }">
          <input type="radio" class="button-check visually-hidden" name="distance" :value="distance.value" v-model="selectedDistance">
          <span class="button-style">{{ distance.label }}</span>
        </label>
      </div>
    </div>
  </div>

  <div class="d-flex justify-content-center mt-5">
    <button class="btn btn-outline-secondary rounded-5 me-4" @click="resetFilters">초기화</button>
    <button class="btn btn-outline-secondary rounded-5" @click="programSearch" type="submit">검색하기</button>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useCategoryStore } from "@/stores/categoryStore";
import { useProgramStore } from '@/stores/programStore'
import { useUserStore } from '@/stores/userStore';

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
const defaultLatitude = 37.5004462;
const defaultLongitude = 127.037236;
const distances = [
  { value: 1000, label: '1km 이내' },
  { value: 3000, label: '3km 이내' },
  { value: 5000, label: '5km 이내' },
  { value: 10000, label: '10km 이내' },
  { value: 15000, label: '15km 이내' },
  { value: 20000, label: '20km 이내' }
];
const selectedDistance = ref(0); // 기본값은 0이고

// 보낼 필터 객체에 대한것
const programStore = useProgramStore()
const userStore = useUserStore()

const categoryId = ref(0);
const order = ref("createdAt")
const direction = ref("asc")

const programSearch = function () {
  const latitude = userStore.loginUser ? userStore.loginUser.latitude : defaultLatitude;
  const longitude = userStore.loginUser ? userStore.loginUser.longitude : defaultLongitude;

  programStore.programSearch({
    categoryId: categoryId.value,
    sunday: isSelected.value[0],
    monday: isSelected.value[1],
    tuesday: isSelected.value[2],
    wednesday: isSelected.value[3],
    thursday: isSelected.value[4],
    friday: isSelected.value[5],
    saturday: isSelected.value[6],
    startTime: startTime.value + ":00",
    endTime: endTime.value + ":00",
    latitude: latitude,
    longitude: longitude,
    distance: selectedDistance.value,
    order: order.value,
    direction: direction.value
  })
}

const resetFilters = function () {
  categoryId.value = 0;
  isSelected.value = [false, false, false, false, false, false, false];
  startTime.value = '00:00';
  endTime.value = '23:59';
  selectedDistance.value = 0;
}
</script>

<style scoped>
img {
  width: 40px;
}

.btn-category {
  padding: 3px;
  margin: 15px;
  border-radius: 5px;
  border-color: gray;
}



.exercise-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(80px, 1fr));
}
.exercise-item {
  width: 100%;
}

input {
  background-color: var(--bs-body-bg) !important;
}

</style>
