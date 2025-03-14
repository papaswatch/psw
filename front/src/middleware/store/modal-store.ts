import { create } from 'zustand/react';
import { ModalProps } from '../../types/modal-type';

interface ModalState {
  modal: ModalProps;
  setModal: (modal: ModalProps) => void;
  clearModal: () => void;
}

export const useModalStore = create<ModalState>((set) => ({
  modal: { isOpen: false, header: '', content: '' },
  setModal: (modal) => {
    console.log("Modal opened", modal);  // 로그 확인
    set({ modal });
  },
  clearModal: () => set((state) => ({...state, modal: { isOpen: false, header: '', content: '' } }))
}))