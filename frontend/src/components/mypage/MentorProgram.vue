<!-- <template>
    <div class="w-100 m-3">
      <h4 class="mb-4">프로그램 리스트</h4>
      <table class="text-center">
        <thead>
          <tr>
            <th>프로그램 이름</th>
            <th>기간</th>
            <th>인원</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="program in mentorPrograms" :key="program.id">
            <td>{{ program.title }}</td>
            <td>
              <div>
                {{ formatDate(program.programStartDate) }}
              </div>
              ~ 
              <div>
                {{ formatDate(program.programEndDate) }}
              </div>
            </td>
            <td>{{ program.reserveNum }} / {{ program.capacity }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </template>
   -->
   <template>
    <div class="w-100 m-3">
      <h4 class="mb-4">프로그램 리스트</h4>
      <table class="text-center">
        <thead>
          <tr>
            <th>프로그램 이름</th>
            <th>기간</th>
            <th>인원</th>
          </tr>
        </thead>
        <tbody v-if="mentorPrograms && mentorPrograms.length > 0">
          <tr v-for="program in mentorPrograms" :key="program.id">
            <td>{{ program.title }}</td>
            <td>
              <div>
                {{ formatDate(program.programStartDate) }}
              </div>
              ~ 
              <div>
                {{ formatDate(program.programEndDate) }}
              </div>
            </td>
            <td>{{ program.reserveNum }} / {{ program.capacity }}</td>
          </tr>
        </tbody>
        <tbody v-else>
          <tr>
            <td colspan="3">프로그램이 없습니다.</td>
          </tr>
        </tbody>
      </table>
    </div>
  </template>
  <script setup>
  import { onMounted } from 'vue'
  import { useProgramStore } from '@/stores/programStore';
  import { useUserStore } from '@/stores/userStore';
  
  const { mentorPrograms, getMentorPrograms } = useProgramStore();
  const { loginUser, isMentor} = useUserStore();
  
  onMounted(() => {
    if (isMentor && loginUser && loginUser.id) {
      getMentorPrograms(loginUser.id)
    }
  })
  
  const formatDate = (dateString) => {
    const date = new Date(dateString)
    return date.toLocaleDateString()
  }
  </script>
  
  <style>
  table {
    width: 100%;
    border-collapse: collapse;
  }
  
  th, td {
    border: 1px solid #ddd;
    padding: 8px;
  }
  </style>