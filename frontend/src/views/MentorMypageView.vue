<template>
  <div id="app">
    <div class="container mt-5">
      <div class="row">
        <nav class="col-2">
          <div class="sidebar-sticky">
            <ul class="nav flex-column">
              <li class="nav-item mt-5">
                <a class="nav-link border text-secondary" @click="changeTab('mentor')" :class="{ active: activeTab === 'mentor' }">내 정보</a>
              </li>
              <li class="nav-item">
                <a class="nav-link border text-secondary" @click="changeTab('edit')" :class="{ active: activeTab === 'edit' }">내 정보 수정</a>
              </li>
              <li class="nav-item">
                <a class="nav-link border text-secondary" @click="changeTab('program')" :class="{ active: activeTab === 'program' }">프로그램 정보</a>
              </li>
              <li class="nav-item">
                <a class="nav-link border text-secondary" @click="changeTab('review')" :class="{ active: activeTab === 'review' }">후기</a>
              </li>
              <li class="nav-item">
                <a class="nav-link border text-secondary" @click="changeTab('chat')" :class="{ active: activeTab === 'chat' }">채팅방</a>
              </li>
            </ul>
          </div>
        </nav>
        <div class="col-9 m-0">
          <div class="d-flex justify-content-center">
            <div class="w-100">
              <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h1 class="h2">마이페이지</h1>
              </div>
              <div v-if="activeTab === 'mentor'" class="d-flex justify-content-center">
                <MentorProfile :mentor="mentor" />
              </div>
              <div v-if="activeTab === 'edit'" class="d-flex justify-content-center">
                <MentorUpdate :mentor="mentor" />
              </div>
              <div v-if="activeTab === 'program'" class="d-flex justify-content-center">
                <MentorProgram :mentor="mentor" />
              </div>
              <div v-if="activeTab === 'review'" class="d-flex justify-content-center">
                <MentorReview />
              </div>
              <div v-if="activeTab === 'chat'" class="d-flex justify-content-center">
                <MentorChat />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, onMounted, watch } from 'vue';
import { useRouter } from 'vue-router';
import { useUserStore } from '@/stores/userStore';
import MentorProfile from '@/components/mypage/MentorProfile.vue';
import MentorUpdate from '@/components/mypage/MentorUpdate.vue';
import MentorProgram from '@/components/mypage/MentorProgram.vue';
import MentorReview from '@/components/mypage/MentorReview.vue';
import MentorChat from '@/components/mypage/MentorChat.vue';
import axiosInstance from '@/axiosInstance';

export default {
  components: {
    MentorProfile,
    MentorUpdate,
    MentorProgram,
    MentorReview,
    MentorChat,
  },
  setup() {
    const store = useUserStore();
    const router = useRouter();
    const mentor = ref({ ...store.loginUser });
    const activeTab = ref('mentor');

    onMounted(async () => {
      try {
        await axiosInstance.get('/api/auth/verify-token'); // 서버에서 토큰 유효성 검증
      } catch (error) {
        store.logout();
        router.push('/');
      }
    });

    const changeTab = (tabName) => {
      activeTab.value = tabName;
    };

    // store.loginUser를 감시하고 값이 변경될 때마다 user ref를 업데이트
    watch(() => store.loginUser, (newValue) => {
      mentor.value = { ...newValue };
    });

    return {
      mentor,
      activeTab,
      changeTab,
    };
  }
}
</script>

<style scoped>

.nav-link {
  color:white !important
}
.active {
  color: lightgreen !important;
}

.nav {
  padding-top: 20px;
}
.nav-item {
  background-color: black;
}

</style>