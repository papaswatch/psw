import {useMutation, UseMutationResult, useQuery, UseQueryResult} from "@tanstack/react-query";
import {AxiosError} from "axios";
import {CreateProductReq, CreateProductReqV2, GetProductReq, Product} from "../../types/product-type";
import api , {API} from "../api/config";
import {PageData, Response} from "../../types/common-type";
import {QueryKeys} from "./query-key";

export const useProductRegisterMutation = (): UseMutationResult<boolean, AxiosError, { product: CreateProductReq, images: File[] }> => {
    return useMutation({
        mutationFn: async (req) => {
            const formData = new FormData();

            // JSON 데이터를 FormData 에 추가
            formData.append(
                "productInfo",
                new Blob([JSON.stringify(req.product)], { type: 'application/json' })
            );

            // 파일 데이터를 FormData 에 추가

            // FileList를 반복하며 FormData에 추가
            for (let i = 0; i < req.images.length; i += 1) {
                formData.append("images", req.images[i]); // 'images' 는 서버에서 받을 키 이름
            }

            const r = await api.post<Response<boolean>>(API.PRODUCT, formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                }
            });
            return !!r?.data?.data
        }
    })
}

export const useProductRegisterV2Mutation = (): UseMutationResult<boolean, AxiosError, CreateProductReqV2> => {
    return useMutation({
        mutationFn: (req) => api.post<Response<boolean>>(API.PRODUCT_V2, req).then(r => !!r?.data?.data)
    })
}

export const useProductQuery = (req: GetProductReq | null): UseQueryResult<PageData<Product[]>, AxiosError> => {
    return useQuery({
        queryKey: [QueryKeys.PRODUCT, req],
        queryFn: () => api.get<Response<PageData<Product[]>>>(API.PRODUCT, { params: req }).then(r => r?.data?.data),
        enabled: !!req && !!req?.page && !!req?.rows
    })
}