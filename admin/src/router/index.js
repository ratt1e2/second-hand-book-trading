import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      redirect: '/login'
    },
    {
      path: '/login',
      name: 'Login',
      component: () => import('../views/Login.vue'),
      meta: { title: '登录' }
    },
    {
      path: '/register',
      name: 'Register',
      component: () => import('../views/Register.vue'),
      meta: { title: '注册' }
    },
    {
      path: '/dashboard',
      name: 'Dashboard',
      component: () => import('../views/Dashboard.vue'),
      meta: { title: '数据概览', requiresAuth: true }
    },
    {
      path: '/user',
      name: 'User',
      component: () => import('../views/User.vue'),
      meta: { title: '用户管理', requiresAuth: true }
    },
    {
      path: '/book/audit',
      name: 'BookAudit',
      component: () => import('../views/BookAudit.vue'),
      meta: { title: '书籍审核', requiresAuth: true }
    },
    {
      path: '/order',
      name: 'Order',
      component: () => import('../views/Order.vue'),
      meta: { title: '订单管理', requiresAuth: true }
    },
    {
      path: '/category',
      name: 'Category',
      component: () => import('../views/Category.vue'),
      meta: { title: '分类管理', requiresAuth: true }
    }
  ]
})

// 路由守卫，设置页面标题和登录验证
router.beforeEach((to, from, next) => {
  document.title = to.meta.title ? `${to.meta.title} - 旧书交易管理系统` : '旧书交易管理系统'
  
  // 检查是否需要登录
  if (to.meta.requiresAuth) {
    const isLoggedIn = localStorage.getItem('isLoggedIn') === 'true'
    if (isLoggedIn) {
      next()
    } else {
      next('/login')
    }
  } else {
    next()
  }
})

export default router
