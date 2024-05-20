<template>
  <div class="card mb-4" style="max-width: 80%;">
    <div class="card-body">
      <form @submit.prevent="saveChanges">
        <!-- Form Fields -->
        <div class="row mb-3">
          <div class="col-md-6">
            <div class="form-group">
              <label for="userName">이름</label>
              <input type="text" class="form-control" id="userName" :value="user.name" readonly>
            </div>
          </div>
          <div class="col-md-6">
            <div class="form-group">
              <div class="btn-group" role="group" style="border: 1px solid #ced4da; border-radius: 4px;">
                <button type="button" class="btn" :class="{ 'btn-primary': user.gender === 'M', 'disabled': user.gender === 'M' }" @click="selectGender('M')" :disabled="user.gender === 'F'">남자</button>
                <button type="button" class="btn" :class="{ 'btn-primary': user.gender === 'F', 'disabled': user.gender === 'F' }" @click="selectGender('F')" :disabled="user.gender === 'M'">여자</button>
              </div>
            </div>
          </div>
        </div>
        <!-- Other Form Fields -->
        <div class="form-group mb-3">
          <label for="userEmail">이메일</label>
          <input type="email" class="form-control" id="userEmail" :value="user.email" @input="$emit('update:user', { ...user, email: $event.target.value })">
        </div>
        <div class="form-group mb-3">
          <label for="userPassword">비밀번호</label>
          <div class="input-group">
            <input type="password" class="form-control" id="userPassword" :value="user.password" @input="$emit('update:user', { ...user, password: $event.target.value })">
            <button class="btn btn-outline-secondary" type="button" @click="togglePasswordVisibility">
              <i class="bi bi-eye-slash-fill"></i>
            </button>
          </div>
        </div>
        <div class="form-group mb-3">
          <label for="confirmPassword">비밀번호 확인</label>
          <input type="password" class="form-control" id="confirmPassword" :value="confirmPassword" @input="$emit('update:confirmPassword', $event.target.value)">
          <small v-if="confirmPassword && user.password !== confirmPassword" class="text-danger">
            비밀번호가 일치하지 않습니다.
          </small>
        </div>
        <div class="form-group mb-3">
          <label for="userBirthdate">생년월일</label>
          <input type="text" class="form-control" id="userBirthdate" :value="user.dateOfBirth" readonly>
        </div>
        <div class="form-group mb-3">
          <label for="userPhoneNumber">전화번호</label>
          <input type="text" class="form-control" id="userPhoneNumber" :value="user.phoneNumber" @input="$emit('update:user', { ...user, phoneNumber: $event.target.value })">
        </div>
        <div class="form-group mb-3">
          <label for="profileImage">프로필 사진</label>
          <input type="file" class="form-control" id="profileImage" @change="onFileChange">
        </div>
        <div v-if="user.profileImageUrl" class="mb-3">
          <img :src="profileImageUrl" alt="프로필 사진 미리보기" class="img-fluid" style="width: 150px; height: 150px; object-fit: cover; border-radius: 5px;">
        </div>
        <button type="submit" class="btn btn-primary">저장</button>
      </form>
    </div>
  </div>
</template>

<script>
import { useUserStore } from '@/stores/userStore';

export default {
  props: {
    user: Object,
    confirmPassword: String,
  },
  emits: ['update:user', 'update:confirmPassword', 'save-changes'],
  computed: {
    profileImageUrl() {
      return this.user.profileImageUrl;
    }
  },
  methods: {
    onFileChange(event) {
      const file = event.target.files[0];
      if (file) {
        const reader = new FileReader();
        reader.onload = (e) => {
          this.$emit('update:user', { ...this.user, profileImageUrl: e.target.result });
        };
        reader.readAsDataURL(file);
      }
    },
    togglePasswordVisibility() {
      const input = document.getElementById('userPassword');
      input.type = input.type === 'password' ? 'text' : 'password';
    },
    selectGender(gender) {
      this.$emit('update:user', { ...this.user, gender });
    },
    saveChanges() {
      const userStore = useUserStore();
      userStore.updateUser(this.user.id, this.user).then(() => {
        this.$router.push(`/myPage`);
      });
    }
  }
}
</script>

<style scoped>
/* 스타일 생략 */
</style>
