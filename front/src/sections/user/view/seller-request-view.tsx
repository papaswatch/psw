import { useNavigate } from 'react-router-dom';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import { ChangeEvent, useRef, useState } from 'react';
import { CircularProgress, debounce } from '@mui/material';
import Button from '@mui/material/Button';
import { useSellerMutation } from '../../../middleware/query/user-query';
import { AUTH_KEY } from '../../../hooks/use-user-auth';
import { LoginUserInfo } from '../../../types/user-type';

export function SellerRequestView() {
  const navigate = useNavigate()

  const [bankName, setBankName] = useState<string>('')
  const [bankAccountNumber, setBankAccountNumber] = useState<string>('')
  const [bankAccountHolderName, setBankAccountHolderName] = useState<string>('')
  const [certificationFile, setCertificationFile] = useState<File | null>(null)

  const [errors, setErrors] = useState({
    bankName: '',
    bankAccountNumber: '',
    bankAccountHolderName: '',
    certificationFile: '',
  });

  const {mutateAsync: sellerMutateAsync} = useSellerMutation()

  // Debounce를 활용한 입력 검증 함수
  const validateInput = debounce((field: string, value: string) => {
    setErrors((prevErrors) => {
      const newErrors = { ...prevErrors };

      if (field === 'bankName') {
        newErrors.bankName = value.length < 1 ? '계좌 은행을 입력하세요.' : '';
      }

      if (field === 'bankAccountNumber') {
        newErrors.bankAccountNumber = value.length < 1 ? '계좌 번호를 입력하세요.' : '';
      }

      if (field === 'bankAccountHolderName') {
        newErrors.bankAccountHolderName = value.length < 1 ? '예금주를 입력하세요.' : '';
      }
      if (field === 'certificationFile') {
        newErrors.certificationFile = certificationFile ? '' : '사업자 등록증을 업로드하세요.';
      }

      return newErrors;
    });
  }, 500)

  const handleChange = (field: string, value: string) => {
    if (field === 'bankName') setBankName(value);
    if (field === 'bankAccountNumber') setBankAccountNumber(value);
    if (field === 'bankAccountHolderName') setBankAccountHolderName(value);

    validateInput(field, value);
  };

  const handleFileChange = (event: ChangeEvent<HTMLInputElement>) => {
    if (event?.target?.files?.length) {
      const file = event.target.files[0];
      if (!file) {
        setErrors(prev => ({ ...prev, certificationFile: '사업자 등록증을 등록하세요' }))
        return;
      }
      setCertificationFile(file);
      setErrors(prev => ({ ...prev, certificationFile: '' }))
    }
  };

  const isFormValid = Object.values(errors).every((err) => err === '') && bankName && bankAccountNumber && bankAccountHolderName && bankAccountHolderName && certificationFile;

  const handleSellerReq = () => {
    const item = localStorage.getItem(AUTH_KEY);
    if (bankName && bankAccountNumber && bankAccountHolderName && certificationFile && item) {
      const user: LoginUserInfo = JSON.parse(item);
      sellerMutateAsync({
        bank: {userId: user.loginId, bankName, bankAccountNumber, bankAccountHolderName},
        certificationFile
      })
        .then(() => navigate("/"))
    }
  }

  return (
    <Box gap={1.5} display="flex" flexDirection="column" alignItems="center" sx={{ mb: 5 }}>
      <Typography variant="h5">판매자 신청</Typography>

      <Box display="flex" flexDirection="column" width="100%" maxWidth="400px">
        <TextField
          fullWidth
          label="은행 *"
          value={bankName}
          onChange={(e) => handleChange('bankName', e.target.value)}
          error={!!errors.bankName}
          helperText={errors.bankName}
          sx={{ mb: 2 }}
        />

        <TextField
          fullWidth
          label="계좌번호 *"
          value={bankAccountNumber}
          onChange={(e) => handleChange('bankAccountNumber', e.target.value)}
          error={!!errors.bankAccountNumber}
          helperText={errors.bankAccountNumber}
          sx={{ mb: 2 }}
        />

        <TextField
          fullWidth
          label="예금주 *"
          value={bankAccountHolderName}
          onChange={(e) => handleChange('bankAccountHolderName', e.target.value)}
          error={!!errors.bankAccountHolderName}
          helperText={errors.bankAccountHolderName}
          sx={{ mb: 2 }}
        />

        <TextField
          fullWidth
          type="file"
          variant="outlined"
          placeholder="사업자 등록증을 업로드하세요."
          onChange={handleFileChange}
          InputProps={{
            readOnly: true,
          }}
          InputLabelProps={{ shrink: true }}
        />

        <Button
          variant="contained"
          color="primary"
          fullWidth
          sx={{ mt: 2 }}
          disabled={!isFormValid}
          onClick={handleSellerReq}
        >
          판매자 신청
        </Button>
      </Box>
    </Box>
  )
}