import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useAuthStore = defineStore('auth', () => {
    const token = ref(localStorage.getItem('token') || '')
    const userType = ref(localStorage.getItem('userType') || '')
    const userInfo = ref(null)

    const setAuth = (newToken, type, info) => {
        token.value = newToken
        userType.value = type
        userInfo.value = info
        localStorage.setItem('token', newToken)
        localStorage.setItem('userType', type)
    }

    const logout = () => {
        token.value = ''
        userType.value = ''
        userInfo.value = null
        localStorage.removeItem('token')
        localStorage.removeItem('userType')
    }

    return { token, userType, userInfo, setAuth, logout }
})