<template>
  <div class="raw-materials">
    <div class="card">
      <div class="card-header">
        <h2 class="card-title">{{ $t('rawMaterial.title') }}</h2>
        <button @click="openModal()" class="btn btn-primary">
          {{ $t('rawMaterial.add') }}
        </button>
      </div>

      <div v-if="error" class="error">{{ error }}</div>

      <div v-if="loading" class="loading">{{ $t('common.loading') }}</div>

      <div v-else-if="rawMaterials.length === 0" class="empty-state">
        {{ $t('rawMaterial.noData') }}
      </div>

      <div v-else class="table-container">
        <table>
          <thead>
            <tr>
              <th>{{ $t('rawMaterial.code') }}</th>
              <th>{{ $t('rawMaterial.name') }}</th>
              <th>{{ $t('rawMaterial.availableQuantity') }}</th>
              <th>{{ $t('rawMaterial.unit') }}</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="rm in rawMaterials" :key="rm.id">
              <td>{{ rm.code }}</td>
              <td>{{ rm.name }}</td>
              <td>{{ rm.availableQuantity }}</td>
              <td>{{ rm.unit }}</td>
              <td class="actions">
                <button @click="openModal(rm)" class="btn btn-secondary">
                  Edit
                </button>
                <button @click="confirmDelete(rm)" class="btn btn-danger">
                  {{ $t('rawMaterial.delete') }}
                </button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <!-- Modal -->
    <div v-if="showModal" class="modal-overlay" @click.self="closeModal">
      <div class="modal">
        <div class="modal-header">
          <h3 class="modal-title">
            {{ editingId ? $t('rawMaterial.edit') : $t('rawMaterial.add') }}
          </h3>
          <button class="modal-close" @click="closeModal">&times;</button>
        </div>
        <form @submit.prevent="saveRawMaterial">
          <div class="modal-body">
            <div class="form-group">
              <label class="form-label">{{ $t('rawMaterial.code') }} *</label>
              <input
                v-model="form.code"
                type="text"
                class="form-input"
                required
              />
            </div>
            <div class="form-group">
              <label class="form-label">{{ $t('rawMaterial.name') }} *</label>
              <input
                v-model="form.name"
                type="text"
                class="form-input"
                required
              />
            </div>
            <div class="form-group">
              <label class="form-label">{{ $t('rawMaterial.availableQuantity') }} *</label>
              <input
                v-model.number="form.availableQuantity"
                type="number"
                step="0.01"
                min="0"
                class="form-input"
                required
              />
            </div>
            <div class="form-group">
              <label class="form-label">{{ $t('rawMaterial.unit') }} *</label>
              <input
                v-model="form.unit"
                type="text"
                class="form-input"
                required
              />
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" @click="closeModal" class="btn btn-secondary">
              {{ $t('rawMaterial.cancel') }}
            </button>
            <button type="submit" class="btn btn-primary">
              {{ $t('rawMaterial.save') }}
            </button>
          </div>
        </form>
      </div>
    </div>

    <!-- Delete Confirmation -->
    <div v-if="showDeleteConfirm" class="modal-overlay" @click.self="showDeleteConfirm = false">
      <div class="modal">
        <div class="modal-header">
          <h3 class="modal-title">{{ $t('rawMaterial.delete') }}</h3>
          <button class="modal-close" @click="showDeleteConfirm = false">&times;</button>
        </div>
        <div class="modal-body">
          <p>{{ $t('rawMaterial.confirmDelete') }}</p>
        </div>
        <div class="modal-footer">
          <button @click="showDeleteConfirm = false" class="btn btn-secondary">
            {{ $t('rawMaterial.cancel') }}
          </button>
          <button @click="deleteRawMaterial" class="btn btn-danger">
            {{ $t('rawMaterial.delete') }}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'
import { rawMaterialApi } from '../api'

export default {
  name: 'RawMaterials',
  setup() {
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
      availableQuantity: 0,
      unit: ''
    })

    const loadRawMaterials = async () => {
      loading.value = true
      error.value = ''
      try {
        const response = await rawMaterialApi.getAll()
        rawMaterials.value = response.data
      } catch (err) {
        error.value = err.response?.data?.message || 'Error loading raw materials'
      } finally {
        loading.value = false
      }
    }

    const openModal = (item = null) => {
      if (item) {
        editingId.value = item.id
        form.value = { ...item }
      } else {
        editingId.value = null
        form.value = {
          code: '',
          name: '',
          availableQuantity: 0,
          unit: ''
        }
      }
      showModal.value = true
    }

    const closeModal = () => {
      showModal.value = false
      editingId.value = null
    }

    const saveRawMaterial = async () => {
      error.value = ''
      try {
        if (editingId.value) {
          await rawMaterialApi.update(editingId.value, form.value)
        } else {
          await rawMaterialApi.create(form.value)
        }
        closeModal()
        loadRawMaterials()
      } catch (err) {
        error.value = err.response?.data?.message || 'Error saving raw material'
      }
    }

    const confirmDelete = (item) => {
      itemToDelete.value = item
      showDeleteConfirm.value = true
    }

    const deleteRawMaterial = async () => {
      if (!itemToDelete.value) return
      error.value = ''
      try {
        await rawMaterialApi.delete(itemToDelete.value.id)
        showDeleteConfirm.value = false
        itemToDelete.value = null
        loadRawMaterials()
      } catch (err) {
        error.value = err.response?.data?.message || 'Error deleting raw material'
      }
    }

    onMounted(() => {
      loadRawMaterials()
    })

    return {
      rawMaterials,
      loading,
      error,
      showModal,
      showDeleteConfirm,
      editingId,
      form,
      openModal,
      closeModal,
      saveRawMaterial,
      confirmDelete,
      deleteRawMaterial
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
