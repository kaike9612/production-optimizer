<template>
  <div class="products">
    <div class="card">
      <div class="card-header">
        <h2 class="card-title">{{ $t('product.title') }}</h2>
        <button @click="openModal()" class="btn btn-primary">
          {{ $t('product.add') }}
        </button>
      </div>

      <div v-if="error" class="error">{{ error }}</div>

      <div v-if="loading" class="loading">{{ $t('common.loading') }}</div>

      <div v-else-if="products.length === 0" class="empty-state">
        {{ $t('product.noData') }}
      </div>

      <div v-else class="table-container">
        <table>
          <thead>
            <tr>
              <th>{{ $t('product.code') }}</th>
              <th>{{ $t('product.name') }}</th>
              <th>{{ $t('product.salesValue') }}</th>
              <th>{{ $t('product.compositions') }}</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="product in products" :key="product.id">
              <td>{{ product.code }}</td>
              <td>{{ product.name }}</td>
              <td>{{ product.salesValue }}</td>
              <td>{{ product.compositions?.length || 0 }}</td>
              <td class="actions">
                <button @click="openModal(product)" class="btn btn-secondary">
                  Edit
                </button>
                <button @click="confirmDelete(product)" class="btn btn-danger">
                  {{ $t('product.delete') }}
                </button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <!-- Modal -->
    <div v-if="showModal" class="modal-overlay" @click.self="closeModal">
      <div class="modal modal-large">
        <div class="modal-header">
          <h3 class="modal-title">
            {{ editingId ? $t('product.edit') : $t('product.add') }}
          </h3>
          <button class="modal-close" @click="closeModal">&times;</button>
        </div>
        <form @submit.prevent="saveProduct">
          <div class="modal-body">
            <div class="form-group">
              <label class="form-label">{{ $t('product.code') }} *</label>
              <input
                v-model="form.code"
                type="text"
                class="form-input"
                required
              />
            </div>
            <div class="form-group">
              <label class="form-label">{{ $t('product.name') }} *</label>
              <input
                v-model="form.name"
                type="text"
                class="form-input"
                required
              />
            </div>
            <div class="form-group">
              <label class="form-label">{{ $t('product.salesValue') }} *</label>
              <input
                v-model.number="form.salesValue"
                type="number"
                step="0.01"
                min="0"
                class="form-input"
                required
              />
            </div>

            <div class="compositions-section">
              <div class="compositions-header">
                <h4>{{ $t('product.compositions') }}</h4>
                <button type="button" @click="addComposition" class="btn btn-secondary">
                  {{ $t('product.addComposition') }}
                </button>
              </div>

              <div v-for="(comp, index) in form.compositions" :key="index" class="composition-row">
                <div class="form-group">
                  <label class="form-label">{{ $t('product.rawMaterial') }} *</label>
                  <select
                    v-model="comp.rawMaterialId"
                    class="form-select"
                    required
                  >
                    <option value="">{{ $t('product.selectRawMaterial') }}</option>
                    <option
                      v-for="rm in rawMaterials"
                      :key="rm.id"
                      :value="rm.id"
                    >
                      {{ rm.code }} - {{ rm.name }}
                    </option>
                  </select>
                </div>
                <div class="form-group">
                  <label class="form-label">{{ $t('product.quantityNeeded') }} *</label>
                  <input
                    v-model.number="comp.quantityNeeded"
                    type="number"
                    step="0.01"
                    min="0.01"
                    class="form-input"
                    required
                  />
                </div>
                <button type="button" @click="removeComposition(index)" class="btn-remove">
                  &times;
                </button>
              </div>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" @click="closeModal" class="btn btn-secondary">
              {{ $t('product.cancel') }}
            </button>
            <button type="submit" class="btn btn-primary">
              {{ $t('product.save') }}
            </button>
          </div>
        </form>
      </div>
    </div>

    <!-- Delete Confirmation -->
    <div v-if="showDeleteConfirm" class="modal-overlay" @click.self="showDeleteConfirm = false">
      <div class="modal">
        <div class="modal-header">
          <h3 class="modal-title">{{ $t('product.delete') }}</h3>
          <button class="modal-close" @click="showDeleteConfirm = false">&times;</button>
        </div>
        <div class="modal-body">
          <p>{{ $t('product.confirmDelete') }}</p>
        </div>
        <div class="modal-footer">
          <button @click="showDeleteConfirm = false" class="btn btn-secondary">
            {{ $t('product.cancel') }}
          </button>
          <button @click="deleteProduct" class="btn btn-danger">
            {{ $t('product.delete') }}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'
import { productApi, rawMaterialApi } from '../api'

export default {
  name: 'Products',
  setup() {
    const products = ref([])
    const rawMaterials = ref([])
    const loading = ref(false)
    const error = ref('')
    const showModal = ref(false)
    const showDeleteConfirm = ref(false)
    const editingId = ref(null)
    const itemToDelete = ref(null)

    const form = ref({
      code: '',
      name: '',
      salesValue: 0,
      compositions: []
    })

    const loadProducts = async () => {
      loading.value = true
      error.value = ''
      try {
        const response = await productApi.getAll()
        products.value = response.data
      } catch (err) {
        error.value = err.response?.data?.message || 'Error loading products'
      } finally {
        loading.value = false
      }
    }

    const loadRawMaterials = async () => {
      try {
        const response = await rawMaterialApi.getAll()
        rawMaterials.value = response.data
      } catch (err) {
        console.error('Error loading raw materials:', err)
      }
    }

    const openModal = async (item = null) => {
      await loadRawMaterials()
      if (item) {
        editingId.value = item.id
        form.value = {
          code: item.code,
          name: item.name,
          salesValue: item.salesValue,
          compositions: item.compositions?.map(c => ({
            rawMaterialId: c.rawMaterialId,
            quantityNeeded: c.quantityNeeded
          })) || []
        }
      } else {
        editingId.value = null
        form.value = {
          code: '',
          name: '',
          salesValue: 0,
          compositions: []
        }
      }
      showModal.value = true
    }

    const closeModal = () => {
      showModal.value = false
      editingId.value = null
    }

    const addComposition = () => {
      form.value.compositions.push({
        rawMaterialId: '',
        quantityNeeded: 0
      })
    }

    const removeComposition = (index) => {
      form.value.compositions.splice(index, 1)
    }

    const saveProduct = async () => {
      error.value = ''
      try {
        const data = {
          code: form.value.code,
          name: form.value.name,
          salesValue: form.value.salesValue,
          compositions: form.value.compositions
            .filter(c => c.rawMaterialId && c.quantityNeeded > 0)
            .map(c => ({
              rawMaterialId: c.rawMaterialId,
              quantityNeeded: c.quantityNeeded
            }))
        }

        if (editingId.value) {
          await productApi.update(editingId.value, data)
        } else {
          await productApi.create(data)
        }
        closeModal()
        loadProducts()
      } catch (err) {
        error.value = err.response?.data?.message || 'Error saving product'
      }
    }

    const confirmDelete = (item) => {
      itemToDelete.value = item
      showDeleteConfirm.value = true
    }

    const deleteProduct = async () => {
      if (!itemToDelete.value) return
      error.value = ''
      try {
        await productApi.delete(itemToDelete.value.id)
        showDeleteConfirm.value = false
        itemToDelete.value = null
        loadProducts()
      } catch (err) {
        error.value = err.response?.data?.message || 'Error deleting product'
      }
    }

    onMounted(() => {
      loadProducts()
    })

    return {
      products,
      rawMaterials,
      loading,
      error,
      showModal,
      showDeleteConfirm,
      editingId,
      form,
      openModal,
      closeModal,
      addComposition,
      removeComposition,
      saveProduct,
      confirmDelete,
      deleteProduct
    }
  }
}
</script>

<style scoped>
.page-header {
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

.modal-large {
  max-width: 700px;
}

.compositions-section {
  margin-top: 1.5rem;
  padding-top: 1.5rem;
  border-top: 1px solid #e2e8f0;
}

.compositions-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1rem;
}

.compositions-header h4 {
  font-size: 1rem;
  font-weight: 600;
  color: #475569;
}

.composition-row {
  display: flex;
  gap: 0.75rem;
  align-items: flex-start;
  margin-bottom: 0.75rem;
  padding: 1rem;
  background-color: #f8fafc;
  border-radius: 8px;
}

.composition-row .form-group {
  flex: 1;
  margin-bottom: 0;
}

.btn-remove {
  background: #fee2e2;
  color: #ef4444;
  border: none;
  width: 36px;
  height: 36px;
  border-radius: 6px;
  cursor: pointer;
  font-size: 1.25rem;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.2s;
}

.btn-remove:hover {
  background: #fecaca;
}

.badge {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  padding: 0.25rem 0.625rem;
  background-color: #f0fdf4;
  color: #16a34a;
  border-radius: 9999px;
  font-size: 0.75rem;
  font-weight: 600;
}

.actions .btn {
  padding: 0.375rem 0.625rem;
  font-size: 0.75rem;
}

.actions .btn-secondary {
  background-color: #f1f5f9;
  color: #475569;
}

.actions .btn-secondary:hover {
  background-color: #e2e8f0;
}

.actions .btn-danger {
  background-color: #fee2e2;
  color: #ef4444;
}

.actions .btn-danger:hover {
  background-color: #fecaca;
}
</style>
