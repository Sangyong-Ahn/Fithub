<template>
    <div class="w-75">
      <div>
        <form @submit.prevent="saveChanges">
          <!-- Form Fields -->
          <div class="row my-4 align-items-end">
            <div class="col-md-6">
              <div class="form-group">
                <label for="mentorName">이름</label>
                <input type="text" class="form-control" id="mentorName" v-model="mentor.name">
              </div>
            </div>
            <div class="col-md-6">
              <div class="form-group text-end">
                <div class="btn-group w-100" role="group">
                  <button type="button" class="btn" :class="{ 'btn-secondary': mentor.gender === 'M', 'disabled': mentor.gender === 'M' }" @click="selectGender('M')" :disabled="mentor.gender === 'F'">남자</button>
                  <button type="button" class="btn" :class="{ 'btn-secondary': mentor.gender === 'F', 'disabled': mentor.gender === 'F' }" @click="selectGender('F')" :disabled="mentor.gender === 'M'">여자</button>
                </div>
              </div>
            </div>
          </div>
          <!-- Other Form Fields -->
          <div class="form-group my-4">
            <label for="mentorEmail">이메일</label>
            <input type="email" class="form-control" id="mentorEmail" v-model="mentor.email" readonly>
          </div>
          <div class="form-group my-4">
            <label for="mentorPassword">비밀번호</label>
            <div class="input-group">
              <input type="password" class="form-control" id="mentorPassword" v-model="mentor.password">
              <button class="btn btn-outline-secondary" type="button" @click="togglePasswordVisibility">
                <i class="bi bi-eye-slash-fill"></i>
              </button>
            </div>
          </div>
          <div class="form-group my-4">
            <label for="confirmPassword">비밀번호 확인</label>
            <input type="password" class="form-control" id="confirmPassword" v-model="confirmPassword">
            <small v-if="confirmPassword && mentor.password !== confirmPassword" class="text-danger">
              비밀번호가 일치하지 않습니다.
            </small>
          </div>
          <div class="form-group my-4">
            <label for="mentorBirthdate">생년월일</label>
            <input type="text" class="form-control" id="mentorBirthdate" v-model="mentor.dateOfBirth" readonly>
          </div>
          <div class="form-group my-4">
            <label for="mentorPhoneNumber">전화번호</label>
            <input type="text" class="form-control" id="mentorPhoneNumber" v-model="mentor.phoneNumber">
          </div>
          <div class="form-group my-4">
            <label for="mentorContent">소개글</label>
            <input type="text" class="form-control" id="mentorContent" v-model="mentor.content">
          </div>
          <div class="mx-3 my-5">
            <!-- 위치 선택 -->
            <UserMap 
              :id="'mentorEdit'"
              :lat="mentor.latitude"
              :lng="mentor.longitude"
              :width="'100%'"
              :height="'300px'"
              :isMarkable="'true'"
              @update-lat-lng="updateLatLng"
            />
          </div>
          <div class="form-group mb-5 text-end">
            <button type="submit" class="btn btn-outline-secondary" :disabled="!isPasswordMatch">저장</button>
          </div>
        </form>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, computed, onMounted } from 'vue';
  import { useRouter } from 'vue-router';
  import { useUserStore } from '@/stores/userStore';
  import UserMap from '../util/UserMap.vue';
  
  const props = defineProps({
    mentor: Object
  });
  
  const userStore = useUserStore();
  const router = useRouter();
  
  const mentor = ref({ ...props.mentor });
  const confirmPassword = ref('');
  
  onMounted(() => {
    mentor.value = { ...props.mentor };
  });
  
  const isPasswordMatch = computed(() => {
    return mentor.value.password === confirmPassword.value;
  });
  
  const togglePasswordVisibility = () => {
    const input = document.getElementById('mentorPassword');
    input.type = input.type === 'password' ? 'text' : 'password';
  };
  
  const selectGender = (gender) => {
    mentor.value.gender = gender;
  };
  
  const saveChanges = () => {
    console.log(mentor.value)
    userStore.updateMentor(mentor.value.id, mentor.value).then(() => {
    });
  };
  
  const updateLatLng = (latLng) => {
    if(latLng.id=='mentorEdit'){
      mentor.value.latitude = latLng.lat;
      mentor.value.longitude = latLng.lng;
    }
  }
  </script>
  
  <style scoped>
  /* 스타일 생략 */
  </style>
  