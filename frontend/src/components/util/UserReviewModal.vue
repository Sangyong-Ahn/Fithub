<template>
    <div class="modal fade" id="reviewModal" tabindex="-1" aria-labelledby="chatModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
  
          <!-- Modal Header -->
          <div class="modal-header justify-content-center">
            <h5 class="modal-title" id="chatModalLabel">후기 작성</h5>
          </div>
  
          <!-- Modal Body -->
          <div ref="modalBody" class="modal-body">
            <div class="star_rating">
              <span 
                v-for="index in 5" 
                :key="index" 
                class="star" 
                :class="{ on: index <= rating }" 
                @click="setRating(index)">
                &#9733;
              </span>
            </div>
  
            <textarea class="star_box" placeholder="리뷰 내용을 작성해주세요." v-model="content"></textarea>
  
            <input type="submit" class="submitBtn" value="리뷰 등록" @click="insertReview"/>
          </div>
  
        </div>
      </div>
    </div>
</template>
    
<script setup>
import { ref, watch } from 'vue';
import { useUserStore } from '@/stores/userStore.js'
import { useProgramStore } from '@/stores/programStore.js'
import { useReviewStore } from '@/stores/reviewStore.js'

const userStore = useUserStore();
const programStore = useProgramStore();
const reviewStore = useReviewStore();

const content = ref('');
const modalBody = ref(null);
const rating = ref(0);

const setRating = (value) => {
    rating.value = value;
};

const insertReview = () => {
  if (typeof content.value === 'string' && content.value.trim().length === 0) {
    return;
  }
  reviewStore.insertReview(
    {
      mentorId: programStore.program.mentorId,
      programId: programStore.program.id,
      userId: userStore.loginUser.id,
      content: content.value,
      score: rating.value,
    }
  )
  .then(()=>{
    $('#reviewModal').modal('hide');
    // programstore 업데이트.    
    programStore.getProgram(programStore.program.id)
  })
  content.value = '';
  rating.value = 0;
};

</script>

<style scoped>
.star_rating {
width: 100%;
display: flex;
justify-content: center;
margin-bottom: 15px;
}

.star {
font-size: 25px;
cursor: pointer;
color: lightgray;
transition: color 0.2s;
}

.star.on {
color: gold;
}

.star_box {
width: 100%;
box-sizing: border-box;
margin: 15px 0;
background: #F3F4F8;
border: 0;
border-radius: 10px;
height: 200px;
resize: none;
padding: 15px;
font-size: 15px;
font-family: sans-serif;
}

.submitBtn {
display: block;
width: 100%;
font-weight: bold;
border: 0;
border-radius: 10px;
padding: 15px 0;
font-size: 1.1em;
text-align: center;
background: rgb(194, 248, 180);
}
</style>
