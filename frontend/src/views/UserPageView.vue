<template>
  <div id="app">
    <div class="container-fluid">
      <div class="row">
        <nav class="col-md-2 col-lg-2 d-md-block bg-light fixed-top" style="margin-top: 210px; margin-left: 20px; padding-left: 30px">
          <div class="sidebar-sticky">
            <ul class="nav flex-column">
              <li class="nav-item">
                <a class="nav-link" @click="changeTab('user')" :class="{ active: activeTab === 'user' }">유저 페이지</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" @click="changeTab('edit')" :class="{ active: activeTab === 'edit' }">정보 수정</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" @click="changeTab('reservation')" :class="{ active: activeTab === 'reservation' }">예약 정보</a>
              </li>
            </ul>
          </div>
        </nav>
        <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-md-4" style="margin-left: 220px;">
          <div class="d-flex justify-content-center">
            <div class="w-100" style="max-width: 80%;">
              <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h1 class="h2">마이페이지</h1>
              </div>
              <div v-if="activeTab === 'user'">
                <UserPage :user="user" />
              </div>
              <div v-if="activeTab === 'edit'">
                <EditUserInfo :user="user" :confirmPassword="confirmPassword" @update:user="updateUser" @update:confirmPassword="updateConfirmPassword" @save-changes="saveChanges" />
              </div>
              <div v-if="activeTab === 'reservation'">
                <ReservationInfo :user="user" />
              </div>
            </div>
          </div>
        </main>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import UserPage from '@/components/UserPage.vue';
import EditUserInfo from '@/components/EditUserInfo.vue';
import ReservationInfo from '@/components/ReservationInfo.vue';
import { useUserStore } from '@/stores/userStore';

export default {
  components: {
    UserPage,
    EditUserInfo,
    ReservationInfo
  },
  setup() {
    const store = useUserStore();
    const router = useRouter();
    const user = ref({ ...store.loginUser });
    const activeTab = ref('user');
    const confirmPassword = ref('');

    const changeTab = (tabName) => {
      activeTab.value = tabName;
    };

    const updateUser = (updatedUser) => {
      user.value = updatedUser;
    };

    const updateConfirmPassword = (newPassword) => {
      confirmPassword.value = newPassword;
    };

    const saveChanges = async () => {
      try {
        if (user.value.password === confirmPassword.value || !user.value.password) {
          await store.updateUser(user.value.id, user.value);
          await router.push('/myPage'); // 경로 변경
          activeTab.value = 'user'; // 유저 페이지 탭으로 전환
        } else {
          alert("비밀번호와 확인 비밀번호가 일치하지 않습니다.");
        }
      } catch (error) {
        console.error('정보 저장 중 오류 발생:', error);
      }
    };

    return {
      user,
      activeTab,
      confirmPassword,
      changeTab,
      updateUser,
      updateConfirmPassword,
      saveChanges
    };
  }
}
</script>
