import { ref } from 'vue'
import { defineStore } from "pinia";
import router from '@/router'
import axios from 'axios'

const REST_API = `http://localhost:8080/program`

export const useProgramStore = defineStore("program", () => {

  const originalProgramList = ref([])
  const programList = ref([])

  // 지도에서 특정 지점 하나를 누른지 체크하는 변수
  const isSpecified = ref(false)

  const getProgramList = function () {
    axios.get(REST_API)
      .then((response) => {
        originalProgramList.value = response.data
        programList.value = response.data
      })
      .catch(e => {
        console.log(e)
      });
  }

  const program = ref({})

  const getProgram = function (id) {
    axios.get(`${REST_API}/${id}`)
      .then((response) => {
        program.value = response.data
    })
  }

  const programSearch = function (searchCondition) {
    axios.get(`${REST_API}/search`, {params: searchCondition})
      .then((response) => {
        console.log(response.data)
        isSpecified.value = false;
        originalProgramList.value = response.data;
        programList.value = response.data
    })
    .catch((error) => {
      console.log(error);
    });
  }

  const insertProgram = function (program) {
    console.log(program);
    const formData = new FormData();
    formData.append('program', JSON.stringify(program));

    axios.post(`${REST_API}`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })
    .then((response) => {
      console.log(response.data);
    })
    .catch((error) => {
      console.log(error);
    });
  }

  return { originalProgramList, programList, getProgramList, program, getProgram, programSearch, isSpecified, insertProgram };
});
