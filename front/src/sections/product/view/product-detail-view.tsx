import React from "react";

import {Viewer} from "@toast-ui/react-editor";

import {Card, CardContent, CardMedia, Grid, TextField, Typography} from "@mui/material";
import Box from "@mui/material/Box";

import {useProductStore} from "../../../middleware/store/product-store";

const ProductDetailView: React.FC = () => {

    const {selectedProduct} = useProductStore()


    return selectedProduct ?
        <Card sx={{margin: "auto", mt: 5, p: 3}}>
            <CardContent>
                <Typography variant="h5" gutterBottom>
                    상품 조회
                </Typography>

                <Grid container spacing={2}>
                    <Grid item xs={12}>
                        <TextField
                            fullWidth
                            label="상품명"
                            name="name"
                            value={selectedProduct?.name}
                            InputProps={{
                                readOnly: true
                            }}
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <TextField
                            fullWidth
                            label="상표"
                            name="brand"
                            value={selectedProduct?.brand}
                            required
                            InputProps={{
                                readOnly: true,
                            }}
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <TextField
                            fullWidth
                            label="가격"
                            name="price"
                            value={selectedProduct?.price}
                            required
                            InputProps={{
                                readOnly: true,
                            }}
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <TextField
                            fullWidth
                            label="수량"
                            name="stock"
                            value={selectedProduct?.stock}
                            required
                            InputProps={{
                                readOnly: true,
                            }}
                        />
                    </Grid>

                    {/* 해시태그 입력 */}
                    <Grid item xs={12}>

                        <Box display="flex" flexWrap="wrap" gap={1} mt={1}>
                            {selectedProduct?.hashtags.map((tag, index) => (
                                <Box key={index} sx={{
                                    display: "flex",
                                    alignItems: "center",
                                    bgcolor: "grey.200",
                                    p: 1,
                                    borderRadius: 1
                                }}>
                                    <Typography variant="body2">#{tag}</Typography>
                                </Box>
                            ))}
                        </Box>
                    </Grid>
                </Grid>
            </CardContent>
            <CardMedia>
                <Viewer initialValue={selectedProduct?.contents}/>
            </CardMedia>
        </Card>
        : null;
}

export default ProductDetailView