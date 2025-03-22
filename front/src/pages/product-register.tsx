import { Helmet } from 'react-helmet-async';
import { CONFIG } from '../config-global';
import ProductRegisterView from '../sections/product/view/product-register-view';

export default function Page() {
  return (
    <>
      <Helmet>
        <title>{`상품등록 - ${CONFIG.appName}`}</title>
      </Helmet>
      <ProductRegisterView/>
    </>
  )
}