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
  getUserById: (id) => api.get(`/users/${id}`),
  createUser: (data) => api.post('/users', data),
  updateUser: (id, data) => api.put(`/users/${id}`, data),
  deleteUser: (id) => api.delete(`/users/${id}`),
  getUserByEmail: (email) => api.get(`/users/email/${email}`)
}

export const contentService = {
  getAllContent: () => api.get('/content'),
  getContentById: (id) => api.get(`/content/${id}`),
  getContentByCreator: (userId) => api.get(`/content/creator/${userId}`),
  createContent: (data) => api.post('/content', data),
  updateContent: (id, data) => api.put(`/content/${id}`, data),
  deleteContent: (id) => api.delete(`/content/${id}`),
  approveContent: (id) => api.put(`/content/${id}/approve`)
}

export const contentRightsService = {
  getAllRights: () => api.get('/rights'),
  getRightsById: (id) => api.get(`/rights/${id}`),
  getRightsByContent: (contentId) => api.get(`/rights/content/${contentId}`),
  createRights: (data) => api.post('/rights', data),
  updateRights: (id, data) => api.put(`/rights/${id}`, data),
  deleteRights: (id) => api.delete(`/rights/${id}`)
}

export const usageService = {
  getAllTransactions: () => api.get('/usage'),
  getTransactionById: (id) => api.get(`/usage/${id}`),
  getTransactionsByStatus: (status) => api.get(`/usage/status/${status}`),
  recordUsage: (data) => api.post('/usage', data),
  verifyTransaction: (id) => api.put(`/usage/${id}/verify`),
  settleTransaction: (id) => api.put(`/usage/${id}/settle`)
}

export const royaltyService = {
  getAllCalculations: () => api.get('/royalty/calculations'),
  getCalculationById: (id) => api.get(`/royalty/calculations/${id}`),
  calculateRoyalty: (contentId) => api.post(`/royalty/calculate/${contentId}`),
  approveCalculation: (id) => api.put(`/royalty/calculations/${id}/approve`),
  getAllPayments: () => api.get('/royalty/payments'),
  getPaymentById: (id) => api.get(`/royalty/payments/${id}`),
  processPayment: (data) => api.post('/royalty/payments', data),
  getCreatorEarnings: (userId) => api.get(`/royalty/earnings/${userId}`)
}

export default api
