<template>
    <el-card>
        <template #header>
            <span>报修单详情</span>
        </template>
        <el-descriptions :column="2" border>
            <el-descriptions-item label="单号">BX001</el-descriptions-item>
            <el-descriptions-item label="状态">待分配</el-descriptions-item>
            <el-descriptions-item label="学生姓名">张三</el-descriptions-item>
            <el-descriptions-item label="学生电话">13800000000</el-descriptions-item>
            <el-descriptions-item label="宿舍位置">A栋101室</el-descriptions-item>
            <el-descriptions-item label="报修类型">水电</el-descriptions-item>
            <el-descriptions-item label="问题描述" :span="2">灯坏了，不亮</el-descriptions-item>
            <el-descriptions-item label="提交时间" :span="2">2024-01-01 10:00:00</el-descriptions-item>
        </el-descriptions>
        
        <div style="margin-top: 20px">
            <el-divider>处理操作</el-divider>
            <el-select v-model="newStatus" placeholder="修改状态">
                <el-option label="处理中" :value="1" />
                <el-option label="已完成" :value="2" />
            </el-select>
            <el-input v-model="result" type="textarea" placeholder="处理结果说明" style="margin-top: 10px" />
            <el-button type="primary" @click="handleUpdate" style="margin-top: 10px">提交</el-button>
        </div>
        <el-button @click="goBack" style="margin-top: 10px">返回</el-button>
    </el-card>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

const router = useRouter()
const newStatus = ref(null)
const result = ref('')

const handleUpdate = () => {
    ElMessage.success('更新成功')
    router.push('/admin')
}

const goBack = () => {
    router.push('/admin')
}
</script>