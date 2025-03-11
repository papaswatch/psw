import axios from "axios";

const api = axios.create({
  withCredentials: true,
  timeout: 30000,
  headers: {
    'Content-Type': 'application/json'
  }
});

const apiUrl = "/psw"

export const API = {
  USER: {
    LOGIN: `${apiUrl}/user/login`,
    ME: `${apiUrl}/user/me`
  }
}

export default api;
