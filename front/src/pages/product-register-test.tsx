import { Helmet } from 'react-helmet-async';
import { CONFIG } from '../config-global';
import ProductRegisterViewTest from "../sections/product/view/product-register-view-test";

export default function Page() {
  return (
    <>
      <Helmet>
        <title>{`상품등록 test - ${CONFIG.appName}`}</title>
      </Helmet>
      <ProductRegisterViewTest/>
    </>
  )
}