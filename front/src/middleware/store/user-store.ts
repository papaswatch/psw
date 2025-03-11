import {create} from "zustand/react";
import { LoginUserInfo } from '../../types/user-type';


interface PopupState {
  user: LoginUserInfo | null;
  setUser: (user: LoginUserInfo | null) => void;
}

export const useUserStore = create<PopupState>((set) => ({
  user: null,
  setUser: (user: LoginUserInfo | null) => set({user})
}))