import request from './request'

// 获取宿舍列表
export const getDormitoryList = () => {
    return request.get('/api/dormitory/list')
}

// 获取报修单详情
export const getRepairDetail = (id) => {
    return request.get(`/api/admin/repair/${id}`)
}

// 更新报修状态
export const updateRepairStatus = (id, data) => {
    return request.put(`/api/admin/repair/${id}/status`, data)
}

// 删除报修单
export const deleteRepair = (id) => {
    return request.delete(`/api/admin/repair/${id}`)
}