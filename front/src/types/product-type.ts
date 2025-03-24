export type CreateProductReq = {
    name: string;
    brand: string;
    price: string;
    stock: string;
    description: string;
    hashtags?: string[];
}

export type ProductOrder =
    | "RECENT_CREATED"
    | "HIGH_LIKED"
    | "HIGH_PRICE"
    | "OLD_CREATED"
    | "LOW_LIKED"
    | "LOW_PRICE"

export type GetProductReq = {
    page: number;
    rows: number;

    keyword: string;
    order: ProductOrder;
}

export type Product = {
    productId: number;
    name: string;
    contents: string;
    brand: string;
    stock: number;
    price: number;
    liked: number;
    seller: string;
    imgFilePath: string;
    imgHashName: string;
    imgExtension: string;
    hashtags: string[];
}

