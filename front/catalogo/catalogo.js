App.onReady(() => {
  let user = { username: 'Usuário' }
  try {
    const storedUser = localStorage.getItem('authUser')
    if (storedUser) {
      user = JSON.parse(storedUser)
    }
  } catch {
    // ignore
  }

  const displayName = user.displayName || user.username
  
  // Se houver script utilitário de escapeHtml no projeto, use-o para prevenir XSS.
  // Caso não esteja disponível no momento da renderização, faremos um fallback simples.
  const safeName = (window.App && window.App.escapeHtml) 
    ? window.App.escapeHtml(displayName) 
    : displayName.replace(/</g, "&lt;").replace(/>/g, "&gt;")

  App.renderApp( /* html */`
    <section>
      <h1>Catálogo A Casa do Frango</h1>
      <p>Bem-vindo, ${safeName}!</p>
      <p><a href="#" id="logout-btn">Sair</a></p>
      <hr>
      <h2>Nosso Menu</h2>
      <div id="catalog-loading">Carregando pratos...</div>
      <div id="catalog-list" style="display: none;"></div>
    </section>
  `)

  const logoutBtn = App.qs('#logout-btn')
  if (logoutBtn) {
    logoutBtn.addEventListener('click', (e) => {
      e.preventDefault()
      try {
        localStorage.removeItem('authToken')
        localStorage.removeItem('authUser')
      } catch {
        // ignore
      }
      window.location.href = '../login/'
    })
  }

  async function loadCatalog() {
    try {
      const res = await App.api.getCatalog()
      const listEl = App.qs('#catalog-list')
      const loadingEl = App.qs('#catalog-loading')
      
      if (res && res.data) {
        // Agrupar itens por tipo
        const grouped = res.data.reduce((acc, item) => {
          if (!acc[item.type]) acc[item.type] = []
          acc[item.type].push(item)
          return acc
        }, {})

        let html = ''
        for (const type in grouped) {
          html += `
            <div class="carousel-section">
              <h3>${App.escapeHtml(type)}</h3>
              <div class="carousel">
                ${grouped[type].map(item => `
                  <a href="../product/?id=${item.id}" class="card" style="text-decoration: none; color: inherit;">
                    <h4>${App.escapeHtml(item.name)}</h4>
                    <p>ID: ${item.id}</p>
                  </a>
                `).join('')}
              </div>
            </div>
          `
        }

        listEl.innerHTML = html
      }
      
      if (loadingEl) loadingEl.style.display = 'none'
      if (listEl) listEl.style.display = 'block'
    } catch (err) {
      const loadingEl = App.qs('#catalog-loading')
      if (loadingEl) loadingEl.textContent = 'Erro ao carregar o catálogo: ' + App.normalizeErrorMessage(err)
    }
  }

  loadCatalog()
})
