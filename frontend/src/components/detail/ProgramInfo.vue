<template>
  <div class="border m-3 rounded-4 bg-white">
    <!-- 상단 -->
    <div class="d-flex justify-content-between align-items-center mx-5">
      <div class="fs-3 m-3">{{ store.program.title }}</div>
      <div class="text-end m-3">
        <div>예약 기간: {{ getDate(store.program.reservationStartDate) }} ~ {{ getDate(store.program.reservationEndDate) }}</div>
        <div>프로그램 기간: {{ getDate(store.program.programStartDate) }} ~ {{ getDate(store.program.programEndDate) }}</div>
      </div>
    </div>
    <!-- 내용 -->
    <div class="d-flex justify-content-around m-3 mt-0">
      <img class="border rounded-4" :src="imgUrl" />
      <div class="border rounded-4 custom-width">
        <div v-for="time in computedTimes" :key="time.id">
          <div class="d-flex mx-4 mt-3 mb-2">
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
            <div :class="{ 'text-body-tertiary': !time.friday }">
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
                          'btn-secondary': !isReservationPeriod
                        }"
                        :disabled="!time.reserved && (isFull(time) || !isReservationPeriod)"
                        @click="time.reserved ? deleteMatch(time.id) : insertMatch(time.id)">
                  <div v-if="time.reserved">
                    예약 취소
                  </div>
                  <div v-else-if="!isReservationPeriod && currentDate.value < reservationStartDate">
                    오픈 전
                  </div>
                  <div v-else-if="!isReservationPeriod && currentDate.value > reservationEndDate">
                    마감
                  </div>
                  <div v-else-if="isFull(time)">
                    마감
                  </div>
                  <div v-else>
                    {{ time.price.toLocaleString() }}원
                  </div>
                </button>
              </div>
              <div class="ms-2 text-center" style="width:150px">
                <span class="fs-6">{{ time.reserveNum }} / {{ time.capacity }}</span>
                <div class="progress">
                  <div class="progress-bar progress-bar-striped progress-bar-animated" role="progressbar" :class="{ 'bg-success': time.reserveNum !== time.capacity, 'bg-danger': time.reserveNum === time.capacity }" :style="{ width: (time.reserveNum / time.capacity * 100) + '%' }">
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, watch, onMounted, computed } from "vue";
import { useRoute } from 'vue-router'
import { useProgramStore } from "@/stores/programStore";
import { useUserStore } from "@/stores/userStore";
import { useMatchStore } from "@/stores/matchStore";

const store = useProgramStore()
const userStore = useUserStore()
const matchStore = useMatchStore()
const route = useRoute();

let imgUrl = store.program.thumbnail;
const img = new Image();

new Promise((resolve)=>{
  img.src = imgUrl;
  img.onload = () => resolve()
  img.onerror = () => {
    imgUrl = `/src/assets/common/thumbnail/${store.program.id}.jpg`
    resolve();
  }
})

const computedTimes = computed(() => {
  if (!store.program.times) return store.program.times;
  const newTimes = store.program.times.map(time => {
    let reserved = false;
    if (!userStore.loginUser) reserved = false; // 로그인한 사용자가 없으면 예약된 것으로 처리하지 않음
    for (const match of matchStore.matchList) {
      if (match.timeId === time.id) {
        reserved = true; // 예약된 경우 true 반환
        break;
      }
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

const currentDate = ref(new Date());

const reservationStartDate = computed(() => new Date(store.program.reservationStartDate));
const reservationEndDate = computed(() => new Date(store.program.reservationEndDate));

const isReservationPeriod = computed(() => {
  return currentDate.value >= reservationStartDate.value && currentDate.value <= reservationEndDate.value;
});

onMounted(() => {
  if (userStore.loginUser) {
    matchStore.getMatchListByUser(userStore.loginUser.id);
  }
});

const insertMatch = (timeId) => {
  if (!userStore.loginUser) {
    alert("로그인이 필요합니다.");
    return;
  }
  if (userStore.isMentor) {
    alert("일반 회원 계정으로만 신청이 가능합니다.");
    return;
  }
  matchStore.insertMatch({ timeId: timeId, mentorId: store.program.mentorId, userId: userStore.loginUser.id })
};

const deleteMatch = (timeId) => {
  const match = matchStore.matchList.find(m => m.timeId === timeId && m.userId === userStore.loginUser.id);
  if (match) {
    matchStore.deleteMatch(match.id)
  } else {
    alert("예약을 찾을 수 없습니다.");
  }
};

watch(() => matchStore.matchList, (newMatchList, oldMatchList) => {
  store.getProgram(route.params.id);
}, { deep: true });

</script>

<style scoped>
img {
  width: 250px;
  object-fit: contain;
  border: 1px solid black !important;
}
.custom-width {
  width: 60%;
  min-height: 150px;
}
.bg-success {
    background-color: lightgreen !important;
}

</style>
