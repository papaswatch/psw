import { Helmet } from 'react-helmet-async';
import { CONFIG } from '../config-global';
import { SignUpView } from '../sections/signup/sign-up-view';

export default function Page() {
  return (
    <>
      <Helmet>
        <title>{`회원가입 - ${CONFIG.appName}`}</title>
      </Helmet>

      <SignUpView />
    </>
  )
}