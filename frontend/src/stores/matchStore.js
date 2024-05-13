import { ref } from 'vue'
import { defineStore } from "pinia";
import axios from 'axios'

const REST_API = `http://localhost:8080/match`

export const useCategoryStore = defineStore("match", () => {

  const matchList = ref([])

  const getMatchList = function () {
    axios.get(REST_API/user/${userId})
      .then((response) => {
        categoryList.value = response.data
      })
  }

  return { categoryList, getCategoryList };
});
