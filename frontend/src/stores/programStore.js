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
        programList.value = response.data
      })
      .catch(e => {
        console.log(e)
        originalProgramList.value = testData()
        programList.value = testData()
      });
  }

  const program = ref({})

  const getProgram = function (id) {
    axios.get(`${REST_API}/${id}`)
      .then((response) => {
        program.value = response.data
    })
  }

  return { programList, getProgramList, program, getProgram };
});


const testData = () => [
  {
  "id": 2,
  "mentorId": 1,
  "categoryId": 3,
  "title": "멘토 1의 글",
  "reservationStartDate": "2024-05-09T15:00:00.000+00:00",
  "reservationEndDate": "2024-05-09T15:00:00.000+00:00",
  "programStartDate": "2024-05-09T15:00:00.000+00:00",
  "programEndDate": "2024-05-09T15:00:00.000+00:00",
  "thumbnail": "string",
  "content": "string",
  "youtubeUrl": "string",
  "latitude": 37.5081110,
  "longitude": 127.037237,
  "createdAt": "2024-05-10T14:37:11.000+00:00",
  "times": [
      {
      "id": 1,
      "programId": 2,
      "sunday": true,
      "monday": false,
      "tuesday": true,
      "wednesday": false,
      "thursday": false,
      "friday": false,
      "saturday": false,
      "startTime": "12:00:00",
      "endTime": "14:00:00",
      "price": 23000,
      "capacity": 5,
      "reserveNum": 0
      },
      {
      "id": 2,
      "programId": 2,
      "sunday": false,
      "monday": true,
      "tuesday": true,
      "wednesday": false,
      "thursday": false,
      "friday": false,
      "saturday": false,
      "startTime": "14:00:00",
      "endTime": "15:00:00",
      "price": 15000,
      "capacity": 3,
      "reserveNum": 0
      }
  ],
  "mentorInfo": null
  },
  {
  "id": 3,
  "mentorId": 2,
  "categoryId": 3,
  "title": "멘토 2의 글",
  "reservationStartDate": "2024-05-09T15:00:00.000+00:00",
  "reservationEndDate": "2024-05-09T15:00:00.000+00:00",
  "programStartDate": "2024-05-09T15:00:00.000+00:00",
  "programEndDate": "2024-05-09T15:00:00.000+00:00",
  "thumbnail": "string",
  "content": "string",
  "youtubeUrl": "string",
  "latitude": 37.5004463,
  "longitude": 127.037236,
  "createdAt": "2024-05-10T14:37:11.000+00:00",
  "times": [
      {
      "id": 1,
      "programId": 2,
      "sunday": true,
      "monday": false,
      "tuesday": true,
      "wednesday": false,
      "thursday": false,
      "friday": false,
      "saturday": false,
      "startTime": "12:00:00",
      "endTime": "14:00:00",
      "price": 3000,
      "capacity": 5,
      "reserveNum": 0
      },
      {
      "id": 2,
      "programId": 2,
      "sunday": false,
      "monday": true,
      "tuesday": true,
      "wednesday": false,
      "thursday": false,
      "friday": false,
      "saturday": false,
      "startTime": "14:00:00",
      "endTime": "15:00:00",
      "price": 7000,
      "capacity": 3,
      "reserveNum": 0
      }
  ],
  "mentorInfo": null
  }
];