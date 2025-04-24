import {useRef, useState} from "react";
import {useNavigate} from "react-router-dom";

import axios from "axios";

import {TextField, Button, Card, CardContent, Typography, Grid, debounce, Chip, CardMedia} from "@mui/material";

import Box from "@mui/material/Box";
import {Editor} from "@toast-ui/react-editor";

import '@toast-ui/editor/dist/toastui-editor.css';
import {useModalStore} from "../../../middleware/store/modal-store";
import {useProductRegisterMutation, useProductRegisterV2Mutation} from "../../../middleware/query/product-query";
import {API, IMG_URL} from "../../../middleware/api/config";
import {Response} from "../../../types/common-type";
import {ProductImageUrl} from "../../../types/product-type";

export default function ProductRegisterViewTest() {

  const navigate = useNavigate()

  const editorRef = useRef<Editor>(null);

  const handleSave = () => {
    const markdown = editorRef.current?.getInstance().getMarkdown();
    console.log('📝 작성된 마크다운:', markdown);
  };

  const { setModal, clearModal } = useModalStore()

  const {mutateAsync: productMutateAsync} = useProductRegisterV2Mutation()

  const [name, setName] = useState<string>('')
  const [brand, setBrand] = useState<string>('')
  const [price, setPrice] = useState<string>('')
  const [stock, setStock] = useState<string>('')

  const [hashtags, setHashtags] = useState<string[]>([]);
  const [tagInput, setTagInput] = useState<string>("");

  const [images, setImages] = useState<FileList | null>(null)
  const [usedImageUrls, setUsedImageUrls] = useState<ProductImageUrl[]>([]);

  const [errors, setErrors] = useState({
    name: "",
    brand: "",
    price: "",
    stock: "",
  })

  // 모든 필드가 유효한 경우 버튼 활성화
  const isFormValid = Object.values(errors).every((err) => err === '') && !!name && !!brand && !!price && !!stock;

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
        newErrors.price = price.length < 1 ? '가격을 입력해주세요.' : '';
      }

      if (field === 'stock') {
        newErrors.stock = stock.length < 1 ? '수량을 입력해주세요.' : '';
      }

      // if (field === 'description') {
      //   newErrors.description = description.length < 1 ? '설명을 입력해주세요.' : '';
      // }

      return newErrors;
    });
  }, 500)

  /* 이름, 상표, 가격, 재고, 설명 을 입력했을 때 핸들링 함수 */
  const handleChange = (field: string, value: string) => {
    if (field === 'name') setName(value);
    if (field === 'brand') setBrand(value);
    if (field === 'price') setPrice(value);
    if (field === 'stock') setStock(value);

    validateInput(field, value);
  };

  /* 이미지 상태 처리 핸들링 함수 */
  const handleImageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target?.files?.length) {
      setImages(e.target.files);
      setErrors(prev => ({ ...prev, images: "" }))
    } else {
      setImages(null)
      setErrors(prev => ({...prev, images: "에러!"}))
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
  const handleSubmit = async (e: React.FormEvent) => {
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

    // if (images) {
    //   productMutateAsync({ product: { name, brand, price, stock, description, hashtags }, images: Array.from(images) })
    //       .then(r => console.log("success! r", r));
    // }
    const content = editorRef.current?.getInstance()?.getMarkdown();
    if (!content) {
      setModal({
        isOpen: true,
        header: '알림',
        content: '내용을 입력해주세요.',
        callback: async () => clearModal(),
      })
      return;
    }

    const imageIds = usedImageUrls.map(it => it.id)
    productMutateAsync({ name, brand, price, stock, description: content, imageIds, hashtags })
        .then(r => {
          console.log("product save result:", r);
          navigate('/')
        });
  };

  return (
      <>
        <Card sx={{ margin: "auto", mt: 5, p: 3 }}>
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
              </Grid>
            </form>
          </CardContent>
          <CardMedia>
            <Editor
                ref={editorRef}
                previewStyle="vertical" // vertical, tab 중 선택
                height="640px"
                initialEditType="wysiwyg" // `markdown` 'wysiwyg' 가능
                // useCommandShortcut={true}
                hooks={{
                  addImageBlobHook: async (blob, callback) => {
                    try {
                      const formData = new FormData();
                      formData.append("file", blob);
                      const res = await axios.post<Response<ProductImageUrl>>(API.IMAGES, formData, {
                        headers: { "Content-Type": "multipart/form-data" },
                      });
                      if (res?.data?.data) {
                        const productImageUrl = res.data.data
                        setUsedImageUrls(prev => [...prev, productImageUrl])
                        callback(IMG_URL + productImageUrl.url, `@${productImageUrl.id}@`);
                      }
                    } catch (error) {
                      console.error("이미지 업로드 실패:", error);
                    }
                  }
                }}
            />
          </CardMedia>
          <Grid item xs={12}>
            <Button
                fullWidth
                type="submit"
                variant="contained"
                color="primary"
                // disabled={!isFormValid}
                onClick={handleSubmit}
            >
              상품 등록
            </Button>
          </Grid>
        </Card>
      </>
  );
}
