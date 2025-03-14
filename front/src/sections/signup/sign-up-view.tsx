import { useCallback, useState } from 'react';
import { useNavigate } from 'react-router-dom';

import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import { debounce } from '@mui/material';
import Button from '@mui/material/Button';
import { useSignupMutation } from '../../middleware/query/user-query';
import { useModalStore } from '../../middleware/store/modal-store';
import { formatPhoneNumber, isValidPhoneNumber } from '../../utils/format-number';

export function SignUpView() {

  const navigate = useNavigate()

  const [userId, setUserId] = useState('');
  const [pwd, setPwd] = useState('');
  const [confirmPwd, setConfirmPwd] = useState('');
  const [name, setName] = useState('');
  const [phone, setPhone] = useState('');
  const [email, setEmail] = useState('');

  const [errors, setErrors] = useState({
    userId: '',
    pwd: '',
    confirmPwd: '',
    phone: '',
    email: '',
  });

  const {modal, setModal} = useModalStore()

  const {mutateAsync: signupMutateAsync} = useSignupMutation()

  // Debounce를 활용한 입력 검증 함수
  const validateInput = debounce((field: string, value: string) => {
      setErrors((prevErrors) => {
        const newErrors = { ...prevErrors };

        if (field === 'userId') {
          newErrors.userId = value.length < 4 ? '아이디는 최소 4자 이상 입력해야 합니다.' : '';
        }

        if (field === 'pwd') {
          newErrors.pwd = value.length < 6 ? '비밀번호는 최소 6자 이상이어야 합니다.' : '';
        }

        if (field === 'confirmPwd') {
          newErrors.confirmPwd = value !== pwd ? '비밀번호가 일치하지 않습니다.' : '';
        }

        if (field === 'email') {
          const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
          newErrors.email = !emailRegex.test(value) ? '올바른 이메일 형식이 아닙니다.' : '';
        }

        if (field === 'phone') {
          const phoneRegex = /^[0-9]{10,11}$/;
          /* newErrors.phone = !phoneRegex.test(value) ? '올바른 전화번호를 입력하세요.' : ''; */
          newErrors.phone = !isValidPhoneNumber(phone) ? '올바른 전화번호를 입력하세요.' : '';
        }

        return newErrors;
      });
    }, 500)

  const handleChange = (field: string, value: string) => {
    if (field === 'userId') setUserId(value);
    if (field === 'pwd') setPwd(value);
    if (field === 'confirmPwd') setConfirmPwd(value);
    if (field === 'name') setName(value);
    if (field === 'email') setEmail(value);

    validateInput(field, value);
  };

  const handlePhoneChange = (value: string) => {
    const formatted = formatPhoneNumber(value);
    setPhone(formatted)
  }

  // 모든 필드가 유효한 경우 버튼 활성화
  const isFormValid = Object.values(errors).every((err) => err === '') &&
    userId && pwd && confirmPwd && name && phone && email;

  const handleSignUp = () => {
    signupMutateAsync({ userId, pwd, name, phone, email})
      .then(() => {
        setModal({ isOpen: true, header: '알림', content: '회원가입이 완료되었습니다.', callback: () => navigate('/') });
      })
      .catch(() => {
        setModal({ isOpen: true, header: '알림', content: '회원가입에 문제가 발생했습니다.\n관리자에게 문의해주세요.'})
      })
  }

  return (
    <Box gap={1.5} display="flex" flexDirection="column" alignItems="center" sx={{ mb: 5 }}>
      <Typography variant="h5">회원가입</Typography>

      <Box display="flex" flexDirection="column" width="100%" maxWidth="400px">
        <TextField
          fullWidth
          label="아이디 *"
          value={userId}
          onChange={(e) => handleChange('userId', e.target.value)}
          error={!!errors.userId}
          helperText={errors.userId}
          sx={{ mb: 2 }}
        />

        <TextField
          fullWidth
          label="비밀번호 *"
          type="password"
          value={pwd}
          onChange={(e) => handleChange('pwd', e.target.value)}
          error={!!errors.pwd}
          helperText={errors.pwd}
          sx={{ mb: 2 }}
        />

        <TextField
          fullWidth
          label="비밀번호 확인 *"
          type="password"
          value={confirmPwd}
          onChange={(e) => handleChange('confirmPwd', e.target.value)}
          error={!!errors.confirmPwd}
          helperText={errors.confirmPwd}
          sx={{ mb: 2 }}
        />

        <TextField
          fullWidth
          label="이름 *"
          value={name}
          onChange={(e) => handleChange('name', e.target.value)}
          sx={{ mb: 2 }}
        />

        <TextField
          fullWidth
          label="전화번호 *"
          value={phone}
          onChange={(e) => handlePhoneChange(e.target.value)}
          error={!!errors.phone}
          helperText={errors.phone}
          sx={{ mb: 2 }}
        />

        <TextField
          fullWidth
          label="이메일 *"
          value={email}
          onChange={(e) => handleChange('email', e.target.value)}
          error={!!errors.email}
          helperText={errors.email}
          sx={{ mb: 2 }}
        />

        <Button
          variant="contained"
          color="primary"
          fullWidth
          sx={{ mt: 2 }}
          disabled={!isFormValid}
          onClick={handleSignUp}
        >
          회원가입
        </Button>
      </Box>
    </Box>
  );
}