<template>
  <div class="container">
    <div class="row">
      <div class="col-1"></div>
      <div class="col-10 border rounded-4 bg-light p-4 mb-5">
        <h2 class="mb-5">게시글 작성</h2>
        <!-- 프로그램 생성 폼 -->
        <form @submit.prevent="insert">

          <div class="d-flex mx-4 mb-4">
            <!-- 운동 카테고리 입력 부분 -->
            <div class="mb-3 w-25">
              <label for="categoryDropdown" class="form-label">운동 선택</label>
              <!-- 드롭다운 버튼 -->
              <div class="dropdown">
                <button class="btn btn-outline-secondary dropdown-toggle " type="button" id="categoryDropdown" data-bs-toggle="dropdown" aria-expanded="false">
                  {{ selectedCategory }}
                </button>
                <ul class="dropdown-menu scrollable-menu" aria-labelledby="categoryDropdown">
                  <li v-for="category in categories" :key="category.id" @click="selectCategory(category)">
                    <a class="dropdown-item">{{ category.name }}</a>
                  </li>
                </ul>
              </div>
            </div>
            <!-- 제목 입력 칸-->
            <div class="mb-3 w-75">
              <label for="title" class="form-label">제목</label>
              <input type="text" class="form-control" id="title" v-model="title">
            </div>
          </div>


          <div class="row">
            <div class="mb-3 col-lg col-sm-6">
              <label for="reservationStartDate" class="form-label">예약 시작일</label>
              <input type="date" class="form-control" id="reservationStartDate" v-model="reservationStartDate">
            </div>
            <div class="mb-3 col-lg col-sm-6">
              <label for="reservationEndDate" class="form-label">예약 종료일</label>
              <input type="date" class="form-control" id="reservationEndDate" v-model="reservationEndDate">
            </div>
            <div class="mb-3 col-lg col-sm-6">
              <label for="programStartDate" class="form-label">프로그램 시작일</label>
              <input type="date" class="form-control" id="programStartDate" v-model="programStartDate">
            </div>
            <div class="mb-3 col-lg col-sm-6">
              <label for="programEndDate" class="form-label">프로그램 종료일</label>
              <input type="date" class="form-control" id="programEndDate" v-model="programEndDate">
            </div>
          </div>

          <!-- 시간대 입력 -->
          <div v-for="(time, index) in times" :key="index" class="mx-4 mb-4">
            <div class="mb-3 border rounded-4 bg-white p-3">
              <label class="form-label">시간대 {{ index + 1 }}</label>
              <div class="d-flex align-items-center">
                <!-- 요일 버튼 -->
                <div v-for="day in days" :key="day.value">
                  <div class="me-3">
                    <div class="form-check form-switch">
                      <input class="form-check-input" type="checkbox" :id="day.value + index" v-model="time[day.value]">
                      <label class="form-check-label" :for="day.value + index">{{ day.label }}</label>
                    </div>
                  </div>
                </div>
              </div>
              <!-- 시작 시간과 종료 시간 입력 -->
              <div class="d-flex mt-3 justify-content-between">
                <div class="me-3">
                  <label for="startTime{{ index }}" class="form-label">시작 시간</label>
                  <input type="time" class="form-control" :id="'startTime' + index" v-model="time.startTime">
                </div>
                <div>
                  <label for="endTime{{ index }}" class="form-label">종료 시간</label>
                  <input type="time" class="form-control" :id="'endTime' + index" v-model="time.endTime">
                </div>
                <div class="me-3">
                  <label for="price{{ index }}" class="form-label">금액</label>
                  <input type="number" class="form-control" :id="'price' + index" v-model="time.price">
                </div>
                <div>
                  <label for="capacity{{ index }}" class="form-label">최대인원</label>
                  <input type="number" class="form-control" :id="'capacity' + index" v-model="time.capacity">
                </div>
              </div>
            </div>

            <!-- 시간대 제거 버튼 -->
            <div class="text-end" v-if="times.length > 1 && index !== 0">
              <button type="button" class="btn btn-sm btn-outline-danger bg-white" @click="removeTime(index)">제거하기</button>
            </div>
          </div>

          <!-- 시간대 추가 버튼 -->
          <div class="mx-4 mb-4">
            <button type="button" class="btn btn-sm btn-outline-secondary bg-white" @click="addTime">추가하기</button>
          </div>

          <!-- 나머지 입력 칸 -->
          <div class="mx-4 mb-4">
            <label for="thumbnail" class="form-label">썸네일 이미지 업로드</label>
            <input type="file" class="form-control" id="thumbnail" ref="thumbnailInput">
          </div>
          <div class="mx-4 mb-4">
            <label for="content" class="form-label">내용</label>
            <textarea class="form-control" id="content" v-model="content"></textarea>
          </div>
          <div class="mx-4 mb-4">
            <label for="youtubeUrl" class="form-label">YouTube URL</label>
            <input type="text" class="form-control" id="youtubeUrl" v-model="youtubeUrl">
          </div>
          
          <div class="mx-4 mb-4">
            <label class="form-label">장소</label>
            <div class="mb-3 border rounded-4 bg-white p-3 w-75">
              <UserMap
                :id="'programCreate'"
                :lat="latitude"
                :lng="longitude"
                :width="'100%'"
                :height="'500px'"
                :isMarkable="'true'"
                @update-lat-lng="updateLatLng"
              />
            </div>
          </div>

          <div class="mx-4 mb-4 text-end">
            <button type="submit" @click="insertProgram" class="btn btn-outline-secondary bg-white">등록하기</button>
          </div>
        </form>
      </div>
      <div class="col-1"></div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref, computed } from 'vue';
import { useCategoryStore } from '@/stores/categoryStore';
import { useProgramStore } from '@/stores/programStore';
import { useUserStore } from '@/stores/userStore';
import { getDateString } from "@/common/common.js";
import UserMap from "@/components/util/UserMap.vue";

const categoryStore = useCategoryStore();
const programStore = useProgramStore();
const userStore = useUserStore();

// 각 입력 필드에 대한 상태 변수
const categoryId = ref(0)
const title = ref('');
const reservationStartDate = ref(getDateString());
const reservationEndDate = ref(getDateString(7));
const programStartDate = ref(getDateString(7));
const programEndDate = ref(getDateString(14));
const thumbnail = ref('');
const content = ref('');
const youtubeUrl = ref('');
const latitude = ref(userStore.loginUser.latitude);
const longitude = ref(userStore.loginUser.longitude);

// 카테고리 관련 상태 변수
const categories = computed(() => categoryStore.categoryList); // 카테고리 목록
const selectedCategory = ref('운동을 선택하세요'); // 선택된 카테

// 요일 정보
const days = [
  { value: 'sunday', label: '일' },
  { value: 'monday', label: '월' },
  { value: 'tuesday', label: '화' },
  { value: 'wednesday', label: '수' },
  { value: 'thursday', label: '목' },
  { value: 'friday', label: '금' },
  { value: 'saturday', label: '토' }
];

// 시간대 정보 배열
const times = ref([{ 
  sunday: false, monday: false, tuesday: false, wednesday: false, thursday: false, friday: false, saturday: false, 
  startTime: '14:00', endTime: '15:00', price: '', capacity: ''
}]);

// 선택된 카테고리를 설정하는 메서드
const selectCategory = (category) => {
  selectedCategory.value = category.name;
  categoryId.value = category.id;
};

// 시간대 추가 버튼 클릭 시 호출되는 메서드
const addTime = () => {
  console.log(times.value[times.value.length-1])
  times.value.push({
    ...times.value[times.value.length-1]
  });
};

// 시간대 제거 버튼 클릭 시 호출되는 메서드
const removeTime = (index) => {
  if (index !== 0) {
    times.value.splice(index, 1);
  }
};

// 폼 제출 시 처리할 메서드
const insertProgram = function () {
  // 여기서 폼 데이터를 처리합니다.
  const imageInput = document.getElementById('thumbnail');

  programStore.insertProgram({
    mentorId: userStore.loginUser.id,
    categoryId: categoryId.value,
    title: title.value,
    reservationStartDate: reservationStartDate.value,
    reservationEndDate: reservationEndDate.value, 
    programStartDate: programStartDate.value, 
    programEndDate: programEndDate.value, 
    thumbnail: thumbnail.value,
    content: content.value,
    youtubeUrl: youtubeUrl.value,
    times: times.value,
    latitude: latitude.value,
    longitude: longitude.value,
  }, imageInput?.files[0])
};

const updateLatLng = (latLng) => {
  latitude.value = latLng.lat;
  longitude.value = latLng.lng;
}

onMounted(() => {
  categoryStore.getCategoryList();
});
</script>

<style scoped>
.scrollable-menu {
  max-height: 200px; /* 드롭다운의 최대 높이를 지정 */
  overflow-y: auto;
}
</style>
