<template>
  <!-- 상단 -->
  <div class="d-flex justify-content-between border">
    <div class="fs-3 ms-3">{{ store.program.title }}</div>
    <div class="text-end">
        <div>예약 기간: {{ getDate(store.program.reservationStartDate) }} ~ {{ getDate(store.program.reservationEndDate) }}</div>
        <div>프로그램 기간: {{ getDate(store.program.reservationStartDate) }} ~ {{ getDate(store.program.reservationEndDate) }}</div>
    </div>
  </div>
  <!-- 내용 -->
  <div class="d-flex justify-content-around border m-3">
    <img class="border" src="@/assets/common/thumbnail-demo.jpg"/>
    <div class="border rounded w-50">
      <div v-for="time in computedTimes" :key="time.id">
        <div class="d-flex m-3">
          <div :class="{ 'text-body-tertiary': !time.sunday }">
            <div class="me-1 fs-5" v-if="time.sunday">일</div>
            <div class="me-1 fs-5" v-else>일</div>
          </div>
          <div :class="{ 'text-body-tertiary': !time.monday }">
            <div class="me-1 fs-5" v-if="time.monday">월</div>
            <div class="me-1 fs-5" v-else>월</div>
          </div>
          <div :class="{ 'text-body-tertiary': !time.tuesday }">
            <div class="me-1 fs-5" v-if="time.tuesday">화</div>
            <div class="me-1 fs-5" v-else>화</div>
          </div>
          <div :class="{ 'text-body-tertiary': !time.wednesday }">
            <div class="me-1 fs-5" v-if="time.wednesday">수</div>
            <div class="me-1 fs-5" v-else>수</div>
          </div>
          <div :class="{ 'text-body-tertiary': !time.thursday }">
            <div class="me-1 fs-5" v-if="time.thursday">목</div>
            <div class="me-1 fs-5" v-else>목</div>
          </div>
          <div :class="{ 'text-body-tertiaryight': !time.friday }">
            <div class="me-1 fs-5" v-if="time.friday">금</div>
            <div class="me-1 fs-5" v-else>금</div>
          </div>
          <div :class="{ 'text-body-tertiary': !time.saturday }">
            <div class="me-1 fs-5" v-if="time.saturday">토</div>
            <div class="me-1 fs-5" v-else>토</div>
          </div>
        </div>
        <div class="d-flex justify-content-between mx-4">
          <div class="d-flex align-items-center">
            <div class="border border-secondary rounded px-3 py-1 me-2">
              {{ time.startTime.slice(0, 5) }}
            </div>
            <span class="fs-5 text-muted">~</span>
            <div class="border border-secondary rounded px-3 py-1 ms-2">
              {{ time.endTime.slice(0, 5) }}
            </div>
          </div>
          <div class="d-flex align-items-center">
            <div class="me-2">
              <button class="btn btn-md" :class="{ 
                          'btn-outline-secondary': time.reserved || !isFull(time),
                        }"
                      :disabled="isFull(time)">
                <template v-if="time.reserved">
                  예약 취소
                </template>
                <template v-else-if="isFull(time)">
                  마감
                </template>
                <template v-else>
                  {{ time.price }}원
                </template>
              </button>
            </div>
            <div>
              <span class="fs-4">{{ time.reserveNum }} / {{ time.capacity }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import { useRoute } from 'vue-router'
import { useProgramStore } from "@/stores/programStore";
import { useUserStore } from "@/stores/userStore";
import { useMatchStore } from "@/stores/matchStore";

const store = useProgramStore()
const userStore = useUserStore()
const matchStore = useMatchStore()
const route = useRoute();

const computedTimes = computed(()=>{
  console.log('computed')
  if (!store.program.times) return store.program.times;
  const newTimes = store.program.times.map(time=>{
    let reserved = false;
    if (!userStore.loginUser) reserved = false; // 로그인한 사용자가 없으면 예약된 것으로 처리하지 않음
    for (const match of matchStore.matchList) {
      if (match.timeId === time.id) reserved =  true; // 예약된 경우 true 반환
      break;
    }
    return {
      ...time,
      reserved
    }
  });
 
  return newTimes
})

onMounted(() => {
  store.getProgram(route.params.id)

})

const getDate = function(dateString) {
    if (!dateString) return "";
    return dateString.split('T')[0];
};

const isFull = function(time) {
  return time.reserveNum === time.capacity;
};


onMounted(() => {


  if (userStore.loginUser) {
    console.log(userStore.loginUser.id)
    matchStore.getMatchListByUser(userStore.loginUser.id);
  }
});

</script>

<style scoped>
img {
  width: 250px;
}
</style>