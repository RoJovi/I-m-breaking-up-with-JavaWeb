<template>
    <el-card>
        <template #header>
            <div style="display: flex; justify-content: space-between; align-items: center">
                <span>报修单管理</span>
                <el-select v-model="statusFilter" placeholder="状态筛选" clearable @change="loadOrders">
                    <el-option label="待分配" :value="0" />
                    <el-option label="处理中" :value="1" />
                    <el-option label="已完成" :value="2" />
                    <el-option label="已取消" :value="3" />
                </el-select>
            </div>
        </template>
        
        <el-table :data="orders" stripe v-loading="loading">
            <el-table-column prop="orderNum" label="单号" width="180" />
            <el-table-column prop="studentName" label="学生姓名" width="100" />
            <el-table-column prop="studentPhone" label="学生电话" width="120" />
            <el-table-column prop="dormitoryLocation" label="宿舍位置" width="120" />
            <el-table-column prop="typeName" label="类型" width="80" />
            <el-table-column prop="description" label="描述" show-overflow-tooltip />
            <el-table-column prop="statusName" label="状态" width="100" />
            <el-table-column prop="adminName" label="处理人" width="100" />
            <el-table-column label="操作" width="240" fixed="right">
                <template #default="{ row }">
                    <el-button type="info" size="small" @click="viewDetail(row.id)">详情</el-button>
                    <el-button 
                        v-if="row.status === 0" 
                        type="primary" 
                        size="small"
                        :loading="processingId === row.id"
                        @click="updateStatus(row, 1)"
                    >开始处理</el-button>
                    <el-button 
                        v-if="row.status === 1" 
                        type="success" 
                        size="small"
                        @click="showCompleteDialog(row)"
                    >完成</el-button>
                    <el-button 
                        v-if="row.status === 0 || row.status === 1" 
                        type="danger" 
                        size="small"
                        :loading="deletingId === row.id"
                        @click="deleteOrder(row.id)"
                    >删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        
        <!-- 详情弹窗 -->
        <el-dialog v-model="detailDialogVisible" title="报修单详情" width="600px">
            <el-descriptions :column="2" border v-if="currentDetail">
                <el-descriptions-item label="单号">{{ currentDetail.orderNum }}</el-descriptions-item>
                <el-descriptions-item label="状态">
                    <el-tag :type="getStatusTagType(currentDetail.status)">{{ currentDetail.statusName }}</el-tag>
                </el-descriptions-item>
                <el-descriptions-item label="学生姓名">{{ currentDetail.studentName }}</el-descriptions-item>
                <el-descriptions-item label="学生电话">{{ currentDetail.studentPhone }}</el-descriptions-item>
                <el-descriptions-item label="宿舍位置">{{ currentDetail.dormitoryLocation }}</el-descriptions-item>
                <el-descriptions-item label="报修类型">{{ currentDetail.typeName }}</el-descriptions-item>
                <el-descriptions-item label="问题描述" :span="2">{{ currentDetail.description }}</el-descriptions-item>
                <el-descriptions-item label="图片" :span="2">
                    <img v-if="currentDetail.imageUrl" :src="currentDetail.imageUrl" style="max-width: 200px" />
                    <span v-else>无</span>
                </el-descriptions-item>
                <el-descriptions-item label="提交时间">{{ currentDetail.createTime }}</el-descriptions-item>
                <el-descriptions-item label="处理人">{{ currentDetail.adminName || '未分配' }}</el-descriptions-item>
                <el-descriptions-item v-if="currentDetail.result" label="处理结果" :span="2">{{ currentDetail.result }}</el-descriptions-item>
            </el-descriptions>
            <template #footer>
                <el-button @click="detailDialogVisible = false">关闭</el-button>
            </template>
        </el-dialog>
        
        <!-- 完成报修弹窗 -->
        <el-dialog v-model="completeDialogVisible" title="完成报修" width="500px">
            <el-form>
                <el-form-item label="处理结果">
                    <el-input 
                        v-model="completeResult" 
                        type="textarea" 
                        :rows="4" 
                        placeholder="请填写处理结果说明"
                    />
                </el-form-item>
            </el-form>
            <template #footer>
                <el-button @click="completeDialogVisible = false">取消</el-button>
                <el-button type="primary" @click="confirmComplete" :loading="completing">确认完成</el-button>
            </template>
        </el-dialog>
    </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getAllRepairs, getRepairDetail, updateRepairStatus, deleteRepair } from '@/api/admin'

const statusFilter = ref(null)
const orders = ref([])
const loading = ref(false)
const processingId = ref(null)
const deletingId = ref(null)
const completing = ref(false)
const detailDialogVisible = ref(false)
const currentDetail = ref(null)
const completeDialogVisible = ref(false)
const currentOrder = ref(null)
const completeResult = ref('')

const typeMap = { 1: '水电', 2: '家具', 3: '电器', 4: '网络', 5: '其他' }
const statusMap = { 0: '待分配', 1: '处理中', 2: '已完成', 3: '已取消' }

const getStatusTagType = (status) => {
    const map = { 0: 'warning', 1: 'primary', 2: 'success', 3: 'info' }
    return map[status]
}

// 加载报修单列表
const loadOrders = async () => {
    loading.value = true
    try {
        const params = statusFilter.value !== null ? { status: statusFilter.value } : {}
        const res = await getAllRepairs(params)
        orders.value = res.data.map(order => ({
            ...order,
            typeName: typeMap[order.type],
            statusName: statusMap[order.status]
        }))
    } catch (error) {
        ElMessage.error('加载失败')
    } finally {
        loading.value = false
    }
}

// 查看详情
const viewDetail = async (id) => {
    try {
        const res = await getRepairDetail(id)
        currentDetail.value = {
            ...res.data,
            typeName: typeMap[res.data.type],
            statusName: statusMap[res.data.status]
        }
        detailDialogVisible.value = true
    } catch (error) {
        ElMessage.error('加载详情失败')
    }
}

// 更新状态（开始处理）
const updateStatus = async (order, newStatus) => {
    const adminId = parseInt(localStorage.getItem('userId')) || 1
    const adminName = localStorage.getItem('userName') || localStorage.getItem('account') || '管理员'
    
    processingId.value = order.id
    try {
        await updateRepairStatus(order.id, {
            status: newStatus,
            adminId: adminId
        })
        
        order.status = newStatus
        order.statusName = statusMap[newStatus]
        order.adminId = adminId
        order.adminName = adminName
        
        ElMessage.success('已开始处理')
    } catch (error) {
        ElMessage.error('操作失败')
    } finally {
        processingId.value = null
    }
}

// 显示完成弹窗
const showCompleteDialog = (order) => {
    currentOrder.value = order
    completeResult.value = ''
    completeDialogVisible.value = true
}

// 确认完成
const confirmComplete = async () => {
    if (!completeResult.value) {
        ElMessage.warning('请填写处理结果说明')
        return
    }
    
    completing.value = true
    try {
        await updateRepairStatus(currentOrder.value.id, {
            status: 2,
            result: completeResult.value
        })
        
        currentOrder.value.status = 2
        currentOrder.value.statusName = statusMap[2]
        currentOrder.value.result = completeResult.value
        
        ElMessage.success('已完成报修')
        completeDialogVisible.value = false
    } catch (error) {
        ElMessage.error('操作失败')
    } finally {
        completing.value = false
    }
}

// 删除报修单
const deleteOrder = async (id) => {
    try {
        await ElMessageBox.confirm('确定删除此报修单吗？', '提示', { type: 'warning' })
        deletingId.value = id
        await deleteRepair(id)
        orders.value = orders.value.filter(order => order.id !== id)
        ElMessage.success('删除成功')
    } catch (error) {
        if (error !== 'cancel') {
            ElMessage.error('删除失败')
        }
    } finally {
        deletingId.value = null
    }
}

onMounted(() => {
    loadOrders()
})
</script>