// Cliente de API (fetch)
;(function () {
  if (!window.App) {
    throw new Error('App não inicializado. Garanta que main.js foi carregado antes de utils/api.js')
  }

  // Constante para definir se será usado MOCK ou a API real
  const MOCK = true;

  function getQueryParam(name) {
    try {
      return new URLSearchParams(location.search).get(name)
    } catch {
      return null
    }
  }

  function getApiBaseUrl() {
    const fromQuery = getQueryParam('apiBase')
    if (fromQuery) return fromQuery.replace(/\/$/, '')

    try {
      const fromStorage = localStorage.getItem('apiBaseUrl')
      if (fromStorage) return fromStorage.replace(/\/$/, '')
    } catch {
      // ignore
    }

    // Mesmo host por padrão (quando front é servido pelo mesmo servidor do servlet)
    return ''
  }

  function normalizeErrorMessage(err) {
    if (!err) return 'Erro inesperado'
    if (typeof err === 'string') return err
    if (err instanceof Error && err.message) return err.message
    return 'Erro inesperado'
  }

  async function readJsonSafe(res) {
    try {
      return await res.json()
    } catch {
      return null
    }
  }

  async function apiFetch(path, options) {
    const baseUrl = getApiBaseUrl()
    const url = `${baseUrl}${path}`

    const res = await fetch(url, {
      ...options,
      headers: {
        Accept: 'application/json',
        ...(options && options.headers ? options.headers : {}),
      },
    })

    if (res.ok) {
      const json = await readJsonSafe(res)
      return json
    }

    const body = await readJsonSafe(res)
    const message = (body && body.message) || `HTTP ${res.status}`

    const error = new Error(message)
    error.status = res.status
    error.body = body
    throw error
  }

  async function login({ user, pass }) {
    if (MOCK) {
      if (window.App.mockApi && window.App.mockApi.login) {
        return window.App.mockApi.login({ user, pass })
      }
      console.warn('MOCK ativado mas window.App.mockApi.login não encontrado. Garanta que utils/mock.js foi carregado.')
    }

    const payload = {
      user: String(user || '').trim(),
      pass: String(pass || ''),
    }

    if (!payload.user || !payload.pass) {
      const error = new Error('Preencha usuário e senha')
      error.status = 400
      throw error
    }

    // Contrato esperado do backend: POST /api/login (JSON)
    return apiFetch('/api/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(payload),
    })
  }

  async function getCatalog() {
    if (MOCK) {
      if (window.App.mockApi && window.App.mockApi.getCatalog) {
        return window.App.mockApi.getCatalog()
      }
      console.warn('MOCK ativado mas window.App.mockApi.getCatalog não encontrado.')
    }

    // Contrato esperado do backend: GET /api/catalog (JSON)
    return apiFetch('/api/catalog', {
      method: 'GET',
    })
  }

  window.App.api = {
    login,
    getCatalog,
  }

  window.App.apiFetch = apiFetch
  window.App.getApiBaseUrl = getApiBaseUrl
  window.App.normalizeErrorMessage = normalizeErrorMessage
})()
