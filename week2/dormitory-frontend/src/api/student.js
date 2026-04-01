import request from './request'

// 学生登录
export const studentLogin = (data) => {
    return request.post('/api/student/login', data)
}

// 学生注册（只需要学号和密码）
export const studentRegister = (data) => {
    return request.post('/api/student/register', data)
}

// 获取学生信息
export const getStudentInfo = () => {
    return request.get('/api/student/info')
}

// 更新学生信息（姓名、手机号、宿舍等）
export const updateStudentInfo = (data) => {
    return request.put('/api/student/info', data)
}

// 提交报修
export const submitRepair = (data) => {
    return request.post('/api/repair/submit', data)
}

// 获取我的报修单
export const getMyRepairs = () => {
    return request.get('/api/repair/my-list')
}

// 取消报修
export const cancelRepair = (id) => {
    return request.put(`/api/repair/${id}/cancel`)
}