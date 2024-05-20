import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import DetailView from '@/views/DetailView.vue'
import ProgramCreate from '@/components/detail/ProgramCreate.vue'
import UserMypageView from '@/views/UserMypageView.vue'
import MentorMypageView from '@/views/MentorMypageView.vue'
import { useUserStore } from '@/stores/userStore';
import axiosInstance from '@/axiosInstance';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/program/:id',
      name: 'programDetail',
      component: DetailView
    },
    {
      path: '/program/create',
      name: 'programCreate',
      component: ProgramCreate
    },
    {
      path: '/user/mypage',
      name: 'user-mypage',
      component: UserMypageView,
    },
    {
      path: '/mentor/mypage',
      name: 'mentor-mypage',
      component: MentorMypageView,
    },
  ]
}

)

// router.beforeEach(async (to, from, next) => {
//   const userStore = useUserStore();
//   const token = sessionStorage.getItem('accessToken');

//   if (to.matched.some(record => record.meta.requiresAuth)) {
//     if (!token) {
//       next('/');
//     } else {
//       try {
//         await axiosInstance.post('/api/auth/validate-token', null, {
//           headers: { 'Authorization': `Bearer ${token}` }
//         });
//         next();
//       } catch (error) {
//         try {
//           const refreshToken = sessionStorage.getItem('refreshToken');
//           const response = await axiosInstance.post('/api/auth/refresh-token', null, {
//             headers: { 'Authorization': `Bearer ${refreshToken}` }
//           });
//           const newAccessToken = response.data.accessToken;

//           sessionStorage.setItem('accessToken', newAccessToken);
//           axios.defaults.headers.common['Authorization'] = `Bearer ${newAccessToken}`;

//           next();
//         } catch (refreshError) {
//           userStore.logout();
//           next('/');
//         }
//       }
//     }
//   } else {
//     next();
//   }
// });
router.beforeEach(async (to, from, next) => {
  const userStore = useUserStore();
  const token = sessionStorage.getItem('accessToken');

  if (to.matched.some(record => record.meta.requiresAuth)) {
    if (!token) {
      next('/');
    } else {
      try {
        // 토큰 유효성 검사 요청
        await axiosInstance.post('/api/auth/validate-token', null, {
          headers: { 'Authorization': `Bearer ${token}` }
        });
        // 토큰이 유효하면 다음으로 이동
        next();
      } catch (error) {
        // 토큰이 유효하지 않은 경우
        console.error('Token validation error:', error.response.data);
        // 리프레시 토큰으로 토큰 재발급 시도
        try {
          const refreshToken = sessionStorage.getItem('refreshToken');
          const response = await axiosInstance.post('/api/auth/refresh-token', null, {
            headers: { 'Authorization': `Bearer ${refreshToken}` }
          });
          const newAccessToken = response.data.accessToken;

          // 새 액세스 토큰으로 sessionStorage 업데이트
          sessionStorage.setItem('accessToken', newAccessToken);
          // axios 인스턴스에 새 액세스 토큰 설정
          axios.defaults.headers.common['Authorization'] = `Bearer ${newAccessToken}`;

          // 다시 유효성 검사 시도
          next();
        } catch (refreshError) {
          // 리프레시 토큰으로 토큰 재발급 실패 시 로그아웃 처리
          console.error('Token refresh error:', refreshError.response.data);
          userStore.logout();
          next('/');
        }
      }
    }
  } else {
    next();
  }
});

export default router
