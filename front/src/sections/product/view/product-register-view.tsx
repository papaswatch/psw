import { useState } from "react";
import {useNavigate} from "react-router-dom";

import {TextField, Button, Card, CardContent, Typography, Grid, debounce} from "@mui/material";
import Box from "@mui/material/Box";

export default function ProductRegisterView() {

  const navigate = useNavigate()

  const [name, setName] = useState<string>('')
  const [brand, setBrand] = useState<string>('')
  const [price, setPrice] = useState<string>('')
  const [stock, setStock] = useState<string>('')
  const [description, setDescription] = useState<string>('')
  const [images, setImages] = useState<FileList | null>(null)

  const [errors, setErrors] = useState({
    name: "",
    brand: "",
    price: "",
    stock: "",
    description: "",
  })

  // Debounce를 활용한 입력 검증 함수
  const validateInput = debounce((field: string, value: string) => {
    setErrors((prevErrors) => {
      const newErrors = { ...prevErrors };

      if (field === 'name') {
        newErrors.name = value.length < 1 ? '상품명을 입력해주세요.' : '';
      }

      if (field === 'brand') {
        newErrors.brand = value.length < 1 ? '상표를 입력해주세요.' : '';
      }

      if (field === 'price') {
        newErrors.price = price.length ? '가격을 입력해주세요.' : '';
      }

      if (field === 'stock') {
        newErrors.stock = stock.length ? '수량을 입력해주세요.' : '';
      }

      if (field === 'description') {
        newErrors.description = description.length ? '설명을 입력해주세요.' : '';
      }

      return newErrors;
    });
  }, 500)

  const handleChange = (field: string, value: string) => {
    if (field === 'name') setName(value);
    if (field === 'brand') setBrand(value);
    if (field === 'price') setPrice(value);
    if (field === 'stock') setStock(value);
    if (field === 'description') setDescription(value);

    validateInput(field, value);
  };

  const handleImageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target?.files?.length) {
      setImages(e.target.files);
    }
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
  };

  return (
    <Card sx={{ maxWidth: 500, margin: "auto", mt: 5, p: 3 }}>
      <CardContent>
        <Typography variant="h5" gutterBottom>
          상품 등록
        </Typography>
        <form onSubmit={handleSubmit}>
          <Grid container spacing={2}>
            <Grid item xs={12}>
              <TextField
                fullWidth
                label="상품명"
                name="name"
                value={name}
                onChange={e => handleChange('name', e.target.value)}
                required
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                  fullWidth
                  label="상표"
                  name="brand"
                  value={brand}
                  onChange={(e) => handleChange('brand', e.target.value)}
                  required
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                fullWidth
                label="가격"
                name="price"
                value={price}
                onChange={(e) => handleChange('price', e.target.value)}
                required
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                  fullWidth
                  label="수량"
                  name="stock"
                  value={stock}
                  onChange={(e) => handleChange('stock', e.target.value)}
                  required
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                fullWidth
                label="설명"
                name="description"
                multiline
                rows={3}
                value={description}
                onChange={e => handleChange('description', e.target.value)}
              />
            </Grid>
            <Grid item xs={12}>
              <input
                accept="image/*"
                type="file"
                multiple
                onChange={handleImageChange}
                style={{ display: "none" }}
                id="image-upload"
              />
              <label htmlFor="image-upload">
                <Button variant="contained" component="span" fullWidth>
                  {images?.length ? "이미지 변경" : "이미지 업로드"}
                </Button>
              </label>
              <Box gap={1.5} display="flex" alignItems="center" justifyContent="flex-start">
              {images?.length && Array.from(images).map((file, index) => (
                  <div key={index}>
                    <img
                        src={URL.createObjectURL(file)}
                        alt={`preview-${index}`}
                        style={{ width: 100, height: 100, objectFit: "cover", backgroundSize: "100% 100%", backgroundRepeat: 'no-repeat', borderRadius: 8 }}
                    />
                  </div>
              ))}
              </Box>
            </Grid>
            <Grid item xs={12}>
              <Button type="submit" variant="contained" color="primary" fullWidth>
                상품 등록
              </Button>
            </Grid>
          </Grid>
        </form>
      </CardContent>
    </Card>
  );
}
