// Util simples para escapar texto em HTML
// Disponível como App.escapeHtml(text)
;(function () {
  if (!window.App) {
    throw new Error('App não inicializado. Garanta que main.js foi carregado antes de utils/escapeHtml.js')
  }

  window.App.escapeHtml = function escapeHtml(text) {
    return String(text)
      .replaceAll('&', '&amp;')
      .replaceAll('<', '&lt;')
      .replaceAll('>', '&gt;')
      .replaceAll('"', '&quot;')
      .replaceAll("'", '&#39;')
  }
})()
