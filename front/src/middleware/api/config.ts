import axios from "axios";

const api = axios.create({
  withCredentials: true,
  timeout: 30000,
  headers: {
    'Content-Type': 'application/json'
  }
});

export const IMG_URL = import.meta.env.MODE === "development" ? "http://localhost:18080/psw/imgs" : "/psw/imgs"
const apiUrl = import.meta.env.MODE === 'development' ? "http://localhost:18080/psw" : "/psw"

export const API = {
  USER: {
    SIGNUP: `${apiUrl}/user/signup`,
    LOGIN: `${apiUrl}/user/login`,
    LOGOUT: `${apiUrl}/user/logout`,
    ME: `${apiUrl}/user/me`,
    SELLER: `${apiUrl}/seller/validate`,
  },
  PRODUCT: `${apiUrl}/products`
};

export default api;
