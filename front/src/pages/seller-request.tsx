import { Helmet } from 'react-helmet-async';
import { CONFIG } from '../config-global';
import { SellerRequestView } from '../sections/user/view/seller-request-view';

export default function Page() {
  return (
    <>
      <Helmet>
        <title>{`판매자 신청 - ${CONFIG.appName}`}</title>
      </Helmet>

      <SellerRequestView/>
    </>
  )
}