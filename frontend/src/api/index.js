import axios from 'axios'

const api = axios.create({
  baseURL: '/api',
  timeout: 10000
})

export const rawMaterialApi = {
  getAll: () => api.get('/raw-materials'),
  getById: (id) => api.get(`/raw-materials/${id}`),
  create: (data) => api.post('/raw-materials', data),
  update: (id, data) => api.put(`/raw-materials/${id}`, data),
  delete: (id) => api.delete(`/raw-materials/${id}`)
}

export const productApi = {
  getAll: () => api.get('/products'),
  getById: (id) => api.get(`/products/${id}`),
  create: (data) => api.post('/products', data),
  update: (id, data) => api.put(`/products/${id}`, data),
  delete: (id) => api.delete(`/products/${id}`)
}

export const optimizationApi = {
  optimize: () => api.post('/optimization/optimize')
}

export default api
