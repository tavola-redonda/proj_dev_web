;(function () {
  function qs(selector, root) {
    return (root || document).querySelector(selector)
  }

  function renderApp(html) {
    const app = qs('#app')
    if (!app) {
      throw new Error('Elemento #app não encontrado no HTML')
    }
    app.innerHTML = html
  }

  function onReady(fn) {
    if (document.readyState === 'loading') {
      document.addEventListener('DOMContentLoaded', fn)
      return
    }
    fn()
  }

  // API global simples para os scripts de cada página.
  window.App = {
    qs,
    renderApp,
    onReady,
  }
})()
