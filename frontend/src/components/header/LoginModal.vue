<template>
  <div>
    <!-- Login Modal -->
    <div class="modal fade" id="loginModal" tabindex="-1" aria-labelledby="loginModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header justify-content-center">
            <h1 class="modal-title fs-5" id="loginModalLabel">로그인</h1>
          </div>
          <div class="modal-body">
            <!-- 이메일 입력 -->
            <div class="m-3">
              <input type="email" v-model="userEmail" class="form-control custom-input" id="loginEmail" placeholder="이메일을 입력하세요">
            </div>
            <!-- 비밀번호 입력 -->
            <div class="m-3">
              <input type="password" v-model="userPassword" class="form-control custom-input" id="loginPassword" placeholder="비밀번호를 입력하세요">
            </div>
            <div style="color: red">{{ store.errMsg }}</div>
          </div>
          <div class="modal-footer justify-content-around">
            <button type="button" @click="resetInputs" class="btn" data-bs-toggle="modal" data-bs-target="#mentorLoginModal" style="border: 1px solid gray; width: 40%">멘토 전환</button>
            <button type="button" @click="userLogin" class="btn" style="width: 40%;">로그인</button>
          </div>
        </div>
      </div>
    </div>

    <!-- Mentor Login Modal -->
    <div class="modal fade" id="mentorLoginModal" tabindex="-1" aria-labelledby="mentorLoginModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header justify-content-center">
            <h1 class="modal-title fs-5" id="mentorLoginModalLabel">멘토 로그인</h1>
          </div>
          <div class="modal-body">
            <!-- 멘토 이메일 입력 -->
            <div class="m-3">
              <input type="email" v-model="mentorEmail" class="form-control custom-input" id="mentorLoginEmail" placeholder="이메일을 입력하세요">
            </div>
            <!-- 멘토 비밀번호 입력 -->
            <div class="m-3">
              <input type="password" v-model="mentorPassword" class="form-control custom-input" id="mentorLoginPassword" placeholder="비밀번호를 입력하세요">
            </div>
            <div style="color: red">{{ store.errMsg }}</div>
          </div>
          <div class="modal-footer justify-content-around">
            <button type="button" @click="resetInputs" class="btn" data-bs-toggle="modal" data-bs-target="#loginModal" style="border: 1px solid gray; width: 40%;">일반 회원 전환</button>
            <button type="button" @click="mentorLogin" class="btn" style="width: 40%;">로그인</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useUserStore } from '@/stores/userStore';
import { useMatchStore } from '@/stores/matchStore';

const userEmail = ref('');
const userPassword = ref('');
const mentorEmail = ref('');
const mentorPassword = ref('');

const store = useUserStore();
const matchStore = useMatchStore();

const userLogin = function() {
  // 비동기
  store.userLogin(userEmail.value, userPassword.value)
  .then(()=>{
    matchStore.getMatchListByUser(store.loginUser.id)
  })
  
}

const mentorLogin = function() {
  store.mentorLogin(mentorEmail.value, mentorPassword.value)
}

const resetInputs = function() {
  userEmail.value = '';
  userPassword.value = '';
  mentorEmail.value = '';
  mentorPassword.value = '';
  store.errMsg = '';
}

</script>

<style scoped>
    
</style>