import { useMutation, UseMutationResult } from '@tanstack/react-query';
import { AxiosError } from 'axios';
import api, { API } from '../api/api';
import { KeyValue, Response } from '../../types/common-type';
import { LoginUserInfo, SellerBank, SignupInfo } from '../../types/user-type';

export const useLoginMutation = (): UseMutationResult<void, AxiosError, KeyValue<string, string>> => {
  return useMutation({
    mutationFn: (req) => api.post(API.USER.LOGIN, req)
  })
}

export const useMeMutation = (): UseMutationResult<LoginUserInfo, AxiosError, void> => {
  return useMutation({
    mutationFn: () => api.get<LoginUserInfo>(API.USER.ME).then(r => r?.data)
  })
}

export const useSignupMutation = (): UseMutationResult<boolean|undefined, AxiosError, SignupInfo> => {
  return useMutation({
    mutationFn: (req) => api.post<Response<boolean>>(API.USER.SIGNUP, req).then(r => r?.data?.data)
  })
}

export const useLogoutMutation = (): UseMutationResult<void, AxiosError, void> => {
  return useMutation({
    mutationFn: () => api.post(API.USER.LOGOUT)
  })
}

export const useSellerMutation = (): UseMutationResult<boolean, AxiosError, { bank: SellerBank, certificationFile: File }> => {
  return useMutation({
    mutationFn: async (req) => {
      const formData = new FormData();

      // JSON 데이터를 FormData에 추가
      formData.append(
        'sellerValidateReq',
        new Blob([JSON.stringify(req.bank)], { type: 'application/json' })
      );

      // 파일 데이터를 FormData에 추가
      formData.append('certificationFile', req.certificationFile);

      const r = await api.post<Response<boolean>>(API.USER.SELLER, formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });
      return !!r?.data?.data;
    },
  });
};