App.onReady(() => {
  App.renderApp( /* html */`
    <section>
      <h1>Seu Carrinho</h1>
      <div id="cart-content"></div>
      <div id="checkout-status" style="margin-top: 15px; color: green; font-weight: bold; display: none;"></div>
      <p style="margin-top: 20px;"><a href="../catalogo/">Voltar ao Catálogo</a></p>
    </section>
  `)

  function loadCart() {
    const cartStr = localStorage.getItem('cart')
    const contentEl = App.qs('#cart-content')

    if (!cartStr) {
      contentEl.innerHTML = '<p>Seu carrinho está vazio.</p>'
      return
    }

    try {
      const cart = JSON.parse(cartStr)
      if (!Array.isArray(cart) || cart.length === 0) {
        contentEl.innerHTML = '<p>Seu carrinho está vazio.</p>'
        return
      }

      let total = 0
      let html = '<ul style="list-style: none; padding: 0;">'
      for (const item of cart) {
        total += Number(item.price)
        html += /* html */`
          <li style="border-bottom: 1px solid #ccc; padding: 10px 0; display: flex; justify-content: space-between;">
            <span>${App.escapeHtml(item.name)}</span>
            <span>R$ ${Number(item.price).toFixed(2).replace('.', ',')}</span>
          </li>
        `
      }
      html += /* html */`
        <li style="padding: 10px 0; font-weight: bold; display: flex; justify-content: space-between;">
          <span>Total</span>
          <span>R$ ${total.toFixed(2).replace('.', ',')}</span>
        </li>
      `
      html += '</ul>'
      
      html += /* html */`
        <button id="btn-checkout" style="margin-top: 15px; padding: 12px 24px; font-weight: bold; background: #28a745; color: white; border: none; border-radius: 4px; cursor: pointer;">
          Efetuar Pagamento
        </button>
      `

      contentEl.innerHTML = html

      const btnCheckout = App.qs('#btn-checkout')
      if (btnCheckout) {
        btnCheckout.addEventListener('click', async () => {
          btnCheckout.disabled = true
          btnCheckout.textContent = 'Processando...'
          const statusEl = App.qs('#checkout-status')
          
          try {
            const res = await App.api.checkout(cart)
            if (res && res.ok) {
              if (statusEl) {
                statusEl.textContent = 'Pagamento efetuado com sucesso! Redirecionando...'
                statusEl.style.display = 'block'
              }
              localStorage.removeItem('cart') // Limpa o carrinho
              
              // Redireciona para o catálogo após 2 segundos
              setTimeout(() => {
                window.location.href = '../catalogo/'
              }, 2000)
            }
          } catch (err) {
            btnCheckout.disabled = false
            btnCheckout.textContent = 'Efetuar Pagamento'
            alert('Erro no pagamento: ' + App.normalizeErrorMessage(err))
          }
        })
      }

    } catch (err) {
      contentEl.innerHTML = '<p>Erro ao carregar o carrinho.</p>'
    }
  }

  loadCart()
})
