import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { defineStore } from "pinia";
import axios from 'axios';

import { useChatStore } from './chatStore';

const USER_REST_API = `http://localhost:8080/user`;
const MENTOR_REST_API = `http://localhost:8080/mentor`;
const CHAT_REST_API = `http://localhost:8080/chat`

export const useUserStore = defineStore("user", () => {
  const router = useRouter();

  const loginUser = ref(null);
  const isUser = ref(false);
  const isMentor = ref(false);
  const errMsg = ref('');
  const user = ref({});

  const getUser = function (id) {
    axios.get(`${USER_REST_API}/${id}`)
    .then(response => {
      console.log(response.data);
      user.value = response.data;
    })
  };

  const userLogin = function (email, password) {
    axios.post(`${USER_REST_API}/login`, null, {
      params: {
        email: email,
        password: password,
      }
    })
    .then(response => {
      console.log(response);
      isUser.value = true;
      isMentor.value = false;
      loginUser.value = response.data;
      errMsg.value = '';
      
      sessionStorage.setItem('accessToken', response.data["accessToken"]);
      sessionStorage.setItem('loginUser', JSON.stringify(loginUser.value));
      sessionStorage.setItem('isUser', isUser.value);
      sessionStorage.setItem('isMentor', isMentor.value);

      $('#loginModal').modal('hide');
    })
    .catch(error => {
      errMsg.value = error.response.data;
    });
  };

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
      errMsg.value = '';
      
      sessionStorage.setItem('accessToken', response.data["accessToken"]);
      sessionStorage.setItem('loginUser', JSON.stringify(loginUser.value));
      sessionStorage.setItem('isUser', isUser.value);
      sessionStorage.setItem('isMentor', isMentor.value);

      $('#mentorLoginModal').modal('hide');
    })
    .catch(error => {
      errMsg.value = error.response.data;
    });
  };

  const logout = function () {
    sessionStorage.removeItem('accessToken');
    sessionStorage.removeItem('loginUser');
    sessionStorage.removeItem('isUser');
    sessionStorage.removeItem('isMentor');
    
    if (isMentor.value) {
      axios.get(`${MENTOR_REST_API}/logout`).then(() => {
        isMentor.value = false;
        loginUser.value = null;
      });
    } else if (isUser.value) {
      axios.get(`${USER_REST_API}/logout`).then(() => {
        isUser.value = false;
        loginUser.value = null;
      });
    }
    router.push({ name: 'home' });
  };

  const userCreate = function (user) {
    axios.post(USER_REST_API, user)
    .then(() => {
      errMsg.value = '';
      alert('회원 가입에 성공했습니다.');
      $('#registModal').modal('hide');
    })
    .catch(error => {
      errMsg.value = error.response.data;
    });
  };

  const mentorCreate = function (mentor) {
    axios.post(MENTOR_REST_API, mentor)
    .then(() => {
      errMsg.value = '';
      alert('멘토 회원 가입에 성공했습니다.');
      $('#mentorRegistModal').modal('hide');
    })
    .catch(error => {
      errMsg.value = error.response.data;
    });
  };

  const loadSessionStorage = function () {
    const storedAccessToken = sessionStorage.getItem('accessToken');
    const storedLoginUser = sessionStorage.getItem('loginUser');
    const storedIsUser = sessionStorage.getItem('isUser');
    const storedIsMentor = sessionStorage.getItem('isMentor');
    
    if (storedAccessToken) {
      loginUser.value = JSON.parse(storedLoginUser);
      isUser.value = storedIsUser === 'true';
      isMentor.value = storedIsMentor === 'true';
    }
  };

  const updateUser = function (id, user) {
    return axios
      .put(`${USER_REST_API}/${id}`, user)
      .then((response) => {
        console.log('사용자 정보 업데이트 성공:', response.data);
        loginUser.value = response.data;
        sessionStorage.setItem('loginUser', JSON.stringify(loginUser.value));
      })
      .catch((error) => {
        console.error('사용자 정보 업데이트 실패:', error);
      });
  };

  const updateMentor = function (id, mentor) {
    return axios
      .put(`${MENTOR_REST_API}/${id}`, mentor)
      .then((response) => {
        console.log('멘토 정보 업데이트 성공:', response.data);
        loginUser.value = response.data;
        sessionStorage.setItem('loginUser', JSON.stringify(loginUser.value));
      })
      .catch((error) => {
        console.error('멘토 정보 업데이트 실패:', error);
      });
  };

  const chatRooms = ref([])

  const getChatRooms = function (id) {
    axios.get(`${MENTOR_REST_API}/${id}/chat`)
      .then((response) => {
        chatRooms.value = response.data
      })
      .catch((error) => {
        console.error('Failed to get chat rooms:', error);
      });
  }

  const chatRoom = ref({})

  const insertMentorChat = function (chat) {
    console.log(chat)
    return axios.post(CHAT_REST_API, chat)
      .then((response) => {
        getChatRooms(chat.mentorId)
        chatRoom.value.chats.push(response.data)
      })
      .catch((error) => {
        console.error('insert 실패');
      });
  };


  onMounted(loadSessionStorage);

  return {
    user, getUser, loginUser, isUser, isMentor, errMsg,
    userLogin, mentorLogin, logout, userCreate, mentorCreate, 
    loadSessionStorage, updateUser, updateMentor, chatRooms, getChatRooms, chatRoom, insertMentorChat
  };
});
