import type { Breakpoint, SxProps, Theme } from '@mui/material/styles';
import { useTheme } from '@mui/material/styles';

import { useState } from 'react';

import Box from '@mui/material/Box';
import Alert from '@mui/material/Alert';

import { _login, _logout, _notifications, _signup } from 'src/_mock';

import { Iconify } from 'src/components/iconify';

import { Main } from './main';
import { layoutClasses } from '../classes';
import { NavDesktop, NavMobile } from './nav';
import { navData } from '../config-nav-dashboard';
import { Searchbar } from '../components/searchbar';
import { _workspaces } from '../config-nav-workspace';
import { MenuButton } from '../components/menu-button';
import { LayoutSection } from '../core/layout-section';
import { HeaderSection } from '../core/header-section';
import { AccountPopover } from '../components/account-popover';
import { NotificationsPopover } from '../components/notifications-popover';
import { LoginButton } from '../components/login-button';
import { useUserStore } from '../../middleware/store/user-store';
import { LogoutButton } from '../components/logout-button';
import { SingupButton } from '../components/signup-button';
import Modal from '../../components/modal/modal';
import { useUserAuth } from '../../hooks/use-user-auth';

// ----------------------------------------------------------------------

export type DashboardLayoutProps = {
  sx?: SxProps<Theme>;
  children: React.ReactNode;
  header?: {
    sx?: SxProps<Theme>;
  };
};

export function DashboardLayout({ sx, children, header }: DashboardLayoutProps) {

  useUserAuth();

  const theme = useTheme();

  const [navOpen, setNavOpen] = useState(false);

  const {user} = useUserStore()

  const layoutQuery: Breakpoint = 'lg';

  return (
    <LayoutSection
      /** **************************************
       * Header
       *************************************** */
      headerSection={
        <HeaderSection
          layoutQuery={layoutQuery}
          slotProps={{
            container: {
              maxWidth: false,
              sx: { px: { [layoutQuery]: 5 } },
            },
          }}
          sx={header?.sx}
          slots={{
            topArea: (
              <Alert severity="info" sx={{ display: 'none', borderRadius: 0 }}>
                This is an info Alert.
              </Alert>
            ),
            leftArea: (
              <>
                <MenuButton
                  onClick={() => setNavOpen(true)}
                  sx={{
                    ml: -1,
                    [theme.breakpoints.up(layoutQuery)]: { display: 'none' },
                  }}
                />
                {/*  참고!! 이건 모바일용임ㅋㅋㅋㅋㅋㅋㅋㅎㄷㄷ, 데스크탑 용도의 Nav는 아래 `NavDesktop` 찾아.  */}
                <NavMobile
                  data={navData}
                  open={navOpen}
                  onClose={() => setNavOpen(false)}
                  workspaces={_workspaces}
                />
              </>
            ),
            rightArea: (
              <Box gap={1} display="flex" alignItems="center">
                {/*  돋보기 부분.(검색)  */}
                <Searchbar />

                {user ? (
                  <>
                    {/* 알림 팝오버 */}
                    <NotificationsPopover data={_notifications} />
                    {/* 사용자 기능 모음 팝오버 */}
                    <AccountPopover
                      data={[
                        { label: 'Home', href: '/', icon: <Iconify width={22} icon="solar:home-angle-bold-duotone" /> },
                        { label: 'Profile', href: '#', icon: <Iconify width={22} icon="solar:shield-keyhole-bold-duotone" /> },
                        { label: 'Settings', href: '#', icon: <Iconify width={22} icon="solar:settings-bold-duotone" /> },
                        { label: '판매자신청', href: '/seller-req', icon: <Iconify width={22} icon="solar:settings-bold-duotone" /> },
                        { label: '상품등록', href: '/product-reg', icon: <Iconify width={22} icon="solar:document-bold-duotone" /> },
                        { label: '장바구니', href: '/cart', icon: <Iconify width={22} icon="solar:cart-bold-duotone" /> },
                        // <Iconify width={22} icon="solar:user-bold-duotone" />  // 사용자 아이콘
                        // <Iconify width={22} icon="solar:bell-bold-duotone" />  // 알림 아이콘
                        // <Iconify width={22} icon="solar:chat-round-bold-duotone" />  // 채팅 아이콘
                        // <Iconify width={22} icon="solar:search-bold-duotone" />  // 검색 아이콘
                        // <Iconify width={22} icon="solar:document-bold-duotone" />  // 문서 아이콘
                        // <Iconify width={22} icon="solar:calendar-bold-duotone" />  // 캘린더 아이콘
                        // <Iconify width={22} icon="solar:cartEntity-bold-duotone" />  // 장바구니 아이콘
                        // <Iconify width={22} icon="solar:logout-bold-duotone" />  // 로그아웃 아이콘
                      ]}
                    />
                    <LogoutButton data={_logout}/>
                  </>
                ) : (
                  <>
                    {/* Login 팝오버 */}
                    <LoginButton data={_login} />
                    <SingupButton data={_signup} />
                  </>
                )}
                {/*  Language 설정 아이콘인데, 인단 주석  */}
                {/* <LanguagePopover data={_langs} /> */}
              </Box>
            ),
          }}
        />
      }
      /** **************************************
       * Sidebar 여기가 데스크탑 용도의 사이드 Nav 임.
       *************************************** */
      sidebarSection={
        <NavDesktop data={navData} layoutQuery={layoutQuery} workspaces={_workspaces} />
      }
      /** **************************************
       * Footer
       *************************************** */
      footerSection={null}
      /** **************************************
       * Style
       *************************************** */
      cssVars={{
        '--layout-nav-vertical-width': '300px',
        '--layout-dashboard-content-pt': theme.spacing(1),
        '--layout-dashboard-content-pb': theme.spacing(8),
        '--layout-dashboard-content-px': theme.spacing(5),
      }}
      sx={{
        [`& .${layoutClasses.hasSidebar}`]: {
          [theme.breakpoints.up(layoutQuery)]: {
            pl: 'var(--layout-nav-vertical-width)',
          },
        },
        ...sx,
      }}
    >
      <Modal/>
      <Main>{children}</Main>
    </LayoutSection>
  );
}
