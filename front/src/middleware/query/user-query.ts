import { useMutation, UseMutationResult } from '@tanstack/react-query';
import { AxiosError } from 'axios';
import api, { API } from '../api/api';
import { KeyValue } from '../../types/common-type';
import { LoginUserInfo } from '../../types/user-type';

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