import request from './request'

// 管理员登录
export const adminLogin = (data) => {
    return request.post('/api/admin/login', data)
}

// 👇 添加管理员注册
export const adminRegister = (data) => {
    return request.post('/api/admin/register', data)
}

// 获取管理员信息
export const getAdminInfo = () => {
    return request.get('/api/admin/info')
}

// 更新管理员信息
export const updateAdminInfo = (data) => {
    return request.put('/api/admin/info', data)
}

// 获取所有报修单
export const getAllRepairs = (params) => {
    return request.get('/api/admin/repairs', { params })
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