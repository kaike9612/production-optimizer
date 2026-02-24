<template>
  <div class="optimization">
    <div class="page-header">
      <div>
        <h1 class="page-title">{{ $t('optimization.title') }}</h1>
        <p class="page-subtitle">Otimize sua produção baseado nas matérias-primas disponíveis</p>
      </div>
      <button @click="runOptimization" class="btn btn-primary" :disabled="loading">
        {{ loading ? $t('common.loading') : $t('optimization.optimize') }}
      </button>
    </div>

    <div v-if="error" class="error">{{ error }}</div>

    <div v-if="!result && !loading && !error" class="empty-state">
      <div class="empty-icon">📊</div>
      <p>{{ $t('optimization.noResults') }}</p>
      <p class="empty-hint">Clique no botão "Recalcular" para otimizar sua produção</p>
    </div>

    <!-- KPI Cards -->
    <div v-if="result" class="kpi-grid">
      <div class="kpi-card">
        <div class="kpi-icon products">📦</div>
        <div class="kpi-content">
          <div class="kpi-label">{{ $t('optimization.kpiProducts') }}</div>
          <div class="kpi-value">{{ result.producedProducts?.length || 0 }}</div>
        </div>
      </div>
      <div class="kpi-card">
        <div class="kpi-icon units">🏭</div>
        <div class="kpi-content">
          <div class="kpi-label">{{ $t('optimization.kpiUnits') }}</div>
          <div class="kpi-value">{{ totalUnits }}</div>
        </div>
      </div>
      <div class="kpi-card">
        <div class="kpi-icon value">💰</div>
        <div class="kpi-content">
          <div class="kpi-label">{{ $t('optimization.kpiValue') }}</div>
          <div class="kpi-value highlight">{{ formatCurrency(result.totalSalesValue) }}</div>
        </div>
      </div>
    </div>

    <div v-if="result" class="results">
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
                <td><span class="quantity-badge">{{ pp.quantityProduced }}</span></td>
                <td>{{ formatCurrency(pp.salesValue) }}</td>
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
</template>

<script>
import { ref, computed } from 'vue'
import { optimizationApi } from '../api'

export default {
  name: 'Optimization',
  setup() {
    const result = ref(null)
    const loading = ref(false)
    const error = ref('')

    const totalUnits = computed(() => {
      if (!result.value?.producedProducts) return 0
      return result.value.producedProducts.reduce((sum, pp) => sum + (pp.quantityProduced || 0), 0)
    })

    const formatCurrency = (value) => {
      if (value === null || value === undefined) return 'R$ 0,00'
      return new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(value)
    }

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
      totalUnits,
      formatCurrency,
      runOptimization
    }
  }
}
</script>

<style scoped>
.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1.5rem;
}

.page-title {
  font-size: 1.5rem;
  font-weight: 600;
  color: #1e293b;
}

.page-subtitle {
  color: #64748b;
  font-size: 0.875rem;
  margin-top: 0.25rem;
}

.empty-icon {
  font-size: 3rem;
  margin-bottom: 1rem;
}

.empty-hint {
  color: #94a3b8;
  font-size: 0.875rem;
  margin-top: 0.5rem;
}

/* KPI Cards Grid */
.kpi-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 1.5rem;
  margin-bottom: 2rem;
}

.kpi-card {
  background: white;
  border: 1px solid #e2e8f0;
  border-radius: 12px;
  padding: 1.5rem;
  display: flex;
  align-items: center;
  gap: 1rem;
}

.kpi-icon {
  width: 48px;
  height: 48px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.5rem;
}

.kpi-icon.products {
  background-color: #dbeafe;
}

.kpi-icon.units {
  background-color: #f0fdf4;
}

.kpi-icon.value {
  background-color: #fef3c7;
}

.kpi-content {
  flex: 1;
}

.kpi-label {
  font-size: 0.75rem;
  color: #64748b;
  text-transform: uppercase;
  letter-spacing: 0.05em;
  margin-bottom: 0.25rem;
}

.kpi-value {
  font-size: 1.5rem;
  font-weight: 700;
  color: #1e293b;
}

.kpi-value.highlight {
  color: #16a34a;
}

/* Results Section */
.results {
  margin-top: 1.5rem;
}

.section {
  margin-bottom: 2rem;
}

.section-title {
  font-size: 1rem;
  font-weight: 600;
  color: #1e293b;
  margin-bottom: 1rem;
  padding-bottom: 0.75rem;
  border-bottom: 1px solid #e2e8f0;
}

/* Badge for quantity in table */
.quantity-badge {
  display: inline-flex;
  align-items: center;
  padding: 0.25rem 0.625rem;
  background-color: #f0fdf4;
  color: #16a34a;
  border-radius: 9999px;
  font-size: 0.75rem;
  font-weight: 600;
}

/* Summary Card */
.summary-card {
  background: linear-gradient(135deg, #16a34a 0%, #15803d 100%);
  border-radius: 12px;
  padding: 1.5rem;
  color: white;
  margin-bottom: 2rem;
}

.summary-label {
  font-size: 0.875rem;
  opacity: 0.9;
  margin-bottom: 0.5rem;
}

.summary-value {
  font-size: 2rem;
  font-weight: 700;
}

/* Responsive */
@media (max-width: 1024px) {
  .kpi-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 640px) {
  .kpi-grid {
    grid-template-columns: 1fr;
  }
}
</style>
