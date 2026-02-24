<template>
  <div class="optimization">
    <div class="card">
      <div class="card-header">
        <h2 class="card-title">{{ $t('optimization.title') }}</h2>
        <button @click="runOptimization" class="btn btn-primary" :disabled="loading">
          {{ loading ? $t('common.loading') : $t('optimization.optimize') }}
        </button>
      </div>

      <div v-if="error" class="error">{{ error }}</div>

      <div v-if="!result && !loading && !error" class="empty-state">
        {{ $t('optimization.noResults') }}
      </div>

      <div v-if="result" class="results">
        <div class="summary">
          <div class="summary-item">
            <span class="summary-label">{{ $t('optimization.totalSalesValue') }}:</span>
            <span class="summary-value">{{ result.totalSalesValue }}</span>
          </div>
        </div>

        <div v-if="result.producedProducts && result.producedProducts.length > 0" class="section">
          <h3 class="section-title">{{ $t('optimization.producedProducts') }}</h3>
          <div class="table-container">
            <table>
              <thead>
                <tr>
                  <th>{{ $t('optimization.productCode') }}</th>
                  <th>{{ $t('optimization.productName') }}</th>
                  <th>{{ $t('optimization.quantityProduced') }}</th>
                  <th>{{ $t('optimization.salesValue') }}</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(pp, index) in result.producedProducts" :key="index">
                  <td>{{ pp.productCode }}</td>
                  <td>{{ pp.productName }}</td>
                  <td>{{ pp.quantityProduced }}</td>
                  <td>{{ pp.salesValue }}</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>

        <div v-if="result.remainingRawMaterials && result.remainingRawMaterials.length > 0" class="section">
          <h3 class="section-title">{{ $t('optimization.remainingRawMaterials') }}</h3>
          <div class="table-container">
            <table>
              <thead>
                <tr>
                  <th>{{ $t('rawMaterial.code') }}</th>
                  <th>{{ $t('rawMaterial.name') }}</th>
                  <th>{{ $t('rawMaterial.availableQuantity') }}</th>
                  <th>{{ $t('rawMaterial.unit') }}</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="rm in result.remainingRawMaterials" :key="rm.id">
                  <td>{{ rm.code }}</td>
                  <td>{{ rm.name }}</td>
                  <td>{{ rm.availableQuantity }}</td>
                  <td>{{ rm.unit }}</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue'
import { optimizationApi } from '../api'

export default {
  name: 'Optimization',
  setup() {
    const result = ref(null)
    const loading = ref(false)
    const error = ref('')

    const runOptimization = async () => {
      loading.value = true
      error.value = ''
      result.value = null

      try {
        const response = await optimizationApi.optimize()
        result.value = response.data
      } catch (err) {
        error.value = err.response?.data?.message || 'Error running optimization'
      } finally {
        loading.value = false
      }
    }

    return {
      result,
      loading,
      error,
      runOptimization
    }
  }
}
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1.5rem;
}

.results {
  margin-top: 1rem;
}

.summary {
  background-color: #f8f9fa;
  padding: 1rem;
  border-radius: 4px;
  margin-bottom: 1.5rem;
}

.summary-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.summary-label {
  font-weight: 600;
  color: #555;
}

.summary-value {
  font-size: 1.25rem;
  font-weight: 700;
  color: #27ae60;
}

.section {
  margin-bottom: 1.5rem;
}

.section-title {
  font-size: 1rem;
  color: #555;
  margin-bottom: 1rem;
}
</style>
