<template>
  <!-- Regist Modal -->
  <div>
    
  <div class="modal fade" id="registModal" tabindex="-1" aria-labelledby="registModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header justify-content-center">
          <h1 class="modal-title fs-5" id="registModalLabel">회원가입</h1>
        </div>
        <div class="modal-body">
          <div class="m-3">
            <!-- 이메일 입력 -->
            <input type="email" v-model="user.email" class="form-control custom-input" id="registEmail" placeholder="이메일을 입력하세요">
          </div>
          <div class="m-3">
            <!-- 비밀번호 입력 -->
            <input type="password" v-model="user.password" class="form-control custom-input" id="registPassword" placeholder="비밀번호를 입력하세요">
          </div>
          <div class="m-3">
            <!-- 이름 입력 -->
            <input type="text" v-model="user.name" class="form-control custom-input" id="registName" placeholder="이름을 입력하세요">
          </div>
          <div class="m-3">
            <!-- 생년월일 입력 -->
            <input type="text" v-model="user.dateOfBirth" class="form-control custom-input" id="registDOB" placeholder="생년월일 8자리를 입력하세요">
          </div>
          <div class="m-3 d-flex justify-content-between">
            <!-- 성별 선택 -->
            <input type="radio" v-model="user.gender" class="btn-check" name="user-gender" id="male" value="M">
            <label class="btn btn-outline-secondary custom-gender" for="male">남</label>

            <input type="radio" v-model="user.gender" class="btn-check" name="user-gender" id="female" value="F">
            <label class="btn btn-outline-secondary custom-gender" for="female">여</label>
          </div>
          <div class="m-3">
            <!-- 전화번호 입력 -->
            <input type="text" v-model="user.phoneNumber" class="form-control custom-input" id="registPhoneNumber" placeholder="전화번호를 입력하세요( - 제외)">
          </div>
          <div class="m-3">
            <!-- 위치 선택 -->
            <Location 
              :lat="latitude"
              :lng="longitude"
              @update-lat-lng="updateLatLng"
            />
          </div>
          <div style="color: red">{{ store.errMsg }}</div>

        </div>
        <div class="modal-footer justify-content-around">
          <button type="button" @click="resetInputs" class="btn" data-bs-toggle="modal" data-bs-target="#mentorRegistModal">멘토 회원가입</button>
          <button type="button" @click="userCreate" class="btn">가입하기</button>
        </div>
      </div>
    </div>
  </div>
  
  <!-- MentorRegist Modal -->
  <div class="modal fade" id="mentorRegistModal" tabindex="-1" aria-labelledby="mentorRegistModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header justify-content-center">
          <h1 class="modal-title fs-5" id="mentorRegistModalLabel">멘토 회원가입</h1>
        </div>
        <div class="modal-body">
          <div class="m-3">
            <!-- 이메일 입력 -->
            <input type="email" v-model="mentor.email" class="form-control custom-input" id="mentorRegistEmail" placeholder="이메일을 입력하세요">
          </div>
          <div class="m-3">
            <!-- 비밀번호 입력 -->
            <input type="password" v-model="mentor.password" class="form-control custom-input" id="mentorRegistPassword" placeholder="비밀번호를 입력하세요">
          </div>
          <div class="m-3">
            <!-- 이름 입력 -->
            <input type="text" v-model="mentor.name" class="form-control custom-input" id="mentorRegistName" placeholder="이름을 입력하세요">
          </div>
          <div class="m-3">
            <!-- 생년월일 입력 -->
            <input type="text" v-model="mentor.dateOfBirth" class="form-control custom-input" id="mentorRegistDOB" placeholder="생년월일 8자리를 입력하세요">
          </div>
          <div class="m-3 d-flex justify-content-between">
            <!-- 성별 선택 -->
            <input type="radio" v-model="mentor.gender" class="btn-check" name="mentor-gender" id="maleMentor" value="M">
            <label class="btn btn-outline-secondary custom-gender" for="maleMentor">남</label>

            <input type="radio" v-model="mentor.gender" class="btn-check" name="mentor-gender" id="femaleMentor" value="F">
            <label class="btn btn-outline-secondary custom-gender" for="femaleMentor">여</label>
          </div>
          <div class="m-3">
            <!-- 전화번호 입력 -->
            <input type="text" v-model="mentor.phoneNumber" class="form-control custom-input" id="mentorRegistPhoneNumber" placeholder="전화번호를 입력하세요( - 제외)">
          </div>
          <div style="color: red">{{ store.errMsg }}</div>

        </div>
        <div class="modal-footer justify-content-around">
          <button type="button" @click="resetInputs" class="btn" data-bs-toggle="modal" data-bs-target="#registModal">일반 회원가입</button>
          <button type="button" @click="mentorCreate" class="btn">가입하기</button>
        </div>
      </div>
    </div>
  </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useUserStore } from "@/stores/userStore";
import Location from "@/components/user/Location.vue";
import { defaultLatLng } from "@/common/common";

const store = useUserStore()
const user = ref({
  email: '', password: '', name: '', dateOfBirth: '', gender: '', phoneNumber: ''
})

const mentor = ref({
  email: '', password: '', name: '', dateOfBirth: '', gender: '', phoneNumber: ''
})

const latitude = ref(defaultLatLng.lat)
const longitude = ref(defaultLatLng.lng)

const userCreate = function () {
    user.value.latitude = latitude.value
    user.value.longitude = longitude.value
    store.userCreate(user.value)
}

const mentorCreate = function () {
    mentor.value.latitude = latitude.value
    mentor.value.longitude = longitude.value
    store.mentorCreate(mentor.value)
}

const resetInputs = function() {
  // 사용자 회원가입 입력 초기화
  user.value = {
    email: '', password: '', name: '', dateOfBirth: '', gender: '', phoneNumber: ''
  };

  // 멘토 회원가입 입력 초기화
  mentor.value = {
    email: '', password: '', name: '', dateOfBirth: '', gender: '', phoneNumber: ''
  };

  latitude.value = defaultLatLng.lat;
  longitude.value = defaultLatLng.lng;
}

const updateLatLng = (latLng) => {
  latitude.value = latLng.lat;
  longitude.value = latLng.lng;
}

</script>

<style scoped>
.custom-input {
  height: 50px;
  border-radius: 0;
}
.custom-gender {
  width: 50%;
  border-radius: 0;
}
</style>
