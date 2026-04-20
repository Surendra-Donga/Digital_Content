import axios from 'axios'

const API_BASE_URL = '/api'

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json'
  }
})

export const userService = {
  getAllUsers: () => api.get('/users'),
  getUsersByRole: (role) => api.get(`/users/role/${role}`),
  createUser: (data) => api.post('/users', data)
}

export const contentService = {
  getAllContent: () => api.get('/content'),
  getContentById: (id) => api.get(`/content/${id}`),
  createDraft: (data) => api.post('/content/draft', data),
  registerContent: (id) => api.put(`/content/${id}/register`),
  approveContent: (id) => api.put(`/content/${id}/approve`)
}

export const contentRightsService = {
  getRightsByContent: (contentId) => api.get(`/rights/content/${contentId}`),
  assignRights: (data) => api.post('/rights', data)
}

export const usageService = {
  getTransactionsByStatus: (status) => api.get(`/usage/status/${status}`),
  recordUsage: (data) => api.post('/usage', data),
  verifyTransaction: (id) => api.put(`/usage/${id}/verify`)
}

export const royaltyService = {
  calculateRoyalty: (contentId) => api.post(`/royalty/calculate/${contentId}`),
  approveCalculation: (id) => api.put(`/royalty/approve/${id}`)
}

export const paymentService = {
  getAllPayments: () => api.get('/payments'),
  initiatePayment: (data) => api.post('/payments', data)
}

export default api
