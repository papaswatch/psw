
export type LoginUserInfo = {
  loginId: string;
  email: string;
  phoneNumber: string;
  name: string;
}

export type SignupInfo = {
  userId: string;
  pwd: string;
  name: string;
  phone: string;
  email: string;
}

export type SellerBank = {
  userId: string;
  bankName: string;
  bankAccountNumber: string;
  bankAccountHolderName: string;
}