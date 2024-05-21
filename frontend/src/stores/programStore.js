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

  const getProgram = async function (id) {
    return axios.get(`${REST_API}/${id}`)
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

  const insertProgram = function (program, image) {
    console.log(program);
    const formData = new FormData();
    const json = JSON.stringify(program);
    console.log(json)
    const blob = new Blob([json], { type: "application/json" });
    formData.append("program", blob);
    formData.append("file", image);

    axios.post(`${REST_API}`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })
    .then((response) => {
      console.log(response.data);
      const newProgramId = response.data.id;
      getProgram(newProgramId)
      .then(() => {
        router.push({ name: 'programDetail', params: { id: newProgramId } });
      });
    })
    .catch((error) => {
      console.log(error);
    });
  }

  const deleteProgram = function (id) {
    if (confirm("정말로 이 프로그램을 삭제하시겠습니까?")) {
      axios.delete(`${REST_API}/${id}`)
      .then(() => {
        alert("프로그램이 삭제되었습니다.");
          router.push({ name: 'home' });
      })
      .catch ((error) => {
        console.log(error);
      })
    }
  };

  const mentorPrograms = ref([]);
  // const getMentorPrograms = function (mentorId) {
  //   console.log(mentorId);
  //   axios.get(`http://localhost:8080/mentor/${mentorId}/program`)
  //   .then(response => {
  //     mentorPrograms.value = response.data;
  //     console.log(mentorPrograms.value.length, '개 받았습니다.');
  //   })
  //   .catch(error => {
  //     console.error('멘토 프로그램 리스트 가져오기 실패: ', error);
  //   })
  // }
  const getMentorPrograms = async function (mentorId) {
    try {
      const response = await axios.get(`http://localhost:8080/mentor/${mentorId}/program`);
      mentorPrograms.value = response.data;
      console.log(mentorPrograms.value)
    } catch (error) {
      console.error('멘토 프로그램 리스트 가져오기 실패: ', error);
    }
  };

  return { originalProgramList, programList, getProgramList, program, getProgram, programSearch, isSpecified, insertProgram, deleteProgram, getMentorPrograms, mentorPrograms };
});
