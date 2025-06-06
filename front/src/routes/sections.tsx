import { lazy, Suspense } from 'react';
import { Outlet, Navigate, useRoutes } from 'react-router-dom';

import Box from '@mui/material/Box';
import LinearProgress, { linearProgressClasses } from '@mui/material/LinearProgress';

import { varAlpha } from 'src/theme/styles';
import { AuthLayout } from 'src/layouts/auth';
import { DashboardLayout } from 'src/layouts/dashboard';

// ----------------------------------------------------------------------

export const HomePage = lazy(() => import('src/pages/home'));
export const BlogPage = lazy(() => import('src/pages/blog'));
export const UserPage = lazy(() => import('src/pages/user'));
export const SignInPage = lazy(() => import('src/pages/sign-in'));
export const SignUpPage = lazy(() => import('src/pages/sign-up'));
export const SellerReqPage = lazy(() => import('src/pages/seller-request'));

export const ProductsPage = lazy(() => import('src/pages/products'));
export const ProductRegisterPage = lazy(() => import('src/pages/product-register'));
export const ProductRegisterTestPage = lazy(() => import('src/pages/product-register-test'));
export const ProductDetailPage = lazy(() => import('src/pages/product-detail'));

export const Page404 = lazy(() => import('src/pages/page-not-found'));

// ----------------------------------------------------------------------

const renderFallback = (
  <Box display="flex" alignItems="center" justifyContent="center" flex="1 1 auto">
    <LinearProgress
      sx={{
        width: 1,
        maxWidth: 320,
        bgcolor: (theme) => varAlpha(theme.vars.palette.text.primaryChannel, 0.16),
        [`& .${linearProgressClasses.bar}`]: { bgcolor: 'text.primary' },
      }}
    />
  </Box>
);

export function Router() {
  return useRoutes([
    {
      element: (
        /*  `DashboardLayout` 이 좌측 네비게이션, 그리고 상단 헤더를 가지고 렌더링함.  */
        <DashboardLayout>
          <Suspense fallback={renderFallback}>
            {/*  react-router 라이브러리인데,,? 무슨 용도인지 추후에 파악해보자.ㅋ  */}
            <Outlet />
          </Suspense>
        </DashboardLayout>
      ),
      /*  어쨋든 라우터에의해서 경로마다 아래 컴포넌트로 라우팅한다는 점.  */
      children: [
        { element: <ProductsPage />, index: true },
        { path: 'user', element: <UserPage /> },
        { path: 'products', element: <ProductsPage /> },
        { path: 'products/:productId', element: <ProductDetailPage /> },
        { path: 'blog', element: <BlogPage /> },
        // { path: 'sign-in', element: <SignInPage /> },
        // { path: 'sign-up', element: <SignUpPage /> },
        { path: 'seller-req', element: <SellerReqPage /> },
        { path: 'product-reg', element: <ProductRegisterPage/> },
        { path: 'product-reg2', element: <ProductRegisterTestPage/>}
      ],
    },
    {
      element: (
        <AuthLayout>
          <Suspense fallback={renderFallback}>
            <Outlet />
          </Suspense>
        </AuthLayout>
      ),
      children: [
        { path: 'sign-in', element: <SignInPage /> },
        { path: 'sign-up', element: <SignUpPage /> },
      ]
    },
    // {
    //   path: 'sign-in',
    //   element: (
    //     <AuthLayout>
    //       <SignInPage />
    //     </AuthLayout>
    //   ),
    // },
    // {
    //   path: 'sign-up',
    //   element: (
    //     <AuthLayout>
    //       <SignUpPage/>
    //     </AuthLayout>
    //   )
    // },
    // {
    //   path: 'seller-req',
    //   element: (
    //     <AuthLayout>
    //       <SellerReqPage/>
    //     </AuthLayout>
    //   )
    // },
    // {
    //   path: 'product-reg',
    //   element: (
    //     <AuthLayout>
    //       <ProductRegisterPage/>
    //     </AuthLayout>
    //   )
    // },
    {
      path: '404',
      element: <Page404 />,
    },
    {
      path: '*',
      element: <Navigate to="/404" replace />,
    },
  ]);
}
