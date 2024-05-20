<template>
    <div class="w-75">
      <div class="row my-4">
        <div class="col">
          <div class="card">
            <div class="card-body">
              <p class="card-text">이름: {{ mentor.name }}</p>
            </div>
          </div>
        </div>
        <div class="col-6">
          <div class="card">
            <div class="card-body">
              <p class="card-text">
                성별: 
                <span v-if="mentor.gender === 'M'">남</span>
                <span v-else-if="mentor.gender === 'F'">여</span>
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
              <p class="card-text">이메일: {{ mentor.email }}</p>
            </div>
          </div>
        </div>
      </div>
      <div class="row my-4">
        <div class="col">
          <div class="card">
            <div class="card-body">
              <p class="card-text">생년월일: {{ mentor.dateOfBirth }}</p>
            </div>
          </div>
        </div>
      </div>
      <div class="row my-4">
        <div class="col">
          <div class="card">
            <div class="card-body">
              <p class="card-text">전화번호: {{ mentor.phoneNumber }}</p>
            </div>
          </div>
        </div>
      </div>
      <div class="row my-4">
        <div class="col">
          <div class="card">
            <div class="card-body">
              <p class="card-text" style="height:200px">소개글: {{ mentor.content }}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    props: ['mentor']
  }
  </script>
  