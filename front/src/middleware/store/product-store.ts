import {create} from "zustand/react";
import {GetProductReq, Product, ProductOrder} from "../../types/product-type";

interface ProductStore {
    // 상품 조회 파라미터
    productParam: GetProductReq | null;
    setProductParam: (productParam: GetProductReq | null) => void;

    // 상품 리스트에서 선택한 상품
    selectedProduct: Product | null;
    setSelectedProduct: (selectedProduct: Product | null) => void;
}

export const useProductStore = create<ProductStore>((set) => ({
    // 상품 조회 파라미터
    productParam: { page: 1, rows: 24, keyword: '', order: "RECENT_CREATED" as ProductOrder },
    setProductParam: (productParam: GetProductReq | null) => set({productParam}),

    // 상품 리스트에서 선택한 상품
    selectedProduct: null,
    setSelectedProduct: (selectedProduct: Product | null) => set({selectedProduct}),
}))
