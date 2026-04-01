<template>
    <div class="login-container">
        <el-card class="login-card">
            <h2 style="text-align: center; margin-bottom: 20px">宿舍报修管理系统</h2>
            
            <!-- 登录区域 -->
            <el-form :model="loginForm" :rules="loginRules" ref="loginFormRef">
                <el-form-item label="身份">
                    <el-radio-group v-model="loginForm.userType">
                        <el-radio label="student">学生登录</el-radio>
                        <el-radio label="admin">管理员登录</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="账号" prop="account">
                    <el-input v-model="loginForm.account" placeholder="学号/工号" />
                    <!-- 移除默认提示，只在输入时显示 -->
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input v-model="loginForm.password" type="password" placeholder="请输入密码" />
                </el-form-item>
                <el-button type="primary" @click="handleLogin" style="width: 100%">登录</el-button>
            </el-form>
            
            <el-divider>
                <span style="color: #999; font-size: 14px">还没有账号？</span>
            </el-divider>
            
            <el-button @click="showRegister = true" style="width: 100%">注册新账号</el-button>
        </el-card>
        
        <!-- 注册弹窗 -->
        <el-dialog v-model="showRegister" title="注册" width="400px">
            <el-form :model="registerForm" :rules="registerRules" ref="registerFormRef">
                <el-form-item label="身份">
                    <el-radio-group v-model="registerForm.userType">
                        <el-radio label="student">学生</el-radio>
                        <el-radio label="admin">管理员</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item :label="registerForm.userType === 'student' ? '学号' : '工号'" prop="account">
                    <el-input v-model="registerForm.account" :placeholder="registerForm.userType === 'student' ? '请输入10位学号' : '请输入10位工号'" />
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input v-model="registerForm.password" type="password" placeholder="请输入密码" />
                </el-form-item>
                <el-form-item label="确认密码" prop="confirmPassword">
                    <el-input v-model="registerForm.confirmPassword" type="password" placeholder="请再次输入密码" />
                </el-form-item>
            </el-form>
            <template #footer>
                <el-button @click="showRegister = false">取消</el-button>
                <el-button type="primary" @click="handleRegister">注册</el-button>
            </template>
        </el-dialog>
    </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { studentLogin, studentRegister } from '@/api/student'
import { adminLogin , adminRegister} from '@/api/admin'

const router = useRouter()
const loginFormRef = ref(null)
const registerFormRef = ref(null)
const showRegister = ref(false)

const loginForm = ref({
    userType: 'student',
    account: '',
    password: ''
})

const registerForm = ref({
    userType: 'student',
    account: '',
    password: '',
    confirmPassword: ''
})

// 登录校验规则 - 移除默认的红色叉叉提示，改用更友好的提示
const loginRules = {
    account: [
        { required: true, message: '请输入账号', trigger: 'blur' },
        {
            validator: (rule, value, callback) => {
                if (!value) {
                    callback(new Error('请输入账号'))
                    return
                }
                
                if (loginForm.value.userType === 'student') {
                    const studentRegex = /^(3225|3125)\d{6}$/
                    if (!studentRegex.test(value)) {
                        callback(new Error('学号格式错误，应为3225xxxxxx或3125xxxxxx（10位）'))
                    } else {
                        callback()
                    }
                } else {
                    const adminRegex = /^0025\d{6}$/
                    if (!adminRegex.test(value)) {
                        callback(new Error('工号格式错误，应为0025xxxxxx（10位）'))
                    } else {
                        callback()
                    }
                }
            },
            trigger: 'blur'
        }
    ],
    password: [
        { required: true, message: '请输入密码', trigger: 'blur' }
    ]
}

// 注册校验规则
const registerRules = {
    account: [
        { required: true, message: '请输入账号', trigger: 'blur' },
        {
            validator: (rule, value, callback) => {
                if (!value) {
                    callback(new Error('请输入账号'))
                    return
                }
                
                if (registerForm.value.userType === 'student') {
                    const studentRegex = /^(3225|3125)\d{6}$/
                    if (!studentRegex.test(value)) {
                        callback(new Error('学号格式错误，应为3225xxxxxx或3125xxxxxx（10位）'))
                    } else {
                        callback()
                    }
                } else {
                    const adminRegex = /^0025\d{6}$/
                    if (!adminRegex.test(value)) {
                        callback(new Error('工号格式错误，应为0025xxxxxx（10位）'))
                    } else {
                        callback()
                    }
                }
            },
            trigger: 'blur'
        }
    ],
    password: [
        { required: true, message: '请输入密码', trigger: 'blur' },
        { min: 6, max: 20, message: '密码长度6-20位', trigger: 'blur' }
    ],
    confirmPassword: [
        { required: true, message: '请确认密码', trigger: 'blur' },
        {
            validator: (rule, value, callback) => {
                if (value !== registerForm.value.password) {
                    callback(new Error('两次输入的密码不一致'))
                } else {
                    callback()
                }
            },
            trigger: 'blur'
        }
    ]
}

// 学生登录 - 统一错误提示
const handleStudentLogin = async (account, password) => {
    try {
        const res = await studentLogin({ studentNum: account, password })
        const { token, id, studentNum, name } = res.data
        
        localStorage.setItem('token', token)
        localStorage.setItem('userId', id)
        localStorage.setItem('account', studentNum)
        localStorage.setItem('userName', name || '')
        localStorage.setItem('userType', 'student')
        
        ElMessage.success('登录成功')
        router.push('/student')
    } catch (error) {
        // 错误已在拦截器处理，但拦截器会显示具体错误信息
        // 如果拦截器没有显示，这里做兜底
        if (error.message && error.message !== 'validation failed') {
            ElMessage.error('账号或密码错误')
        }
    }
}

// 管理员登录 - 统一错误提示
const handleAdminLogin = async (account, password) => {
    try {
        const res = await adminLogin({ adminNum: account, password })
        const { token, id, adminNum, name } = res.data
        
        localStorage.setItem('token', token)
        localStorage.setItem('userId', id)
        localStorage.setItem('account', adminNum)
        localStorage.setItem('userName', name || '')
        localStorage.setItem('userType', 'admin')
        
        ElMessage.success('登录成功')
        router.push('/admin')
    } catch (error) {
        // 错误已在拦截器处理，但拦截器会显示具体错误信息
        // 如果拦截器没有显示，这里做兜底
        if (error.message && error.message !== 'validation failed') {
            ElMessage.error('账号或密码错误')
        }
    }
}

// 统一登录处理
const handleLogin = async () => {
    try {
        await loginFormRef.value.validate()
        
        const { userType, account, password } = loginForm.value
        
        if (userType === 'student') {
            await handleStudentLogin(account, password)
        } else {
            await handleAdminLogin(account, password)
        }
    } catch (error) {
        // 验证失败时，ElForm 会自动显示错误提示，不需要额外处理
        if (error !== 'validation failed') {
            ElMessage.error('登录失败，请检查网络连接')
        }
    }
}

const handleRegister = async () => {
    try {
        await registerFormRef.value.validate()
        
        const { userType, account, password } = registerForm.value
        
        if (userType === 'student') {
            await studentRegister({ studentNum: account, password })
            ElMessage.success('注册成功，请登录')
        } else {
            // 👇 管理员注册
            try {
                await adminRegister({ adminNum: account, password })
                ElMessage.success('注册成功，请登录')
            } catch (error) {
                ElMessage.error('注册失败，管理员账号可能已存在')
                return
            }
        }
        
        showRegister.value = false
        registerForm.value = {
            userType: 'student',
            account: '',
            password: '',
            confirmPassword: ''
        }
    } catch (error) {
        if (error !== 'validation failed') {
            ElMessage.error('注册失败，请稍后重试')
        }
    }
}
</script>

<style scoped>
.login-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}
.login-card {
    width: 400px;
    border-radius: 8px;
}
</style>