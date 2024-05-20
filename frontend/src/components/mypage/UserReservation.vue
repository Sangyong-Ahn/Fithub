<template>
  <div class="w-100 m-3">
    <h4 class="mb-4">예약 정보</h4>
    <table class="text-center">
      <thead>
        <tr>
          <th>프로그램</th>
          <th>기간</th>
          <th>시간대</th>
          <th>멘토 이름</th>
          <th>가격</th>
          <th>인원</th>
          <th>예약 취소</th>
          <th>후기</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="match in matchStore.matchList" :key="match.id">
          <td>{{ match.programInfo.title }}</td>
          <td>
            <div>
              {{ formatDate(match.programInfo.programStartDate) }}
            </div>
            ~ 
            <div>
              {{ formatDate(match.programInfo.programEndDate) }}
            </div>
          </td>
          <td>
            <div>
              <span v-if="match.timeInfo.sunday">일 </span>
              <span v-if="match.timeInfo.monday">월 </span>
              <span v-if="match.timeInfo.tuesday">화 </span>
              <span v-if="match.timeInfo.wednesday">수 </span>
              <span v-if="match.timeInfo.thursday">목 </span>
              <span v-if="match.timeInfo.friday">금 </span>
              <span v-if="match.timeInfo.saturday">토 </span>
            </div>
            {{ match.timeInfo.startTime.slice(0, -3) }} ~ {{ match.timeInfo.endTime.slice(0, -3) }}
          </td>
          <td>{{ match.mentorInfo.name }}</td>
          <td>{{ match.timeInfo.price.toLocaleString() }}원</td>
          <td>{{ match.timeInfo.reserveNum }} / {{ match.timeInfo.capacity }}</td>
          <td><button class="btn btn-outline-danger" @click="cancelMatch(match.id)">취소</button></td>
          <td>
            <router-link :to="{ name: 'programDetail', params: { id: match.programInfo.id }}">
              <button class="btn btn-outline-secondary">작성</button>
            </router-link>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>
  
<script setup>
import { onMounted } from 'vue'
import { useMatchStore } from '@/stores/matchStore'
import { useUserStore } from '@/stores/userStore'

const matchStore = useMatchStore()
const userStore = useUserStore()
const { loginUser } = userStore

onMounted(() => {
  if (loginUser && loginUser.id) {
    matchStore.getMatchListByUser(loginUser.id)
  }
})

const formatDate = (dateString) => {
  const date = new Date(dateString)
  return date.toLocaleDateString()
}

const cancelMatch = (id) => {
  matchStore.deleteMatch(id)
}

</script>
  
<style>
table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  border: 1px solid #ddd;
  padding: 8px;
}
</style>