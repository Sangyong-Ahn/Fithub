import { ref } from 'vue'
import { defineStore } from "pinia";
import axios from 'axios'

const REST_API = `http://localhost:8080/category`

export const useCategoryStore = defineStore("category", () => {

  const categoryList = ref([])

  const getCategoryList = function () {
    axios.get(REST_API)
      .then((response) => {
        categoryList.value = response.data
      })
  }

  return { categoryList, getCategoryList };
});
