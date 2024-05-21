<template>
  <div class="w-100 m-3">
    <h4 class="mb-4">프로그램 리스트</h4>
    <div class="accordion" id="programAccordion">
      <div v-for="program in mentorPrograms" :key="program.id" class="accordion-item">
        <h2 class="accordion-header" :id="'heading' + program.id">
          <button class="" type="button" data-bs-toggle="collapse" :data-bs-target="'#collapse' + program.id" aria-expanded="true" :aria-controls="'collapse' + program.id">
            <div class="program-info">
              <div class="fs-6">{{ program.title }}</div>
              <div class="fs-6">{{ formatDate(program.programStartDate) }} ~ {{ formatDate(program.programEndDate) }}</div>
              <div class="fs-6">인원: {{ calculateTotalReservations(program.times) }} / {{ calculateTotalCapacity(program.times) }}</div>
            </div>
          </button>
        </h2>
        <div :id="'collapse' + program.id" class="accordion-collapse collapse" :aria-labelledby="'heading' + program.id" data-bs-parent="#programAccordion">
          <div class="accordion-body">
            <table class="table">
              <thead>
                <tr>
                  <th>요일</th>
                  <th>시간</th>
                  <th>예약 인원</th>
                  <th>이름</th>
                  <th>전화번호</th>
                </tr>
              </thead>
              <tbody>
                <template v-for="time in program.times" :key="time.id">
                  <tr v-if="time.users.length === 0">
                    <td>
                      <span v-if="time.monday">월 </span>
                      <span v-if="time.tuesday">화 </span>
                      <span v-if="time.wednesday">수 </span>
                      <span v-if="time.thursday">목 </span>
                      <span v-if="time.friday">금 </span>
                      <span v-if="time.saturday">토 </span>
                      <span v-if="time.sunday">일 </span>
                    </td>
                    <td>{{ time.startTime }} ~ {{ time.endTime }}</td>
                    <td>{{ time.reserveNum }} / {{ time.capacity }}</td>
                    <td colspan="2">예약된 사용자가 없습니다</td>
                  </tr>
                  <tr v-for="(user, index) in time.users" :key="user.id">
                    <template v-if="index === 0">
                      <td :rowspan="time.users.length">
                        <span v-if="time.monday">월 </span>
                        <span v-if="time.tuesday">화 </span>
                        <span v-if="time.wednesday">수 </span>
                        <span v-if="time.thursday">목 </span>
                        <span v-if="time.friday">금 </span>
                        <span v-if="time.saturday">토 </span>
                        <span v-if="time.sunday">일 </span>
                      </td>
                      <td :rowspan="time.users.length">{{ time.startTime }} ~ {{ time.endTime }}</td>
                      <td :rowspan="time.users.length">{{ time.reserveNum }} / {{ time.capacity }}</td>
                    </template>
                    <td>{{ user.name }}</td>
                    <td>{{ user.phoneNumber }}</td>
                  </tr>
                </template>
              </tbody>
            </table>
          </div>
        </div>
      </div>
      <div v-if="mentorPrograms && mentorPrograms.length === 0" class="accordion-item">
        <h2 class="accordion-header">
          <button class="accordion-button" type="button" disabled>
            프로그램이 없습니다.
          </button>
        </h2>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted } from 'vue';
import { useProgramStore } from '@/stores/programStore';
import { useUserStore } from '@/stores/userStore';

const { mentorPrograms, getMentorPrograms } = useProgramStore();
const { loginUser, isMentor } = useUserStore();

onMounted(async () => {
  if (isMentor && loginUser && loginUser.id) {
    await getMentorPrograms(loginUser.id);
  }
});

const formatDate = (dateString) => {
  const date = new Date(dateString);
  return date.toLocaleDateString();
};

const calculateTotalReservations = (times) => {
  return times.reduce((total, time) => total + time.reserveNum, 0);
};

const calculateTotalCapacity = (times) => {
  return times.reduce((total, time) => total + time.capacity, 0);
};
</script>

<style>
.program-info {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  gap: 10px;
  width:100%
}

.program-title {
  font-weight: bold;
}

.program-date {
  color: gray;
}

</style>