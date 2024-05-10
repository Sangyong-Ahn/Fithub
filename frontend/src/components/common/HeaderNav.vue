<template>
  <!-- Main Navbar -->
  <nav class="navbar navbar-expand-md py-3">
    <div class="container-fluid">
      <a class="navbar-brand" href="#">Fithub</a>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
        <ul class="navbar-nav">
          <li class="nav-item">
            <button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#loginModal">
              로그인
            </button>
          </li>
          <li class="nav-item">
            <button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#registModal">
              회원가입
            </button>
          </li>
        </ul>
      </div>
    </div>
  </nav>

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
            <input type="email" class="form-control custom-input" id="loginEmail" placeholder="이메일을 입력하세요">
          </div>
          <!-- 비밀번호 입력 -->
          <div class="m-3">
            <input type="password" class="form-control custom-input" id="loginPassword" placeholder="비밀번호를 입력하세요">
          </div>
        </div>
        <div class="modal-footer justify-content-around">
          <button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#mentorLoginModal">멘토 로그인</button>
          <button type="button" class="btn">로그인</button>
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
            <input type="email" class="form-control custom-input" id="mentorLoginEmail" placeholder="이메일을 입력하세요">
          </div>
          <!-- 멘토 비밀번호 입력 -->
          <div class="m-3">
            <input type="password" class="form-control custom-input" id="mentorLoginPassword" placeholder="비밀번호를 입력하세요">
          </div>
        </div>
        <div class="modal-footer justify-content-around">
          <button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#loginModal">일반 회원 로그인</button>
          <button type="button" class="btn">로그인</button>
        </div>
      </div>
    </div>
  </div>

  <!-- Regist Modal -->
  <div class="modal fade" id="registModal" tabindex="-1" aria-labelledby="registModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header justify-content-center">
          <h1 class="modal-title fs-5" id="registModalLabel">회원가입</h1>
        </div>
        <div class="modal-body">
          <div class="m-3">
            <input type="email" class="form-control custom-input" id="registEmail" placeholder="이메일을 입력하세요">
          </div>
          <div class="m-3">
            <input type="password" class="form-control custom-input" id="registPassword" placeholder="비밀번호를 입력하세요">
          </div>
          <div class="m-3">
            <input type="text" class="form-control custom-input" id="registName" placeholder="이름을 입력하세요">
          </div>
          <div class="m-3">
            <input type="text" class="form-control custom-input" id="registDOB" placeholder="생년월일 8자리를 입력하세요">
          </div>
          <div class="m-3 d-flex justify-content-between">
            <input type="radio" class="btn-check" name="user-gender" id="male" value="M">
            <label class="btn btn-outline-secondary custom-gender" for="male">남</label>

            <input type="radio" class="btn-check" name="user-gender" id="female" value="F">
            <label class="btn btn-outline-secondary custom-gender" for="female">여</label>
          </div>
          <div class="m-3">
            <input type="text" class="form-control custom-input" id="registPhoneNumber" placeholder="전화번호를 입력하세요">
          </div>

        </div>
        <div class="modal-footer justify-content-around">
          <button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#mentorRegistModal">멘토 회원가입</button>
          <button type="button" class="btn">가입하기</button>
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
            <input type="email" class="form-control custom-input" id="mentorRegistEmail" placeholder="이메일을 입력하세요">
          </div>
          <div class="m-3">
            <input type="password" class="form-control custom-input" id="mentorRegistPassword" placeholder="비밀번호를 입력하세요">
          </div>
          <div class="m-3">
            <input type="text" class="form-control custom-input" id="mentorRegistName" placeholder="이름을 입력하세요">
          </div>
          <div class="m-3">
            <input type="text" class="form-control custom-input" id="mentorRegistDOB" placeholder="생년월일 8자리를 입력하세요">
          </div>
          <div class="m-3 d-flex justify-content-between">
            <input type="radio" class="btn-check" name="mentor-gender" id="maleMentor" value="M">
            <label class="btn btn-outline-secondary custom-gender" for="maleMentor">남</label>

            <input type="radio" class="btn-check" name="mentor-gender" id="femaleMentor" value="F">
            <label class="btn btn-outline-secondary custom-gender" for="femaleMentor">여</label>
          </div>
          <div class="m-3">
            <input type="text" class="form-control custom-input" id="mentorRegistPhoneNumber" placeholder="전화번호를 입력하세요">
          </div>

        </div>
        <div class="modal-footer justify-content-around">
          <button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#registModal">일반 회원가입</button>
          <button type="button" class="btn">가입하기</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
// No script setup needed for this example.
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
