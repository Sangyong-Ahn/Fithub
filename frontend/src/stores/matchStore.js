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

  return { matchList, getMatchListByUser, clearMatchList };
});
