<template>
  <div class="app">
    <header class="header">
      <h1 class="title">Production Optimizer</h1>
      <nav class="nav">
        <router-link to="/raw-materials" class="nav-link">{{ $t('nav.rawMaterials') }}</router-link>
        <router-link to="/products" class="nav-link">{{ $t('nav.products') }}</router-link>
        <router-link to="/optimization" class="nav-link">{{ $t('nav.optimization') }}</router-link>
      </nav>
      <div class="language-toggle">
        <button @click="toggleLanguage" class="lang-btn">
          {{ currentLanguage === 'en' ? 'PT' : 'EN' }}
        </button>
      </div>
    </header>
    <main class="main">
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
  background-color: #f5f5f5;
  color: #333;
}

.app {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.header {
  background-color: #2c3e50;
  color: white;
  padding: 1rem 2rem;
  display: flex;
  align-items: center;
  justify-content: space-between;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.title {
  font-size: 1.5rem;
  font-weight: 600;
}

.nav {
  display: flex;
  gap: 1rem;
}

.nav-link {
  color: white;
  text-decoration: none;
  padding: 0.5rem 1rem;
  border-radius: 4px;
  transition: background-color 0.2s;
}

.nav-link:hover {
  background-color: rgba(255,255,255,0.1);
}

.nav-link.router-link-active {
  background-color: #3498db;
}

.language-toggle {
  display: flex;
  align-items: center;
}

.lang-btn {
  background-color: #3498db;
  color: white;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 4px;
  cursor: pointer;
  font-weight: 600;
  transition: background-color 0.2s;
}

.lang-btn:hover {
  background-color: #2980b9;
}

.main {
  flex: 1;
  padding: 2rem;
  max-width: 1200px;
  margin: 0 auto;
  width: 100%;
}

.card {
  background-color: white;
  border-radius: 8px;
  padding: 1.5rem;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
}

.card-title {
  font-size: 1.25rem;
  font-weight: 600;
  margin-bottom: 1rem;
  color: #2c3e50;
}

.btn {
  padding: 0.5rem 1rem;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-weight: 500;
  transition: all 0.2s;
}

.btn-primary {
  background-color: #3498db;
  color: white;
}

.btn-primary:hover {
  background-color: #2980b9;
}

.btn-danger {
  background-color: #e74c3c;
  color: white;
}

.btn-danger:hover {
  background-color: #c0392b;
}

.btn-secondary {
  background-color: #95a5a6;
  color: white;
}

.btn-secondary:hover {
  background-color: #7f8c8d;
}

.form-group {
  margin-bottom: 1rem;
}

.form-label {
  display: block;
  margin-bottom: 0.5rem;
  font-weight: 500;
  color: #555;
}

.form-input {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 1rem;
}

.form-input:focus {
  outline: none;
  border-color: #3498db;
}

.form-select {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 1rem;
  background-color: white;
}

.form-error {
  color: #e74c3c;
  font-size: 0.875rem;
  margin-top: 0.25rem;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  padding: 0.75rem;
  text-align: left;
  border-bottom: 1px solid #eee;
}

th {
  background-color: #f8f9fa;
  font-weight: 600;
  color: #555;
}

tr:hover {
  background-color: #f8f9fa;
}

.table-container {
  overflow-x: auto;
}

.actions {
  display: flex;
  gap: 0.5rem;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0,0,0,0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.modal {
  background-color: white;
  border-radius: 8px;
  padding: 1.5rem;
  max-width: 500px;
  width: 90%;
  max-height: 90vh;
  overflow-y: auto;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1rem;
}

.modal-title {
  font-size: 1.25rem;
  font-weight: 600;
}

.modal-close {
  background: none;
  border: none;
  font-size: 1.5rem;
  cursor: pointer;
  color: #999;
}

.modal-close:hover {
  color: #333;
}

.modal-body {
  margin-bottom: 1rem;
}

.modal-footer {
  display: flex;
  justify-content: flex-end;
  gap: 0.5rem;
}

.empty-state {
  text-align: center;
  padding: 2rem;
  color: #999;
}

.loading {
  text-align: center;
  padding: 2rem;
  color: #666;
}

.error {
  color: #e74c3c;
  padding: 1rem;
  background-color: #fadbd8;
  border-radius: 4px;
  margin-bottom: 1rem;
}

.success {
  color: #27ae60;
  padding: 1rem;
  background-color: #d5f4e6;
  border-radius: 4px;
  margin-bottom: 1rem;
}
</style>
