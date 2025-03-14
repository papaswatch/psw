import axios from "axios";

const api = axios.create({
  withCredentials: true,
  timeout: 30000,
  headers: {
    'Content-Type': 'application/json'
  }
});

const apiUrl = import.meta.env.MODE === 'development' ? "http://localhost:18080/psw" : "/psw"

export const API = {
  USER: {
    SIGNUP: `${apiUrl}/user/signup`,
    LOGIN: `${apiUrl}/user/login`,
    ME: `${apiUrl}/user/me`,
  },
};

export default api;
