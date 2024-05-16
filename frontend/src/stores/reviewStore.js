import { ref } from 'vue'
import { defineStore } from "pinia";
import axios from 'axios'

const REST_API = `http://localhost:8080/review`

export const useReviewStore = defineStore("review", () => {

  const insertReview = function (review) {
    return axios.post(REST_API, review)
  };

  return { insertReview };
});
