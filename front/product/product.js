App.onReady(() => {
  App.renderApp( /* html */`
    <section>
      <h1>A Casa do Frango</h1>
      <div id="product-loading">Carregando produto...</div>
      <div id="product-details" style="display: none;"></div>
      <p><a href="../catalogo/">Voltar ao Catálogo</a></p>
    </section>
  `)

  async function loadProduct() {
    try {
      const urlParams = new URLSearchParams(window.location.search)
      const id = urlParams.get('id')
      if (!id) throw new Error('ID do produto não informado')

      const res = await App.api.getProduct(id)
      const detailsEl = App.qs('#product-details')
      const loadingEl = App.qs('#product-loading')
      
      if (res && res.data) {
        const item = res.data
        detailsEl.innerHTML = /* html */`
          <div class="product-card">
            <h2>${App.escapeHtml(item.name)}</h2>
            <p class="type-badge">${App.escapeHtml(item.type)}</p>
            <p class="desc">${App.escapeHtml(item.description)}</p>
            <p class="price">R$ ${Number(item.price).toFixed(2).replace('.', ',')}</p>
          </div>
        `
      }
      
      if (loadingEl) loadingEl.style.display = 'none'
      if (detailsEl) detailsEl.style.display = 'block'
    } catch (err) {
      const loadingEl = App.qs('#product-loading')
      if (loadingEl) loadingEl.textContent = 'Erro ao carregar o produto: ' + App.normalizeErrorMessage(err)
    }
  }

  loadProduct()
})
