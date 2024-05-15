import { ref } from 'vue'
import { defineStore } from "pinia";
import axios from 'axios'

const REST_API = `http://localhost:8080/match`

export const useMatchStore = defineStore("match", () => {

  const matchList = ref([])

  const getMatchListByUser = function (userId) {
    axios.get(`${REST_API}/user/${userId}`)
      .then((response) => {
        matchList.value = response.data
        
      })
  }

  const clearMatchList = function () {
    matchList.value = [];
  };

  const insertMatch = function (match) {
    axios.post(REST_API, match)
      .then((response) => {
        getMatchListByUser(match.userId)
        console.log('matchList Update')
      })
      .catch((error) => {
        console.error('예약에 실패했습니다.', error);
      });
  };

  const deleteMatch = function (id) {
    axios.delete(`${REST_API}/${id}`)
      .then(() => {
        matchList.value = matchList.value.filter(match => match.id !== id);
      })
      .catch((error) => {
        console.error('예약 취소에 실패했습니다.', error);
      });
  };

  return { matchList, getMatchListByUser, clearMatchList, insertMatch, deleteMatch };
});
