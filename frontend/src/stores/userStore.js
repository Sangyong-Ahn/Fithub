import { ref } from 'vue'
import { defineStore } from "pinia";
import router from '@/router'
import axios from 'axios'
import { defaultLatLng } from '@/common/common';

export const useUserStore = defineStore("user", () => {

  const latitude = ref(defaultLatLng.lat)
  const longitude = ref(defaultLatLng.lng)

  return { latitude, longitude };
});
