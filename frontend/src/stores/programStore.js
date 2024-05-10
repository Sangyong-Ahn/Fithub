import { ref } from 'vue'
import { defineStore } from "pinia";
import router from '@/router'
import axios from 'axios'

const REST_API = `http://localhost:8080/program`

export const useProgramStore = defineStore("program", () => {

  const originalProgramList = ref([])
  const programList = ref([])

  const getProgramList = function () {
    axios.get(REST_API)
      .then((response) => {
        originalProgramList.value = response.data
        programList = response.data
      })
  }

  const program = ref({})

  const getProgram = function (id) {
    axios.get(`${REST_API}/${id}`)
      .then((response) => {
        program.value = response.data
    })
  }

  return { programList, getProgramList };
});
