<template>
    <el-card>
        <template #header>
            <span>提交报修</span>
        </template>
        <el-form :model="repairForm" :rules="rules" ref="formRef" label-width="100px">
            <el-form-item label="报修类型" prop="type">
                <el-select v-model="repairForm.type">
                    <el-option label="水电" :value="1" />
                    <el-option label="家具" :value="2" />
                    <el-option label="电器" :value="3" />
                    <el-option label="网络" :value="4" />
                    <el-option label="其他" :value="5" />
                </el-select>
            </el-form-item>
            <el-form-item label="问题描述" prop="description">
                <el-input v-model="repairForm.description" type="textarea" :rows="4" placeholder="请详细描述问题" />
            </el-form-item>
            <el-form-item label="图片">
                <el-upload
                    :action="uploadUrl"
                    :headers="uploadHeaders"
                    :on-success="handleUploadSuccess"
                    :on-error="handleUploadError"
                    :before-upload="beforeUpload"
                    :limit="1"
                    accept="image/jpeg,image/png,image/jpg"
                >
                    <el-button type="primary">选择图片</el-button>
                    <template #tip>
                        <div style="color: #999; font-size: 12px">支持jpg、png格式，大小不超过2MB</div>
                    </template>
                </el-upload>
                <div v-if="repairForm.imageUrl" style="margin-top: 10px">
                    <img :src="repairForm.imageUrl" style="max-width: 200px; max-height: 200px" />
                </div>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="submitRepair" :loading="submitting">提交</el-button>
            </el-form-item>
        </el-form>
    </el-card>
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import { submitRepair as apiSubmitRepair } from '@/api/student'

const router = useRouter()
const formRef = ref(null)
const submitting = ref(false)

const emit = defineEmits(['submit-success'])

const uploadUrl = 'http://localhost:8080/api/upload'
const uploadHeaders = {
    token: localStorage.getItem('token') || ''
}

const repairForm = ref({
    type: 1,
    description: '',
    imageUrl: ''
})

const rules = {
    type: [{ required: true, message: '请选择报修类型', trigger: 'change' }],
    description: [{ required: true, message: '请填写问题描述', trigger: 'blur' }]
}

const beforeUpload = (file) => {
    const isImage = file.type === 'image/jpeg' || file.type === 'image/png' || file.type === 'image/jpg'
    const isLt2M = file.size / 1024 / 1024 < 2
    if (!isImage) {
        ElMessage.error('只能上传jpg、png格式的图片')
        return false
    }
    if (!isLt2M) {
        ElMessage.error('图片大小不能超过2MB')
        return false
    }
    return true
}

const handleUploadSuccess = (response) => {
    console.log('上传响应:', response)
    if (response.code === 1) {
        // response.data 就是 URL 字符串
        repairForm.value.imageUrl = response.data
        ElMessage.success('图片上传成功')
    } else {
        ElMessage.error(response.msg || '上传失败')
    }
}

const handleUploadError = () => {
    ElMessage.error('图片上传失败')
}

// 提交报修
const submitRepair = async () => {
    try {
        await formRef.value.validate()
    } catch (error) {
        ElMessage.warning('请填写完整信息')
        return
    }
    
    const isInfoComplete = localStorage.getItem('isInfoComplete') === 'true'
    if (!isInfoComplete) {
        ElMessage.warning('请先完善个人信息')
        return
    }
    
    submitting.value = true
    try {
        const token = localStorage.getItem('token')
        if (!token) {
            ElMessage.error('请先登录')
            return
        }
        
        await apiSubmitRepair({
            type: repairForm.value.type,
            description: repairForm.value.description,
            imageUrl: repairForm.value.imageUrl || null
        })
        
        ElMessage.success('报修提交成功')
        
        // 清空表单
        repairForm.value = { type: 1, description: '', imageUrl: '' }

	emit('submit-success')
        
        // 跳转到基本信息页，而不是报修列表
        // 通过 emit 通知父组件切换 tab
        emit('submit-success')
        
    } catch (error) {
        console.error('提交失败:', error)
    } finally {
        submitting.value = false
    }
}
</script>