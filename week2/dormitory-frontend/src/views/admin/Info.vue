<template>
    <el-card>
        <template #header>
            <span>基本信息</span>
        </template>
        <el-form :model="adminInfo" :rules="rules" ref="formRef" label-width="100px">
            <el-form-item label="工号">
                <el-input v-model="adminInfo.adminNum" disabled />
            </el-form-item>
            <el-form-item label="姓名" prop="name">
                <el-input v-model="adminInfo.name" placeholder="请填写姓名" />
            </el-form-item>
            <el-form-item label="手机号" prop="phone">
                <el-input v-model="adminInfo.phone" placeholder="请填写手机号" maxlength="11" />
                <div style="color: #999; font-size: 12px">请输入11位手机号码</div>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="saveInfo" :loading="saving">保存</el-button>
                <el-button @click="loadAdminInfo">刷新</el-button>
            </el-form-item>
        </el-form>
    </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getAdminInfo, updateAdminInfo } from '@/api/admin'

const emit = defineEmits(['info-saved'])
const formRef = ref(null)
const saving = ref(false)

const adminInfo = ref({
    adminNum: localStorage.getItem('account') || '',
    name: '',
    phone: ''
})

// 手机号校验规则
const validatePhone = (rule, value, callback) => {
    const phoneRegex = /^1[3-9]\d{9}$/
    if (!value) {
        callback(new Error('请输入手机号'))
    } else if (!phoneRegex.test(value)) {
        callback(new Error('请输入正确的11位手机号码'))
    } else {
        callback()
    }
}

const rules = {
    name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
    phone: [
        { required: true, message: '请输入手机号', trigger: 'blur' },
        { validator: validatePhone, trigger: 'blur' }
    ]
}

// 加载管理员信息
const loadAdminInfo = async () => {
    try {
        const res = await getAdminInfo()
        if (res.data) {
            adminInfo.value = {
                adminNum: res.data.adminNum || localStorage.getItem('account') || '',
                name: res.data.name || '',
                phone: res.data.phone || ''
            }
            // 如果已有信息，标记为已完善
            if (res.data.name && res.data.phone) {
                localStorage.setItem('adminInfoComplete', 'true')
                emit('info-saved')
            }
        }
    } catch (error) {
        console.error('加载管理员信息失败', error)
    }
}

// 保存个人信息
const saveInfo = async () => {
    try {
        await formRef.value.validate()
        
        saving.value = true
        await updateAdminInfo({
            name: adminInfo.value.name,
            phone: adminInfo.value.phone
        })
        
        ElMessage.success('保存成功')
        localStorage.setItem('adminInfoComplete', 'true')
        emit('info-saved')
    } catch (error) {
        if (error !== 'validation failed') {
            ElMessage.error('保存失败')
        }
    } finally {
        saving.value = false
    }
}

onMounted(() => {
    loadAdminInfo()
})
</script>