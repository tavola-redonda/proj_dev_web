// Mocks para desenvolvimento
;(function () {
  if (!window.App) {
    throw new Error('App não inicializado. Garanta que main.js foi carregado antes de utils/mock.js')
  }

  function delay(ms) {
    return new Promise((resolve) => setTimeout(resolve, ms))
  }

  async function mockLogin({ user, pass }) {
    await delay(350)

    const username = String(user || '').trim()
    const password = String(pass || '')

    // Mock simples: aceita admin/admin ou qualquer usuário com senha "123"
    const ok = (username === 'admin' && password === 'admin') || password === '123'

    if (!ok) {
      const error = new Error('Usuário ou senha inválidos (mock)')
      error.status = 401
      throw error
    }

    return {
      ok: true,
      token: `mock-token-${Math.random().toString(16).slice(2)}`,
      user: {
        username,
        displayName: username,
      },
    }
  }

  window.App.mockApi = {
    login: mockLogin,
  }
})()
