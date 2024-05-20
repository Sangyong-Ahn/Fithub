import { ref } from 'vue'
import { defineStore } from "pinia";
import axios from 'axios'

import { useUserStore } from './userStore';

const REST_API = `http://localhost:8080/chat`

export const useChatStore = defineStore("chat", () => {

  const userStore = useUserStore();
  const chatRoom = ref({})
  const chatList = ref([])

  const getChatList = function (mentorId, userId) {
    axios.get(REST_API, {
        params: {
            mentorId: mentorId,
            userId: userId
        }
    })
      .then((response) => {
        chatList.value = response.data
      })
  }

  const insertChat = function (chat) {
    console.log(chat)
    axios.post(REST_API, chat)
      .then((response) => {
        getChatList(chat.mentorId, chat.userId)
        console.log('chatList Update')
      })
      .catch((error) => {
        console.error('insert 실패');
      });
  };

  const insertMentorChat = function (chat) {
    console.log(chat)
    axios.post(REST_API, chat)
      .then((response) => {
        userStore.getChatRooms(chat.mentorId)
        chatRoom.chats.push(response.data)
      })
      .catch((error) => {
        console.error('insert 실패');
      });
  };

  return { chatList, getChatList, insertChat, insertMentorChat, chatRoom };
});
