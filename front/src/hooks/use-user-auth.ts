import { useEffect } from 'react';
import { useUserStore } from '../middleware/store/user-store';
import { LoginUserInfo } from '../types/user-type';

export const AUTH_KEY = 'user_token';
export const SESSION_EXPIRATION_KEY = 'session_expiration'; // 세션 만료 시간 저장

export function useUserAuth() {
  const {setUser} = useUserStore()

  useEffect(() => {
    const user = localStorage.getItem(AUTH_KEY); // 로컬 스토리지에서 유저 정보 가져오기
    const sessionExpiration = localStorage.getItem(SESSION_EXPIRATION_KEY); // 세션 만료 시간
    if (user && sessionExpiration) {
      const expirationTime = parseInt(sessionExpiration, 10);  // 문자열을 숫자로 변환
      const currentTime = new Date().getTime();
      // 세션이 만료되지 않았으면 로그인 상태 유지
      if (currentTime < expirationTime) {
        // 로그인 상태 유지
        const data: LoginUserInfo = JSON.parse(user);
        setUser(data)
      } else {
        // 세션 만료 처리 (로그아웃 등)
        localStorage.removeItem(AUTH_KEY);
        localStorage.removeItem(SESSION_EXPIRATION_KEY);
      }
    }
  }, [setUser]);
}

export function setUserAuth(user: LoginUserInfo) {
  const currentTime = new Date().getTime();
  const sessionExpiration = currentTime + 30 * 60 * 1000; // 현재 시간에 30분을 더한 값
  localStorage.setItem(AUTH_KEY, JSON.stringify(user));
  localStorage.setItem(SESSION_EXPIRATION_KEY, sessionExpiration.toString());
}

export function removeUserAuth() {
  localStorage.removeItem(AUTH_KEY);
  localStorage.removeItem(SESSION_EXPIRATION_KEY);
}