import { createRouter, createWebHistory } from 'vue-router'

const routes = [
    { path: '/', redirect: '/login' },
    { path: '/login', component: () => import('@/views/Login.vue') },
    {
        path: '/student',
        component: () => import('@/views/student/Layout.vue'),
        meta: { requiresAuth: true, role: 'student' }  //  添加角色
    },
    {
        path: '/admin',
        component: () => import('@/views/admin/Layout.vue'),
        meta: { requiresAuth: true, role: 'admin' }    //  添加角色
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

// 路由守卫 - 验证登录和权限
router.beforeEach((to, from, next) => {
    const token = localStorage.getItem('token')
    const userType = localStorage.getItem('userType')
    
    // 需要登录的页面
    if (to.meta.requiresAuth) {
        // 未登录
        if (!token) {
            next('/login')
            return
        }
        
        // 角色不匹配（学生不能进 admin，管理员不能进 student）
        if (to.meta.role && to.meta.role !== userType) {
            next('/login')
            return
        }
    }
    
    next()
})

export default router