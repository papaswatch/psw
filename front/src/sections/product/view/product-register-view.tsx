import { useState } from "react";
import {useNavigate} from "react-router-dom";

import {TextField, Button, Card, CardContent, Typography, Grid, debounce, Chip} from "@mui/material";
import Box from "@mui/material/Box";
import {useModalStore} from "../../../middleware/store/modal-store";
import {useProductRegisterMutation} from "../../../middleware/query/product-query";

export default function ProductRegisterView() {

  const navigate = useNavigate()

  const {setModal} = useModalStore()

  const {mutateAsync: productMutateAsync} = useProductRegisterMutation()

  const [name, setName] = useState<string>('')
  const [brand, setBrand] = useState<string>('')
  const [price, setPrice] = useState<string>('')
  const [stock, setStock] = useState<string>('')
  const [description, setDescription] = useState<string>('')

  const [hashtags, setHashtags] = useState<string[]>([]);
  const [tagInput, setTagInput] = useState<string>("");

  const [images, setImages] = useState<FileList | null>(null)

  const [errors, setErrors] = useState({
    name: "",
    brand: "",
    price: "",
    stock: "",
    description: "",
  })

  // 모든 필드가 유효한 경우 버튼 활성화
  const isFormValid = Object.values(errors).every((err) => err === '') && !!name && !!brand && !!price && !!stock && !!description && !!images;

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

  /* 이름, 상표, 가격, 재고, 설명 을 입력했을 때 핸들링 함수 */
  const handleChange = (field: string, value: string) => {
    if (field === 'name') setName(value);
    if (field === 'brand') setBrand(value);
    if (field === 'price') setPrice(value);
    if (field === 'stock') setStock(value);
    if (field === 'description') setDescription(value);

    validateInput(field, value);
  };

  /* 이미지 상태 처리 핸들링 함수 */
  const handleImageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target?.files?.length) {
      setImages(e.target.files);
    }
  };

  /* 태그 입력 핸들링 함수 */
  const handleTagKeyDown = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === "Enter" || e.key === " ") {
      e.preventDefault();
      const newTag = tagInput.trim();

      if (newTag && !hashtags.includes(newTag)) {
        setHashtags([...hashtags, newTag]);
        setTagInput("");
      }
    } else if (e.key === "Backspace" && tagInput === "") {
      setHashtags((prev) => prev.slice(0, -1));
    }
  };


  const handleRemoveTag = (index: number) => {
    setHashtags(hashtags.filter((_, i) => i !== index));
  };

  /* 제출 버튼 클릭 함수 */
  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    // console.log({
    //   name,
    //   brand,
    //   price,
    //   stock,
    //   description,
    //   images,
    //   hashtags, // 해시태그 추가
    // });
    if (images) {
      productMutateAsync({ product: { name, brand, price, stock, description, hashtags }, images: Array.from(images) })
          .then(r => console.log("success! r", r));
    }
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

            {/* 해시태그 입력 */}
            <Grid item xs={12}>
              <TextField
                  fullWidth
                  label="해시태그 (엔터 또는 띄어쓰기 입력)"
                  value={tagInput}
                  onChange={(e) => setTagInput(e.target.value)}
                  onKeyDown={handleTagKeyDown}
              />
              <Box display="flex" flexWrap="wrap" gap={1} mt={1}>
                {hashtags.map((tag, index) => (
                    <Box key={index} sx={{ display: "flex", alignItems: "center", bgcolor: "grey.200", p: 1, borderRadius: 1 }}>
                      <Typography variant="body2">#{tag}</Typography>
                      <Button size="small" onClick={() => handleRemoveTag(index)}>X</Button>
                    </Box>
                ))}
              </Box>
            </Grid>

            {/* 이미지 업로드 */}
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
              <Button
                  fullWidth
                  type="submit"
                  variant="contained"
                  color="primary"
                  disabled={!isFormValid}
                  onClick={handleSubmit}
              >
                상품 등록
              </Button>
            </Grid>
          </Grid>
        </form>
      </CardContent>
    </Card>
  );
}
