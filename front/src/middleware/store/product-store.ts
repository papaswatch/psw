import {create} from "zustand/react";
import {GetProductReq, ProductOrder} from "../../types/product-type";

interface ProductStore {
    productParam: GetProductReq | null;
    setProductParam: (productParam: GetProductReq | null) => void;
}

export const useProductStore = create<ProductStore>((set) => ({
    productParam: { page: 1, rows: 24, keyword: '', order: "RECENT_CREATED" as ProductOrder },
    setProductParam: (productParam: GetProductReq | null) => set({productParam}),
}))