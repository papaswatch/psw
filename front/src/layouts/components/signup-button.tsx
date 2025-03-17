import React from 'react';
import IconButton, { IconButtonProps } from '@mui/material/IconButton';
import Box from '@mui/material/Box';
import { useNavigate } from 'react-router-dom';

export type SignupButtonProps = IconButtonProps & {
  data?: {
    value: string;
    label: string;
    icon: string;
  }
}

export const SingupButton: React.FC<SignupButtonProps> = ({ data, sx, ...other}) => {

  const navigate = useNavigate()

  return (
    <IconButton
      onClick={() => navigate('/sign-up')}
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