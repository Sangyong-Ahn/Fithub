<template>
  <div class="w-75">
    <div>
      <form @submit.prevent="saveChanges">
        <!-- Form Fields -->
        <div class="row my-4 align-items-end">
          <div class="col-md-6">
            <div class="form-group">
              <label for="userName">이름</label>
              <input type="text" class="form-control" id="userName" v-model="user.name">
            </div>
          </div>
          <div class="col-md-6">
            <div class="form-group text-end">
              <div class="btn-group w-100" role="group">
                <button type="button" class="btn" :class="{ 'btn-secondary': user.gender === 'M', 'disabled': user.gender === 'M' }" @click="selectGender('M')" :disabled="user.gender === 'F'">남자</button>
                <button type="button" class="btn" :class="{ 'btn-secondary': user.gender === 'F', 'disabled': user.gender === 'F' }" @click="selectGender('F')" :disabled="user.gender === 'M'">여자</button>
              </div>
            </div>
          </div>
        </div>
        <!-- Other Form Fields -->
        <div class="form-group my-4">
          <label for="userEmail">이메일</label>
          <input type="email" class="form-control" id="userEmail" v-model="user.email" readonly>
        </div>
        <div class="form-group my-4">
          <label for="userPassword">비밀번호</label>
          <div class="input-group">
            <input type="password" class="form-control" id="userPassword" v-model="user.password">
            <button class="btn btn-outline-secondary" type="button" @click="togglePasswordVisibility">
              <i class="bi bi-eye-slash-fill"></i>
            </button>
          </div>
        </div>
        <div class="form-group my-4">
          <label for="confirmPassword">비밀번호 확인</label>
          <input type="password" class="form-control" id="confirmPassword" v-model="confirmPassword">
          <small v-if="confirmPassword && user.password !== confirmPassword" class="text-danger">
            비밀번호가 일치하지 않습니다.
          </small>
        </div>
        <div class="form-group my-4">
          <label for="userBirthdate">생년월일</label>
          <input type="text" class="form-control" id="userBirthdate" v-model="user.dateOfBirth" readonly>
        </div>
        <div class="form-group my-4">
          <label for="userPhoneNumber">전화번호</label>
          <input type="text" class="form-control" id="userPhoneNumber" v-model="user.phoneNumber">
        </div>
        <div class="mx-3 my-5">
          <!-- 위치 선택 -->
          <UserMap 
            :id="'userEdit'"
            :lat="user.latitude"
            :lng="user.longitude"
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
  user: Object
});

const userStore = useUserStore();
const router = useRouter();

const user = ref({ ...props.user });
const confirmPassword = ref('');

onMounted(() => {
  user.value = { ...props.user };
});

const isPasswordMatch = computed(() => {
  return user.value.password === confirmPassword.value;
});

const togglePasswordVisibility = () => {
  const input = document.getElementById('userPassword');
  input.type = input.type === 'password' ? 'text' : 'password';
};

const selectGender = (gender) => {
  user.value.gender = gender;
};

const saveChanges = () => {
  console.log(user.value)
  userStore.updateUser(user.value.id, user.value).then(() => {
  });
};

const updateLatLng = (latLng) => {
  if(latLng.id=='userEdit'){
    user.value.latitude = latLng.lat;
    user.value.longitude = latLng.lng;
  }
}
</script>

<style scoped>
/* 스타일 생략 */
</style>
