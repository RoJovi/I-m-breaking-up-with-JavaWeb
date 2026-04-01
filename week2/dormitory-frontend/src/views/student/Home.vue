<template>
    <div class="home">
        <el-card>
            <template #header>
                <span>提交报修单</span>
            </template>
            <el-form :model="form" label-width="100px">
                <el-form-item label="宿舍ID">
                    <el-input v-model="form.dormitoryId" placeholder="请输入宿舍ID" />
                </el-form-item>
                <el-form-item label="报修类型">
                    <el-select v-model="form.type">
                        <el-option label="水电" :value="1" />
                        <el-option label="家具" :value="2" />
                        <el-option label="电器" :value="3" />
                        <el-option label="网络" :value="4" />
                        <el-option label="其他" :value="5" />
                    </el-select>
                </el-form-item>
                <el-form-item label="问题描述">
                    <el-input v-model="form.description" type="textarea" :rows="4" />
                </el-form-item>
                <el-form-item label="图片URL">
                    <el-input v-model="form.imageUrl" placeholder="可选" />
                </el-form-item>
                <el-button type="primary" @click="handleSubmit">提交报修</el-button>
            </el-form>
        </el-card>
    </div>
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import { submitRepair } from '@/api/student'
import { useRouter } from 'vue-router'

const router = useRouter()
const form = ref({
    dormitoryId: '',
    type: 1,
    description: '',
    imageUrl: ''
})

const handleSubmit = async () => {
    if (!form.value.dormitoryId || !form.value.description) {
        ElMessage.warning('请填写完整信息')
        return
    }
    try {
        await submitRepair(form.value)
        ElMessage.success('提交成功')
        router.push('/student/orders')
    } catch (error) {}
}
</script>