import axios from 'axios'
import { ElMessage } from 'element-plus'

const request = axios.create({
    baseURL: 'http://localhost:8080',  // 你的后端地址
    timeout: 10000
})

// 请求拦截器：自动添加token
request.interceptors.request.use(
    config => {
        const token = localStorage.getItem('token')
        if (token) {
            config.headers['token'] = token
        }
        return config
    },
    error => Promise.reject(error)
)

// 响应拦截器：适配后端格式（code:1成功，code:0失败，字段msg）
request.interceptors.response.use(
    response => {
        const res = response.data
        
        // 后端返回格式：{ code: 1, msg: "success", data: {...} }
        // 或错误时：{ code: 0, msg: "error", data: null }
        
        if (res.code === 1) {
            // 成功：统一返回格式
            return res
        } else {
            // 业务错误（code === 0）
            // 统一错误提示：显示后端返回的 msg，如果没有则显示默认提示
            const errorMsg = res.msg || '操作失败，请重试'
            ElMessage.error(errorMsg)
            return Promise.reject(new Error(errorMsg))
        }
    },
    error => {
        // 网络错误或服务器错误
        if (error.response) {
            const { status, data } = error.response
            if (status === 401) {
                ElMessage.error('登录已过期，请重新登录')
                localStorage.removeItem('token')
                localStorage.removeItem('userType')
                localStorage.removeItem('account')
                localStorage.removeItem('userId')
                localStorage.removeItem('userName')
                window.location.href = '/login'
            } else if (status === 404) {
                ElMessage.error('请求接口不存在，请检查地址')
            } else if (status === 500) {
                ElMessage.error('服务器错误，请稍后重试')
            } else {
                // 优先使用后端返回的错误信息
                const errorMsg = data?.msg || data?.message || '请求失败，请重试'
                ElMessage.error(errorMsg)
            }
        } else if (error.request) {
            ElMessage.error('网络连接失败，请检查后端是否启动')
        } else {
            ElMessage.error(error.message || '请求失败')
        }
        return Promise.reject(error)
    }
)

export default request