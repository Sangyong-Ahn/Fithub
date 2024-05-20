<template>
    <div>
      <h1>예약 정보</h1>
      <table>
        <thead>
          <tr>
            <th>프로그램 ID</th>
            <th>멘토 ID</th>
            <th>유저 ID</th>
            <th>구매 확인</th>
            <th>생성 일시</th>
            <th>예약 취소</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="match in matchList" :key="match.id">
            <td>{{ match.timeId }}</td>
            <td>{{ match.mentorId }}</td>
            <td>{{ match.userId }}</td>
            <td>{{ match.purchaseConfirm ? 'Yes' : 'No' }}</td>
            <td>{{ formatDate(match.createdAt) }}</td>
            <td>
              <button @click="cancelMatch(match.id)">취소</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </template>
  
  <script>
  import { onMounted } from 'vue'
  import { useMatchStore } from '@/stores/matchStore'
  import { useUserStore } from '@/stores/userStore'
  
  export default {
    setup() {
      const matchStore = useMatchStore()
      const userStore = useUserStore()
      const { matchList, getMatchListByUser, deleteMatch } = matchStore
      const { loginUser } = userStore
  
      onMounted(() => {
        if (loginUser && loginUser.id) {
          getMatchListByUser(loginUser.id)
        }
      })
  
      const formatDate = (dateString) => {
        const date = new Date(dateString)
        return date.toLocaleString()
      }
  
      const cancelMatch = (id) => {
        deleteMatch(id)
      }
  
      return { matchList, formatDate, cancelMatch }
    }
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
  
  th {
    background-color: #f2f2f2;
  }
  </style>