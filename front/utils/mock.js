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

  async function mockGetCatalog() {
    await delay(300)
    return {
      ok: true,
      data: [
        { id: 1, name: 'Frango Assado Inteiro', type: 'Prato Principal' },
        { id: 2, name: 'Frango à Passarinho', type: 'Petisco' },
        { id: 3, name: 'Strogonoff de Frango', type: 'Prato Principal' },
        { id: 4, name: 'Coxinha de Frango', type: 'Salgado' },
        { id: 5, name: 'Salpicão de Frango', type: 'Acompanhamento' }
      ]
    }
  }

  async function mockGetProduct(id) {
    await delay(300)
    const db = [
      { id: 1, name: 'Frango Assado Inteiro', type: 'Prato Principal', description: 'Um delicioso frango assado inteiro, marinado com ervas finas.', price: 45.90 },
      { id: 2, name: 'Frango à Passarinho', type: 'Petisco', description: 'Porção de frango à passarinho crocante com alho frito.', price: 32.50 },
      { id: 3, name: 'Strogonoff de Frango', type: 'Prato Principal', description: 'Strogonoff cremoso acompanhado de arroz branco e batata palha.', price: 28.90 },
      { id: 4, name: 'Coxinha de Frango', type: 'Salgado', description: 'Porção com 6 minicoxinhas recheadas com frango desfiado e catupiry.', price: 18.00 },
      { id: 5, name: 'Salpicão de Frango', type: 'Acompanhamento', description: 'Salpicão tradicional com frango desfiado, cenoura, milho e batata palha.', price: 22.00 }
    ]
    const product = db.find(p => p.id === Number(id))
    if (!product) {
      const error = new Error('Produto não encontrado')
      error.status = 404
      throw error
    }
    return { ok: true, data: product }
  }

  window.App.mockApi = {
    login: mockLogin,
    getCatalog: mockGetCatalog,
    getProduct: mockGetProduct,
  }
})()
