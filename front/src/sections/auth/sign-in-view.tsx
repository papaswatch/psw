import { useState, useCallback, KeyboardEvent } from 'react';
import { useNavigate } from 'react-router-dom';

import Box from '@mui/material/Box';
import Link from '@mui/material/Link';
import Divider from '@mui/material/Divider';
import TextField from '@mui/material/TextField';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import LoadingButton from '@mui/lab/LoadingButton';
import InputAdornment from '@mui/material/InputAdornment';

import { useRouter } from 'src/routes/hooks';

import { Iconify } from 'src/components/iconify';
import { useLoginMutation, useMeMutation } from '../../middleware/query/user-query';
import { KeyValue } from '../../types/common-type';
import { useUserStore } from '../../middleware/store/user-store';
import { setUserAuth } from '../../hooks/use-user-auth';

// ----------------------------------------------------------------------

export function SignInView() {
  const router = useRouter();

  const [userId, setUserId] = useState<string>('')
  const [pwd, setPwd] = useState<string>('')
  const [showPassword, setShowPassword] = useState(false);

  const {setUser} = useUserStore()

  const {mutateAsync: loginMutateAsync} = useLoginMutation()
  const {mutateAsync: meMutateAsync} = useMeMutation();
  const navigate = useNavigate();


  const signIn = useCallback(() => {
    if (!userId) {
      alert("사용자 아이디를 입력하세요.")
      return;
    }
    if (!pwd) {
      alert("사용자 비밀번호를 입력하세요.")
      return;
    }
    const handleLogin = async (req: KeyValue<string, string>): Promise<void> => {
      return loginMutateAsync(req)
    }

    /* 로그인을 서버로 요청한다. */
    handleLogin({ key: userId, value: pwd })
      .then(() => {
        /* 요청이 성공이면  */
        meMutateAsync().then(r => setUserAuth(r));
        navigate('/')
      })
      .catch(() => {
        console.error("failed to login")
      })

  }, [loginMutateAsync, meMutateAsync, pwd, navigate, userId]);

  const handleSignInClick = () => {
    signIn()
  }

  const handleSignInEnter = (e: KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'Enter') {
      signIn()
    }
  }

  const renderForm = (
    <Box display="flex" flexDirection="column" width="100%" alignItems="flex-end" maxWidth="400px">
      <TextField
        fullWidth
        name="id"
        label="아이디"
        value={userId}
        onChange={(e) => setUserId(e.target.value)}
        InputLabelProps={{ shrink: true }}
        sx={{ mb: 3 }}
      />

      <Link variant="body2" color="inherit" sx={{ mb: 1.5 }}>
        비밀번호를 잊어버리셨나요?
      </Link>

      <TextField
        fullWidth
        name="password"
        label="비밀번호"
        value={pwd}
        onChange={(e) => setPwd(e.target.value)}
        InputLabelProps={{ shrink: true }}
        type={showPassword ? 'text' : 'password'}
        InputProps={{
          endAdornment: (
            <InputAdornment position="end">
              <IconButton onClick={() => setShowPassword(!showPassword)} edge="end">
                <Iconify icon={showPassword ? 'solar:eye-bold' : 'solar:eye-closed-bold'} />
              </IconButton>
            </InputAdornment>
          ),
        }}
        sx={{ mb: 3 }}
        onKeyDown={handleSignInEnter}
      />

      <LoadingButton
        fullWidth
        size="large"
        type="submit"
        color="inherit"
        variant="contained"
        onClick={handleSignInClick}
      >
        로그인
      </LoadingButton>
    </Box>
  );

  return (
    <Box gap={1.5} display="flex" flexDirection="column" alignItems="center" sx={{ mb: 5 }}>
      <Box display="flex" flexDirection="column" alignItems="center" sx={{ mb: 5 }}>
        <Typography variant="h5">로그인</Typography>
        <Typography variant="body2" color="text.secondary">
          아직 회원이 아니신가요?
          <Link variant="subtitle2" sx={{ ml: 0.5, cursor: "pointer" }} onClick={() => navigate("/sign-up")}>
            가입하기
          </Link>
        </Typography>
      </Box>

      {renderForm}

      <Divider sx={{ my: 3, '&::before, &::after': { borderTopStyle: 'dashed' } }}>
        <Typography
          variant="overline"
          sx={{ color: 'text.secondary', fontWeight: 'fontWeightMedium' }}
        >
          OR
        </Typography>
      </Divider>

      <Box gap={1} display="flex" justifyContent="center">
        <IconButton color="inherit">
          <Iconify icon="logos:google-icon" />
        </IconButton>
        <IconButton color="inherit">
          <Iconify icon="eva:github-fill" />
        </IconButton>
        <IconButton color="inherit">
          <Iconify icon="ri:twitter-x-fill" />
        </IconButton>
      </Box>
    </Box>
  );
}
