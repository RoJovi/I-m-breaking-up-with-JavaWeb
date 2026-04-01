<template>
    <div>
        <el-menu mode="horizontal">
            <el-menu-item @click="currentTab = 'info'">基本信息</el-menu-item>
            <el-menu-item @click="checkInfoAndGoTo('orders')" :disabled="!isInfoComplete">报修单列表</el-menu-item>
            <el-menu-item @click="logout" style="float: right">退出</el-menu-item>
        </el-menu>
        <div style="padding: 20px">
            <div v-if="currentTab === 'info'">
                <InfoView @info-saved="onInfoSaved" />
            </div>
            <div v-else-if="currentTab === 'orders'">
                <OrdersView />
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import InfoView from './Info.vue'
import OrdersView from './Orders.vue'
import { getAdminInfo } from '@/api/admin'

const router = useRouter()
const currentTab = ref('info')
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
}

const logout = () => {
    localStorage.removeItem('token')
    localStorage.removeItem('userType')
    localStorage.removeItem('account')
    localStorage.removeItem('userId')
    localStorage.removeItem('userName')
    localStorage.removeItem('adminInfoComplete')
    router.push('/login')
}

// 检查管理员信息是否完善
const checkInfoComplete = async () => {
    try {
        const res = await getAdminInfo()
        if (res.data && res.data.name && res.data.phone) {
            isInfoComplete.value = true
            localStorage.setItem('adminInfoComplete', 'true')
        } else {
            isInfoComplete.value = false
        }
    } catch (error) {
        isInfoComplete.value = localStorage.getItem('adminInfoComplete') === 'true'
    }
}

onMounted(() => {
    checkInfoComplete()
})
</script>