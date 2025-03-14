import React from 'react';
import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  IconButton,
  Typography,
} from '@mui/material';

import CloseIcon from '@mui/icons-material/Close';
import { useModalStore } from '../../middleware/store/modal-store';

const Modal: React.FC = () => {
  const { modal, setModal, clearModal } = useModalStore();

  const handleClose = () => {
    setTimeout(() => {
      clearModal();
    }, 10); // 10ms 딜레이 추가
  }

  const handleConfirm = () => {
    handleClose()
    if (modal?.callback) {
      modal.callback();
    }
  }

  return (
    <>
      {/* MUI Dialog (팝업) */}
      <Dialog
        open={modal.isOpen}
        onClose={handleClose}
        maxWidth="sm"
        fullWidth
        disableEnforceFocus // 포커스 강제 이동 방지
        disableAutoFocus // 자동 포커스 방지
        disableRestoreFocus // 닫힐 때 포커스 복원 방지
        disablePortal // 모달을 body에 추가하는 기본 동작 해제
      >
        <DialogTitle
          sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}
        >
          {modal?.header}
          {/* <IconButton onClick={handleClose} sx={{ color: 'gray' }}> */}
          {/*   <CloseIcon /> */}
          {/* </IconButton> */}
        </DialogTitle>

        <DialogContent>
          <Typography variant="body1" sx={{ py: 2 }}>
            {modal?.content}
          </Typography>
        </DialogContent>

        <DialogActions>
          {modal?.cancel ? (
            <Button onClick={handleClose} color="secondary" variant="outlined">
              취소
            </Button>
          ) : null}

          <Button
            onClick={handleConfirm}
            color="primary"
            variant="contained"
          >
            확인
          </Button>
        </DialogActions>
      </Dialog>
    </>
  );
};

export default Modal;