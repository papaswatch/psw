import {useMutation, UseMutationResult} from "@tanstack/react-query";
import {AxiosError} from "axios";
import {Product} from "../../types/product-type";
import api, {API} from "../api/api";
import {Response} from "../../types/common-type";

export const useProductRegisterMutation = (): UseMutationResult<boolean, AxiosError, { product: Product, images: File[] }> => {
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