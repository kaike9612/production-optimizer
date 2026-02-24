<template>
  <div class="app">
    <aside class="sidebar">
      <div class="sidebar-header">
        <h1 class="logo">StockFlow</h1>
      </div>
      <nav class="sidebar-nav">
        <router-link to="/products" class="nav-item">
          <span class="nav-icon">📦</span>
          <span class="nav-text">{{ $t('nav.products') }}</span>
        </router-link>
        <router-link to="/raw-materials" class="nav-item">
          <span class="nav-icon">🧱</span>
          <span class="nav-text">{{ $t('nav.rawMaterials') }}</span>
        </router-link>
        <router-link to="/optimization" class="nav-item">
          <span class="nav-icon">⚙️</span>
          <span class="nav-text">{{ $t('nav.optimization') }}</span>
        </router-link>
      </nav>
      <div class="sidebar-footer">
        <button @click="toggleLanguage" class="lang-btn">
          {{ currentLanguage === 'en' ? 'PT' : 'EN' }}
        </button>
      </div>
    </aside>
    <main class="main-content">
      <router-view />
    </main>
  </div>
</template>

<script>
import { ref } from 'vue'
import { useI18n } from 'vue-i18n'

export default {
  name: 'App',
  setup() {
    const { locale } = useI18n()
    const currentLanguage = ref(locale.value)

    const toggleLanguage = () => {
      currentLanguage.value = currentLanguage.value === 'en' ? 'pt' : 'en'
      locale.value = currentLanguage.value
    }

    return {
      currentLanguage,
      toggleLanguage
    }
  }
}
</script>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, sans-serif;
  background-color: #f8fafc;
  color: #1e293b;
}

.app {
  min-height: 100vh;
  display: flex;
}

/* Sidebar Styles */
.sidebar {
  width: 260px;
  background-color: #0f172a;
  color: white;
  display: flex;
  flex-direction: column;
  position: fixed;
  height: 100vh;
  left: 0;
  top: 0;
}

.sidebar-header {
  padding: 1.5rem;
  border-bottom: 1px solid rgba(255,255,255,0.1);
}

.logo {
  font-size: 1.5rem;
  font-weight: 700;
  color: #16a34a;
}

.sidebar-nav {
  flex: 1;
  padding: 1rem 0;
}

.nav-item {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  padding: 0.875rem 1.5rem;
  color: #94a3b8;
  text-decoration: none;
  transition: all 0.2s;
  border-left: 3px solid transparent;
}

.nav-item:hover {
  background-color: rgba(255,255,255,0.05);
  color: #e2e8f0;
}

.nav-item.router-link-active {
  background-color: rgba(22, 163, 74, 0.15);
  color: #16a34a;
  border-left-color: #16a34a;
}

.nav-icon {
  font-size: 1.25rem;
}

.nav-text {
  font-weight: 500;
}

.sidebar-footer {
  padding: 1.5rem;
  border-top: 1px solid rgba(255,255,255,0.1);
}

.lang-btn {
  background-color: rgba(255,255,255,0.1);
  color: #94a3b8;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 6px;
  cursor: pointer;
  font-weight: 600;
  font-size: 0.875rem;
  transition: all 0.2s;
}

.lang-btn:hover {
  background-color: rgba(255,255,255,0.2);
  color: white;
}

/* Main Content */
.main-content {
  flex: 1;
  margin-left: 260px;
  padding: 2rem;
  min-height: 100vh;
}

/* Global Card Styles */
.card {
  background-color: white;
  border-radius: 12px;
  border: 1px solid #e2e8f0;
  padding: 1.5rem;
  box-shadow: 0 1px 3px rgba(0,0,0,0.05);
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1.5rem;
}

.card-title {
  font-size: 1.25rem;
  font-weight: 600;
  color: #1e293b;
}

/* Button Styles */
.btn {
  padding: 0.625rem 1rem;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-weight: 500;
  font-size: 0.875rem;
  transition: all 0.2s;
}

.btn-primary {
  background-color: #16a34a;
  color: white;
}

.btn-primary:hover {
  background-color: #15803d;
}

.btn-primary:disabled {
  background-color: #86efac;
  cursor: not-allowed;
}

.btn-danger {
  background-color: #ef4444;
  color: white;
}

.btn-danger:hover {
  background-color: #dc2626;
}

.btn-secondary {
  background-color: #f1f5f9;
  color: #475569;
}

.btn-secondary:hover {
  background-color: #e2e8f0;
}

/* Form Styles */
.form-group {
  margin-bottom: 1rem;
}

.form-label {
  display: block;
  margin-bottom: 0.5rem;
  font-weight: 500;
  color: #475569;
  font-size: 0.875rem;
}

.form-input {
  width: 100%;
  padding: 0.625rem 0.875rem;
  border: 1px solid #e2e8f0;
  border-radius: 6px;
  font-size: 0.875rem;
  transition: border-color 0.2s;
}

.form-input:focus {
  outline: none;
  border-color: #16a34a;
}

.form-select {
  width: 100%;
  padding: 0.625rem 0.875rem;
  border: 1px solid #e2e8f0;
  border-radius: 6px;
  font-size: 0.875rem;
  background-color: white;
}

.form-select:focus {
  outline: none;
  border-color: #16a34a;
}

.form-error {
  color: #ef4444;
  font-size: 0.75rem;
  margin-top: 0.25rem;
}

/* Table Styles */
.table-container {
  overflow-x: auto;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  padding: 0.875rem 1rem;
  text-align: left;
  border-bottom: 1px solid #e2e8f0;
}

th {
  background-color: #f8fafc;
  font-weight: 600;
  color: #475569;
  font-size: 0.75rem;
  text-transform: uppercase;
  letter-spacing: 0.05em;
}

tr:hover {
  background-color: #f8fafc;
}

/* Actions */
.actions {
  display: flex;
  gap: 0.5rem;
}

/* Modal Styles */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(15, 23, 42, 0.6);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.modal {
  background-color: white;
  border-radius: 12px;
  padding: 1.5rem;
  max-width: 500px;
  width: 90%;
  max-height: 90vh;
  overflow-y: auto;
}

.modal-large {
  max-width: 700px;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1.5rem;
}

.modal-title {
  font-size: 1.25rem;
  font-weight: 600;
  color: #1e293b;
}

.modal-close {
  background: none;
  border: none;
  font-size: 1.5rem;
  cursor: pointer;
  color: #94a3b8;
  line-height: 1;
}

.modal-close:hover {
  color: #475569;
}

.modal-body {
  margin-bottom: 1.5rem;
}

.modal-footer {
  display: flex;
  justify-content: flex-end;
  gap: 0.75rem;
}

/* Empty & Loading States */
.empty-state {
  text-align: center;
  padding: 3rem;
  color: #94a3b8;
}

.loading {
  text-align: center;
  padding: 3rem;
  color: #64748b;
}

/* Error & Success */
.error {
  color: #ef4444;
  padding: 1rem;
  background-color: #fef2f2;
  border-radius: 8px;
  margin-bottom: 1rem;
  font-size: 0.875rem;
}

.success {
  color: #16a34a;
  padding: 1rem;
  background-color: #f0fdf4;
  border-radius: 8px;
  margin-bottom: 1rem;
  font-size: 0.875rem;
}

/* Responsive */
@media (max-width: 768px) {
  .sidebar {
    width: 100%;
    height: auto;
    position: relative;
  }
  
  .main-content {
    margin-left: 0;
    padding: 1rem;
  }
}
</style>
