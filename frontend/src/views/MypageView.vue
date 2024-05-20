<template>
  <div id="app">
    <div class="container mt-5">
      <div class="row">
        <nav class="col-2">
          <div class="sidebar-sticky">
            <ul class="nav flex-column">
              <li class="nav-item mt-5">
                <a class="nav-link border text-secondary" @click="changeTab('user')" :class="{ active: activeTab === 'user' }">내 정보</a>
              </li>
              <li class="nav-item">
                <a class="nav-link border text-secondary" @click="changeTab('edit')" :class="{ active: activeTab === 'edit' }">내 정보 수정</a>
              </li>
              <li class="nav-item">
                <a class="nav-link border text-secondary" @click="changeTab('reservation')" :class="{ active: activeTab === 'reservation' }">예약 정보</a>
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
              <div v-if="activeTab === 'user'" class="d-flex justify-content-center">
                <UserPage :user="user" />
              </div>
              <div v-if="activeTab === 'edit'" class="d-flex justify-content-center">
                <EditUserInfo :user="user" />
              </div>
              <div v-if="activeTab === 'reservation'" class="d-flex justify-content-center">
                <ReservationInfo :user="user" />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, watch } from 'vue';
import { useRouter } from 'vue-router';
import UserPage from '@/components/mypage/UserPage.vue';
import EditUserInfo from '@/components/mypage/EditUserInfo.vue';
import ReservationInfo from '@/components/mypage/ReservationInfo.vue';
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

    const changeTab = (tabName) => {
      activeTab.value = tabName;
    };

    // store.loginUser를 감시하고 값이 변경될 때마다 user ref를 업데이트
    watch(() => store.loginUser, (newValue) => {
      user.value = { ...newValue };
    });

    return {
      user,
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