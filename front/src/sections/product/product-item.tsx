import React, {MouseEvent} from "react";
import {useNavigate} from "react-router-dom";

import Box from '@mui/material/Box';
import Link from '@mui/material/Link';
import Card from '@mui/material/Card';
import Stack from '@mui/material/Stack';

import Typography from '@mui/material/Typography';
import { Label } from 'src/components/label';
import { ColorPreview } from 'src/components/color-utils';
import {Product} from "../../types/product-type";
import {randomColors} from "../../_mock";
import {IMG_URL} from "../../middleware/api/config";
import {useProductStore} from "../../middleware/store/product-store";

// ----------------------------------------------------------------------

export type ProductItemProps = {
  id: string;
  name: string;
  price: number;
  status: string;
  coverUrl: string;
  colors: string[];
  priceSale: number | null;
};

const SLASH = "/"
const DOT = "."

export function ProductItem({ product }: { product: Product }) {

  const navigate = useNavigate()

  const {setSelectedProduct} = useProductStore()

  const renderStatus = (
    <Label
      variant="inverted"
      color="info"
      // color={(product.status === 'sale' && 'error') || 'info'}
      sx={{
        zIndex: 9,
        top: 16,
        right: 16,
        position: 'absolute',
        textTransform: 'uppercase',
      }}
    >
      NEW
    </Label>
  );

  const renderImg = (
    <Box
      component="img"
      alt={product.name}
      src={ IMG_URL + product.imgFilePath + SLASH + product.imgHashName + DOT + product.imgExtension }
      sx={{
        top: 0,
        width: 1,
        height: 1,
        objectFit: 'cover',
        position: 'absolute',
      }}
    />
  );

  const renderPrice = (
    <Typography variant="subtitle1">
        {product?.price ? product.price.toLocaleString() : '-'}
    </Typography>
  );

    const onClick = (e: MouseEvent<HTMLDivElement>) => {
        if (product?.productId) {
            setSelectedProduct(product)
            navigate(`/products/${product.productId}`)
        }
    }

    return (
    <Card>
      <Box sx={{ pt: '100%', position: 'relative' }} onClick={onClick}>
        {renderStatus}

        {renderImg}
      </Box>

      <Stack spacing={2} sx={{ p: 3 }} onClick={onClick}>
        <Link color="inherit" underline="hover" variant="subtitle2" noWrap>
          {product.name}
        </Link>

        <Box display="flex" alignItems="center" justifyContent="space-between">
          <ColorPreview colors={randomColors()} />
          {renderPrice}
        </Box>
      </Stack>
    </Card>
  );
}
