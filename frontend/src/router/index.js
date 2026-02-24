import { createRouter, createWebHistory } from 'vue-router'
import RawMaterials from '../views/RawMaterials.vue'
import Products from '../views/Products.vue'
import Optimization from '../views/Optimization.vue'

const routes = [
  {
    path: '/',
    redirect: '/raw-materials'
  },
  {
    path: '/raw-materials',
    name: 'RawMaterials',
    component: RawMaterials
  },
  {
    path: '/products',
    name: 'Products',
    component: Products
  },
  {
    path: '/optimization',
    name: 'Optimization',
    component: Optimization
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
