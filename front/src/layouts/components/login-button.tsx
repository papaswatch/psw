import IconButton, { IconButtonProps } from '@mui/material/IconButton';
import React from 'react';
import Box from '@mui/material/Box';
import { RouterLink } from '../../routes/components';

export type LoginPopoverProps = IconButtonProps & {
  data?: {
    value: string;
    label: string;
    icon: string;
  };
};

export const LoginButton: React.FC<LoginPopoverProps> = ({ data, sx, ...other }) => {
  return (
    <IconButton
        component={RouterLink}
        href="/sign-in"
        onClick={() => console.log('HI')}
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
  );
};