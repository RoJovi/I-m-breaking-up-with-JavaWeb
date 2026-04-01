<template>
    <el-card>
        <template #header>
            <span>我的报修单</span>
        </template>
        <el-table :data="orders" stripe v-loading="loading">
            <el-table-column prop="orderNum" label="单号" width="180" />
            <el-table-column prop="typeName" label="类型" width="80" />
            <el-table-column prop="description" label="描述" show-overflow-tooltip />
            <el-table-column prop="statusName" label="状态" width="100" />
            <el-table-column prop="createTime" label="提交时间" width="180" />
            <el-table-column label="操作" width="100">
                <template #default="{ row }">
                    <el-button 
                        v-if="row.status === 0" 
                        type="danger" 
                        size="small"
                        @click="cancelOrder(row.id)"
                        :loading="cancellingId === row.id"
                    >取消</el-button>
                </template>
            </el-table-column>
        </el-table>
        <div v-if="!loading && orders.length === 0" style="text-align: center; padding: 40px; color: #999">
            暂无报修记录
        </div>
    </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getMyRepairs, cancelRepair } from '@/api/student'

const orders = ref([])
const loading = ref(false)
const cancellingId = ref(null)

const typeMap = { 1: '水电', 2: '家具', 3: '电器', 4: '网络', 5: '其他' }
const statusMap = { 0: '待分配', 1: '处理中', 2: '已完成', 3: '已取消' }

const loadOrders = async () => {
    loading.value = true
    try {
        const res = await getMyRepairs()
        orders.value = (res.data || []).map(order => ({
            ...order,
            typeName: typeMap[order.type],
            statusName: statusMap[order.status]
        }))
    } catch (error) {
        // 不显示错误，直接设为空数组
        orders.value = []
    } finally {
        loading.value = false
    }
}

const cancelOrder = async (id) => {
    try {
        await ElMessageBox.confirm('确定取消此报修单吗？', '提示', { type: 'warning' })
        cancellingId.value = id
        await cancelRepair(id)
        ElMessage.success('已取消')
        loadOrders()
    } catch (error) {
        if (error !== 'cancel') {
            ElMessage.error('取消失败')
        }
    } finally {
        cancellingId.value = null
    }
}

onMounted(() => {
    loadOrders()
})
</script>