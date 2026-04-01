<template>
    <el-card>
        <template #header>
            <span>基本信息</span>
        </template>
        <el-form :model="studentInfo" :rules="rules" ref="formRef" label-width="100px">
            <el-form-item label="学号">
                <el-input v-model="studentInfo.studentNum" disabled />
            </el-form-item>
            <el-form-item label="姓名" prop="name">
                <el-input v-model="studentInfo.name" placeholder="请填写姓名" />
            </el-form-item>
            <el-form-item label="性别" prop="gender">
                <el-select v-model="studentInfo.gender" placeholder="请选择性别">
                    <el-option label="男" :value="1" />
                    <el-option label="女" :value="2" />
                </el-select>
            </el-form-item>
            <el-form-item label="手机号" prop="phone">
                <el-input v-model="studentInfo.phone" placeholder="请填写手机号" maxlength="11" />
                <div style="color: #999; font-size: 12px">请输入11位手机号码</div>
            </el-form-item>
            <el-form-item label="宿舍" prop="dormitoryId">
                <el-select 
                    v-model="studentInfo.dormitoryId" 
                    placeholder="请选择宿舍" 
                    @focus="loadDormitories"
                    filterable
                >
                    <el-option 
                        v-for="dorm in dormitories" 
                        :key="dorm.id" 
                        :label="dorm.building + '栋' + dorm.room + '室'" 
                        :value="dorm.id" 
                    />
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="saveInfo" :loading="saving">保存</el-button>
                <el-button @click="loadStudentInfo">刷新</el-button>
            </el-form-item>
        </el-form>
    </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import request from '@/api/request'
import { getDormitoryList } from '@/api/dormitory'
import { getStudentInfo, updateStudentInfo } from '@/api/student'

const emit = defineEmits(['info-saved'])
const formRef = ref(null)
const saving = ref(false)

const studentInfo = ref({
    studentNum: localStorage.getItem('account') || '',
    name: '',
    gender: null,
    phone: '',
    dormitoryId: null
})

const dormitories = ref([])

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
    gender: [{ required: true, message: '请选择性别', trigger: 'change' }],
    phone: [
        { required: true, message: '请输入手机号', trigger: 'blur' },
        { validator: validatePhone, trigger: 'blur' }
    ],
    dormitoryId: [{ required: true, message: '请选择宿舍', trigger: 'change' }]
}

// 加载宿舍列表
const loadDormitories = async () => {
    try {
        const res = await getDormitoryList()
        dormitories.value = res.data
    } catch (error) {
        console.error('加载宿舍失败', error)
        // 模拟数据作为后备
        dormitories.value = [
            { id: 1, building: 'A', room: '101' },
            { id: 2, building: 'A', room: '102' },
            { id: 3, building: 'B', room: '201' },
            { id: 4, building: 'B', room: '202' },
            { id: 5, building: 'C', room: '301' },
            { id: 6, building: 'C', room: '302' }
        ]
    }
}

// 加载学生信息
const loadStudentInfo = async () => {
    try {
        const res = await getStudentInfo()
        if (res.data) {
            studentInfo.value = {
                studentNum: res.data.studentNum || localStorage.getItem('account') || '',
                name: res.data.name || '',
                gender: res.data.gender,
                phone: res.data.phone || '',
                dormitoryId: res.data.dormitoryId
            }
            // 如果已有信息，标记为已完善
            if (res.data.name && res.data.phone && res.data.dormitoryId) {
                localStorage.setItem('isInfoComplete', 'true')
                emit('info-saved')
            }
        }
    } catch (error) {
        console.error('加载学生信息失败', error)
    }
}

// 保存个人信息
const saveInfo = async () => {
    try {
        await formRef.value.validate()
        
        saving.value = true
        await updateStudentInfo({
            name: studentInfo.value.name,
            gender: studentInfo.value.gender,
            phone: studentInfo.value.phone,
            dormitoryId: studentInfo.value.dormitoryId
        })
        
        ElMessage.success('保存成功')
        localStorage.setItem('isInfoComplete', 'true')
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
    loadDormitories()
    loadStudentInfo()
})
</script>