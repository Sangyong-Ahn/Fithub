import axios from 'axios';
import router from './router';
import { useUserStore } from './stores/userStore';

const axiosInstance = axios.create({
  baseURL: 'http://localhost:8080',
});

axiosInstance.interceptors.request.use(
  config => {
    const token = sessionStorage.getItem('accessToken');
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`;
    }
    config.withCredentials = true;
    return config;
  },
  error => Promise.reject(error)
);

axiosInstance.interceptors.response.use(
  response => response,
  async error => {
    const userStore = useUserStore();

    if (error.response && error.response.status === 401) {
      const originalRequest = error.config;

      // Refresh token 로직 추가
      const refreshToken = sessionStorage.getItem('refreshToken');
      if (refreshToken && !originalRequest._retry) {
        originalRequest._retry = true;
        try {
          const response = await axios.post('/api/auth/refresh-token', null, {
            headers: { 'Authorization': `Bearer ${refreshToken}` },
            withCredentials: true,
          });
          const newAccessToken = response.data.accessToken;

          sessionStorage.setItem('accessToken', newAccessToken);
          axios.defaults.headers.common['Authorization'] = `Bearer ${newAccessToken}`;
          originalRequest.headers['Authorization'] = `Bearer ${newAccessToken}`;

          return axiosInstance(originalRequest);
        } catch (refreshError) {
          userStore.logout();
          router.push('/');
          return Promise.reject(refreshError);
        }
      } else {
        userStore.logout();
        router.push('/');
      }
    }
    return Promise.reject(error);
  }
);

export default axiosInstance;
