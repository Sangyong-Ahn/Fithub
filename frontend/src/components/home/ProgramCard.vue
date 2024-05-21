<template>
    <div class="card program-card flex-row m-2 p-3 rounded-4" :style="bgStyle">
        <div class="card-body">
            <h5>{{ program.title }}</h5>
            <p>{{ program.mentorInfo.name }} 멘토</p>
            <div class="d-flex justify-content-between">
                <p class="card-text">⭐{{ program.mentorInfo.reviewAvgScore.toFixed(1) }} (+{{ program.mentorInfo.reviews.length }})</p>
                <p class="card-text">{{ getReservationStatus(program.reservationStartDate, program.reservationEndDate) }}</p>
            </div>
        </div>
    </div>
</template>

<script setup>
const props = defineProps({
    program: Object
}) 

import { reactive, ref } from 'vue';

let imgUrl = props.program.thumbnail;
const img = new Image();
const bgStyle = reactive({})

new Promise((resolve)=>{
  img.src = imgUrl;
  img.onload = () => resolve()
  img.onerror = () => {
    imgUrl = `/src/assets/sports/thumbnail/${props.program.id}.jpg`
    resolve();
  }
}).then(()=>{
  bgStyle['background-image'] =`linear-gradient(rgba(0, 0, 0, 0.5), rgba(0, 0, 0, 0.8)), url('${imgUrl}')`
  bgStyle['background-size'] = '100%';
})

const currentDate = new Date();

const getReservationStatus = (reservationStartDate, reservationEndDate) => {
  const start = new Date(reservationStartDate);
  const end = new Date(reservationEndDate);

  if (currentDate < start) {
    const differenceInTime = start.getTime() - currentDate.getTime();
    const differenceInDays = Math.ceil(differenceInTime / (1000 * 3600 * 24)); // 밀리초(ms)를 일(days)로 변환
    return `(D-${differenceInDays})`;
  } else if (currentDate >= start && currentDate <= end) {
    return '예약 가능';
  } else {
    return '예약 마감';
  }
};

</script>

<style scoped>
img {
    height: 100px;
    border: 1px solid black;
}
.card-body {
    padding: 0px 16px;
}

.card-body > p {
  color: #9d9d9d;
}

.program-card {
  background-repeat: no-repeat;
  background-position: center;
  background-size: cover;
  color:white;
  border: 1px solid black;
}

.card-text {
    margin-bottom: 0px;
}
</style>