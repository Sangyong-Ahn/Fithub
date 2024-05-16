import { ref } from 'vue'
import { defineStore } from "pinia";
import router from '@/router'
import axios from 'axios'

const USER_REST_API = `http://localhost:8080/user`
const MENTOR_REST_API = `http://localhost:8080/mentor`

export const useUserStore = defineStore("user", () => {

  const loginUser = ref(null)
  const isUser = ref(false)
  const isMentor = ref(false)
  const errMsg = ref('')
  const user = ref({})

  const getUser = function (id) {
    axios.get(`${USER_REST_API}/${id}`)
    .then(response => {
      console.log(response.data)
      user.value = response.data
    })
  }

  const userLogin = function (email, password) {
    axios.post(`${USER_REST_API}/login`, null, {
      params: {
        email: email,
        password: password,
      }
    })
    .then(response => {
      console.log(response)
      isUser.value = true;
      isMentor.value = false;
      loginUser.value = response.data;
      errMsg.value = ''
      $('#loginModal').modal('hide');
    })
    .catch(error => {
      errMsg.value = error.response.data;
    });
  }

  const userLoginPromise = async function (email, password) {
    return axios.post(`${USER_REST_API}/login`, null, {
      params: {
        email: email,
        password: password,
      }
    })
    .then(response => {
      console.log(response)
      isUser.value = true;
      isMentor.value = false;
      loginUser.value = response.data;
      errMsg.value = ''
      $('#loginModal').modal('hide');
    })
    .catch(error => {
      errMsg.value = error.response.data;
    });
  }

  const mentorLogin = function (email, password) {
    axios.post(`${MENTOR_REST_API}/login`, null, {
      params: {
        email: email,
        password: password,
      }
    })
    .then(response => {
      isUser.value = false;
      isMentor.value = true;
      loginUser.value = response.data;
      errMsg.value = ''
      $('#mentorLoginModal').modal('hide');
    })
    .catch(error => {
      errMsg.value = error.response.data;
    });
  }

  const logout = function () {
    if(isMentor){
      axios.get(`${MENTOR_REST_API}/logout`)
      .then(() => {
        isMentor.value = false;
        loginUser.value = null;
      })
    }
    else if(isUser){
      axios.get(`${USER_REST_API}/logout`)
      .then(() => {
        isUser.value = false;
        loginUser.value = null;
      })
    }
  }

  const userCreate = function (user) {
    axios({
      url: USER_REST_API,
      method: 'POST',
      data: user
    })
    .then(() => {
      errMsg.value = ''
      alert('회원 가입에 성공했습니다.')
      $('#registModal').modal('hide');
    })
    .catch((error) => {
      errMsg.value = error.response.data;
    })
  }

  const mentorCreate = function (mentor) {
    axios({
      url: MENTOR_REST_API,
      method: 'POST',
      data: mentor
    })
    .then(() => {
      errMsg.value = ''
      alert('멘토 회원 가입에 성공했습니다.')
      $('#mentorRegistModal').modal('hide');
    })
    .catch((error) => {
      errMsg.value = error.response.data;
    })
  }

  return { user, getUser, loginUser, isUser, isMentor, errMsg, userLogin, userLoginPromise, mentorLogin, logout, userCreate, mentorCreate };
});
