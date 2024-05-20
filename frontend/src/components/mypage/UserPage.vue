<template>
  <div class="w-75">
    <div class="row my-4">
      <div class="col">
        <div class="card">
          <div class="card-body">
            <p class="card-text">이름: {{ user.name }}</p>
          </div>
        </div>
      </div>
      <div class="col-6">
        <div class="card">
          <div class="card-body">
            <p class="card-text">
              성별: 
              <span v-if="user.gender === 'M'">남</span>
              <span v-else-if="user.gender === 'F'">여</span>
              <span v-else>알 수 없음</span>
            </p>
          </div>
        </div>
      </div>
    </div>
    <div class="row my-4">
      <div class="col">
        <div class="card">
          <div class="card-body">
            <p class="card-text">이메일: {{ user.email }}</p>
          </div>
        </div>
      </div>
    </div>
    <div class="row my-4">
      <div class="col">
        <div class="card">
          <div class="card-body">
            <p class="card-text">생년월일: {{ user.dateOfBirth }}</p>
          </div>
        </div>
      </div>
    </div>
    <div class="row my-4">
      <div class="col">
        <div class="card">
          <div class="card-body">
            <p class="card-text">전화번호: {{ user.phoneNumber }}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: ['user']
}
</script>
