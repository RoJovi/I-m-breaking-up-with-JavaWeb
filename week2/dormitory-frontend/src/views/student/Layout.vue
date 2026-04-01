<template>
    <div>
        <el-menu mode="horizontal">
            <el-menu-item @click="currentTab = 'info'">基本信息</el-menu-item>
            <el-menu-item @click="checkInfoAndGoTo('repair')" :disabled="!isInfoComplete">提交报修</el-menu-item>
            <el-menu-item @click="checkInfoAndGoTo('orders')" :disabled="!isInfoComplete">报修单列表</el-menu-item>
            <el-menu-item @click="logout" style="float: right">退出</el-menu-item>
        </el-menu>
        <div style="padding: 20px">
            <div v-if="currentTab === 'info'">
                <InfoView @info-saved="onInfoSaved" />
            </div>
            <div v-else-if="currentTab === 'repair' && isInfoComplete">
                <RepairView />
            </div>
            <div v-else-if="currentTab === 'orders' && isInfoComplete">
                <OrdersView />
            </div>
            <!-- 未完善信息时显示提示 -->
            <div v-else-if="currentTab !== 'info'" class="warning-container">
                <el-result
                    icon="warning"
                    title="请先完善个人信息"
                    sub-title="您需要先填写姓名、手机号、宿舍等信息后才能使用报修功能"
                >
                    <template #extra>
                        <el-button type="primary" @click="currentTab = 'info'">去完善信息</el-button>
                    </template>
                </el-result>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import InfoView from './Info.vue'
import RepairView from './Repair.vue'
import OrdersView from './Orders.vue'

const router = useRouter()
const currentTab = ref('info')  // 默认显示基本信息页
const isInfoComplete = ref(false)

const checkInfoAndGoTo = (tab) => {
    if (!isInfoComplete.value) {
        ElMessage.warning('请先完善个人信息')
        return
    }
    currentTab.value = tab
}

const onInfoSaved = () => {
    isInfoComplete.value = true
    ElMessage.success('信息已完善，现在可以使用报修功能了')
}

const logout = () => {
    localStorage.removeItem('token')
    localStorage.removeItem('userType')
    localStorage.removeItem('account')
    localStorage.removeItem('isInfoComplete')
    localStorage.removeItem('userId')
    localStorage.removeItem('userName')
    router.push('/login')
}

onMounted(() => {
    // 检查信息是否完善（从localStorage或后端获取）
    isInfoComplete.value = localStorage.getItem('isInfoComplete') === 'true'
})
</script>

<style scoped>
.warning-container {
    display: flex;
    justify-content: center;
    align-items: center;
    min-height: 400px;
}
</style>