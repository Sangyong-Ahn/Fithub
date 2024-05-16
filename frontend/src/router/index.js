import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import DetailView from '@/views/DetailView.vue'
import ProgramCreate from '@/components/detail/ProgramCreate.vue'

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
  ]
}

)

export default router
