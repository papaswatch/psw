import React from 'react';
import { useNavigate } from 'react-router-dom';

import IconButton, { IconButtonProps } from '@mui/material/IconButton';
import Box from '@mui/material/Box';
import { useModalStore } from '../../middleware/store/modal-store';
import { useLogoutMutation } from '../../middleware/query/user-query';
import { removeUserAuth } from '../../hooks/use-user-auth';

export type LogoutButtonProps = IconButtonProps & {
  data?: {
    value: string;
    label: string;
    icon: string;
  }
}

export const LogoutButton: React.FC<LogoutButtonProps> = ({ data, sx, ...other}) => {

  const navigate = useNavigate();

  const {setModal} = useModalStore()

  const {mutateAsync: logoutMutateAsync} = useLogoutMutation()

  const handleInit = () => {
    removeUserAuth()
    navigate(0)
  }

  const handleLogout = () => {
    setModal({
      isOpen: true,
      header: '알림',
      content: '로그아웃 하시겠습니까?',
      callback: async () => logoutMutateAsync().then(() => handleInit())
    })
  }

  return (
    <IconButton
      onClick={() => handleLogout()}
      sx={{
        width: 40,
        height: 40,
        ...sx,
      }}
    >
      <Box
        component="img"
        alt={data?.label}
        src={data?.icon}
        sx={{ width: 26, height: 20, borderRadius: 0.5, objectFit: 'cover' }}
      />
    </IconButton>
  )
}