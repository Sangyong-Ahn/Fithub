<template>
  <div class="w-50 mb-5">
    <h4 class="text-center mb-4">채팅방 목록</h4>
    <div v-for="chatRoom in userStore.chatRooms" :key="chatRoom.id" class="d-flex justify-content-around border rounded-5 py-3 px-4 my-2" style="background-color: #040404;">
        <div class="flex-grow-1">
          <img class="border rounded-5 me-2" src="@/assets/common/profile-demo.png" style="width:30px; height:30px; object-fit: cover;">  
          {{ chatRoom.name }}님 과의 채팅
        </div>
        <button @click="openChatRoom(chatRoom)" class="btn btn-sm btn-primary">채팅하기</button>
    </div>

    <!-- Chat Modal -->
    <div class="modal fade" id="chatModal" tabindex="-1" aria-labelledby="chatModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">

          <!-- Modal Header -->
          <div class="modal-header justify-content-center">
            <h5 class="modal-title" id="chatModalLabel">채팅</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>

          <!-- Modal Body -->
          <div ref="modalBody" class="modal-body bg-light scrollbar">
            <template v-for="chat in userStore.chatRoom.chats" :key="chat.id">
              <div v-if="!chat.user" class="row justify-content-end mb-3">
                <div class="text-end" style="max-width: 70%;">
                  <div class="text-end" style="font-size:13px; color:gray">{{ new Date(chat.createdAt).toLocaleString() }}</div>
                  <div class="border rounded-4 bg-white px-3 py-2" style="display: inline-block; word-wrap: break-word;">
                    <div>{{ chat.content }}</div>
                  </div>
                </div>
              </div>
              <div v-else class="row mb-3">
                <div class="d-flex" style="max-width: 70%;">
                  <img class="border border-2" src="@/assets/common/profile-demo.png" style="border-radius:50%; width:40px; height:40px;">
                  <div class="mx-2">
                    <div class="text-start" style="font-size:13px; color:gray">{{ new Date(chat.createdAt).toLocaleString() }}</div>
                    <div class="border rounded-4 bg-white px-3 py-2" style="display: inline-block; word-wrap: break-word; border: 1px solid white !important">
                      <div>{{ chat.content }}</div>
                    </div>
                  </div>
                </div>
              </div>
            </template>
          </div>

          <!-- Input Area -->
          <div class="modal-footer justify-content-center">
            <div class="d-flex gap-2 align-items-center">
              <div>
                <input type="text" v-model="content" @keyup.enter="insertChat" class="form-control p-2" style="width: 400px" placeholder="Type your message...">
              </div>
              <div>
                <button @click="insertChat" type="button" class="btn btn-sm btn-outline-secondary">전송</button>
              </div>
            </div>
          </div>

        </div>
      </div>
    </div>
  </div>
</template>
  
<script setup>
import { watch, ref, onMounted, nextTick } from 'vue';
import { useUserStore } from '@/stores/userStore';

const userStore = useUserStore();

const content = ref('');
const modalBody = ref(null);
  
const insertChat = async function() {
  if (typeof content.value === 'string' && content.value.trim().length === 0) {
    return;
  }
  await userStore.insertMentorChat(
    {
      mentorId: userStore.loginUser.id,
      userId: userStore.chatRoom.id,
      content: content.value,
      user: false
    }
  );
  content.value = '';
  nextTick(() => {
    scrollToBottom();
  });
};
  
const scrollToBottom = () => {
  if (modalBody.value) {
    modalBody.value.scrollTo({
      top: modalBody.value.scrollHeight,
      behavior: 'smooth'
    });
  }
};

const openChatRoom = (chatRoom) => {
  userStore.chatRoom = chatRoom
  const chatModal = new bootstrap.Modal(document.getElementById('chatModal'));
  chatModal.show();
};

onMounted(() => {
  const chatModal = document.getElementById('chatModal');
  chatModal.addEventListener('shown.bs.modal', () => {
    nextTick(() => {
      scrollToBottom();
    });
  });
  userStore.getChatRooms(userStore.loginUser.id);
});

</script>

<style scoped>
.scrollbar { 
  width: 100%;
  height: 600px;
  overflow-y: scroll; 
}

/* 스크롤바의 폭 너비 */
.scrollbar::-webkit-scrollbar {
  width: 11px;  
}

.scrollbar::-webkit-scrollbar-thumb {
  background: rgb(171, 231, 6); /* 스크롤바 색상 */
  border-radius: 10px; /* 스크롤바 둥근 테두리 */
}

.scrollbar::-webkit-scrollbar-track {
  background: rgba(171, 231, 6, .1);  /*스크롤바 뒷 배경 색상*/
}
</style>
