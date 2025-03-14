import { useMutation, UseMutationResult } from '@tanstack/react-query';
import { AxiosError } from 'axios';
import api, { API } from '../api/api';
import { KeyValue, Response } from '../../types/common-type';
import { LoginUserInfo, SignupInfo } from '../../types/user-type';

export const useLoginMutation = (): UseMutationResult<void, AxiosError, KeyValue<string, string>> => {
  return useMutation({
    mutationFn: (req) => api.post(API.USER.LOGIN, req)
  })
}

export const useMeMutation = (): UseMutationResult<LoginUserInfo, AxiosError, void> => {
  return useMutation({
    mutationFn: () => api.post<LoginUserInfo>(API.USER.ME).then(r => r?.data)
  })
}

export const useSignupMutation = (): UseMutationResult<boolean|undefined, AxiosError, SignupInfo> => {
  return useMutation({
    mutationFn: (req) => api.post<Response<boolean>>(API.USER.SIGNUP, req).then(r => r?.data?.data)
  })
}