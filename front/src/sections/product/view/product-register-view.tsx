import { useState } from "react";
import { TextField, Button, Card, CardContent, Typography, Grid } from "@mui/material";

export default function ProductRegisterView() {
  const [product, setProduct] = useState({
    name: "",
    price: "",
    description: "",
    image: null as File | null,
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setProduct({ ...product, [e.target.name]: e.target.value });
  };

  const handleImageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files) {
      setProduct({ ...product, image: e.target.files[0] });
    }
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    console.log("등록된 상품:", product);
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
                value={product.name}
                onChange={handleChange}
                required
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                fullWidth
                label="가격"
                name="price"
                type="number"
                value={product.price}
                onChange={handleChange}
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
                value={product.description}
                onChange={handleChange}
              />
            </Grid>
            <Grid item xs={12}>
              <input
                accept="image/*"
                type="file"
                onChange={handleImageChange}
                style={{ display: "none" }}
                id="image-upload"
              />
              <label htmlFor="image-upload">
                <Button variant="contained" component="span" fullWidth>
                  {product.image ? "이미지 변경" : "이미지 업로드"}
                </Button>
              </label>
              {product.image && <Typography mt={1}>{product.image.name}</Typography>}
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
