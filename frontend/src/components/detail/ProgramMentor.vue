<template>
  <div>
    <div>
      <div v-if="mentorInfo" class="text-center border m-3 rounded-4 bg-white p-3">
        <img class="border m-3 mb-0" src="@/assets/common/profile-demo.png" style="border-radius:50%; width:100px; height:100px; object-fit: cover;">
        <div class="fs-5">{{ mentorInfo.name }} 멘토</div>
        <div class="d-flex justify-content-center align-items-center">
          <div class="stars me-2">
            <span v-for="star in 5" :key="star" :class="{ 'filled': star <= mentorInfo.reviewAvgScore }">&#9733;</span>
          </div>
          <div>{{ mentorInfo.reviewAvgScore.toFixed(1) }} ({{ mentorInfo.reviews.length }})</div>
        </div>
        <div class="d-flex justify-content-center">
          <div class="border border-3 rounded-4 w-75 my-3 p-3" style="min-height:200px;">
            개인 소개글 나오는 부분{{ mentorInfo.content }}
          </div>
        </div>
        <div class="d-flex justify-content-center gap-5 my-3">
          <button class="btn btn-outline-secondary btn-lg px-5" @click="openReviewModal">후기 작성</button>
          <button class="btn btn-outline-secondary btn-lg px-5" @click="openChatModal">채팅</button>
        </div>
      </div>
    </div>

    <UserReviewModal/>
    <UserChatModal/>
  </div>
</template>

<script setup>
import { computed } from 'vue';
import { useProgramStore } from '@/stores/programStore';
import { useUserStore } from '@/stores/userStore';
import UserChatModal from '@/components/util/UserChatModal.vue'
import UserReviewModal from '@/components/util/UserReviewModal.vue'

const store = useProgramStore();
const userStore = useUserStore();

const mentorInfo = computed(() => {
  return store.program?.mentorInfo || null;
});

const openChatModal = function () {
  if (!userStore.loginUser) {
    alert('로그인 해주세요');
    return;
  }
  if (!userStore.isUser) {
    alert("일반 회원 계정으로만 신청이 가능합니다.");
    return;
  }
  const modal = new bootstrap.Modal(document.getElementById('chatModal'));
  modal.show();
};

const openReviewModal = function () {
  if (!userStore.loginUser) {
    alert('로그인 해주세요');
    return;
  }
  if (!userStore.isUser) {
    alert("일반 회원 계정으로만 작성이 가능합니다.");
    return;
  }
  const modal = new bootstrap.Modal(document.getElementById('reviewModal'));
  modal.show();
};


</script>

<style scoped>
.stars {
  color: gray;
}

.stars span {
  font-size: 20px; /* Adjust the size as needed */
}

.stars .filled {
  color: gold;
}

.btn {
  color: white;
  border: 1px solid gray;
}

</style>
