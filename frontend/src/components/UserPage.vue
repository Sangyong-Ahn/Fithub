<template>
    <div class="card mb-4" style="max-width: 80%;">
      <div class="card-body">
        <div class="row justify-content-center mb-3">
          <div class="col-md-auto">
            <!-- 프로필 사진 -->
          </div>
        </div>
        <div class="row mb-3">
          <div class="col">
            <div class="card">
              <div class="card-body">
                <p class="card-text">유저 이름: {{ user.name }}</p>
              </div>
            </div>
          </div>
          <div class="col-6">
            <div class="card">
              <div class="card-body">
                <p class="card-text">성별: {{ user.gender }}</p>
              </div>
            </div>
          </div>
        </div>
        <div class="row mb-3">
          <div class="col">
            <div class="card">
              <div class="card-body">
                <p class="card-text">이메일: {{ user.email }}</p>
              </div>
            </div>
          </div>
        </div>
        <div class="row mb-3">
          <div class="col">
            <div class="card">
              <div class="card-body">
                <p class="card-text">생년월일: {{ user.dateOfBirth }}</p>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col">
            <div class="card">
              <div class="card-body">
                <p class="card-text">전화번호: {{ user.phoneNumber }}</p>
              </div>
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
  