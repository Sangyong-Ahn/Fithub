<template>
  <!-- Main Navbar -->
  <div class="container">
    <div class="row">
      <div class="col-1"></div>
      <div class="col-10">
        <nav class="navbar navbar-expand-md py-5">
          <div class="container-fluid">
            <RouterLink class="navbar-brand" :to="{name: 'home'}">Fithub</RouterLink>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
              <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
              <ul class="navbar-nav">
                <!-- 로그인이 안된 상태 -->
                <template v-if="!store.loginUser">
                  <li class="nav-item">
                    <button type="button" @click="resetInput" class="btn" data-bs-toggle="modal" data-bs-target="#loginModal">
                      로그인
                    </button>
                  </li>
                  <li class="nav-item">
                    <button type="button" @click="resetInput" class="btn" data-bs-toggle="modal" data-bs-target="#registModal">
                      회원가입
                    </button>
                  </li>
                </template>
                <!-- 유저가 로그인한 상태 -->
                <template v-else-if="store.isUser">
                  <li class="d-flex nav-item align-items-center">
                    <div>{{ store.loginUser.name }}님 반갑습니다</div>
                  </li>
                  <li class="nav-item">
                    <button type="button" class="btn" @click="$router.push({ name: 'mypage' })">마이페이지</button>
                  </li>
                  <li class="nav-item">
                    <button type="button" @click="logout" class="btn"> 로그아웃</button>
                  </li>
                </template>
                <!-- 멘토가 로그인한 상태 -->
                <template v-else-if="store.isMentor">
                  <li class="d-flex nav-item align-items-center">
                    <div>{{ store.loginUser.name }}님 반갑습니다</div>
                  </li>
                  <li class="nav-item">
                    <button type="button" class="btn" @click="$router.push({ name: 'programCreate' })">게시글작성</button>
                  </li>
                  <li class="nav-item">
                    <button type="button" class="btn" @click="$router.push({ name: 'mentorPageView' })">마이페이지</button>
                  </li>
                  <li class="nav-item">
                    <button type="button" @click="logout" class="btn">로그아웃</button>
                  </li>
                </template>
              </ul>
            </div>
          </div>
        </nav>
      </div>
      <div class="col-1"></div>
    </div>
  </div>

  <!-- Login Modal -->
  <LoginModal />
  
  <!-- Register Modal -->
  <RegisterModal />

</template>

<script setup>
import { RouterLink } from 'vue-router';
import LoginModal from './LoginModal.vue';
import RegisterModal from './RegisterModal.vue';
import { useUserStore } from '@/stores/userStore';
import { useMatchStore } from '@/stores/matchStore';

const store = useUserStore();
const matchStore = useMatchStore();

const logout = function() {
  matchStore.clearMatchList();
  store.logout();
}

const resetInput = function() {
  store.errMsg = ''
}

</script>

<style scoped>
</style>
