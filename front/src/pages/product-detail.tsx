import { Helmet } from 'react-helmet-async';
import { CONFIG } from '../config-global';
import {useProductStore} from "../middleware/store/product-store";
import ProductDetailView from "../sections/product/view/product-detail-view";

export default function Page() {
    const {selectedProduct} = useProductStore()
    return (
        <>
            <Helmet>
                <title>{`${selectedProduct?.name} - ${CONFIG.appName}`}</title>
            </Helmet>
            <ProductDetailView/>
        </>
    )
}