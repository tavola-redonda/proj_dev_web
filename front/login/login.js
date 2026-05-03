App.onReady(() => {
  App.renderApp( /* html */`
    <section>
      <h1>Login</h1>
      <form id="login-form">
        <p id="login-error" role="alert" hidden></p>
        <p>
          <label>
            Usuário
            <input name="user" autocomplete="username" required />
          </label>
        </p>
        <p>
          <label>
            Senha
            <input name="pass" type="password" autocomplete="current-password" required />
          </label>
        </p>
        <p>
          <button type="submit">Entrar</button>
        </p>
      </form>
      <p><a href="../home/">Voltar para Home</a></p>
    </section>
  `)

  const form = App.qs('#login-form')
  const errorEl = App.qs('#login-error')
  const submitBtn = App.qs('button[type="submit"]', form)

  function setError(message) {
    if (!errorEl) return
    const msg = String(message || '').trim()
    if (!msg) {
      errorEl.hidden = true
      errorEl.textContent = ''
      return
    }
    errorEl.hidden = false
    errorEl.textContent = msg
  }

  form.addEventListener('submit', (e) => {
    e.preventDefault()

    setError('')

    const data = new FormData(form)
    const user = String(data.get('user') || '').trim()
    const pass = String(data.get('pass') || '')

    const run = async () => {
      if (submitBtn) submitBtn.disabled = true

      try {
        const res = await App.api.login({ user, pass })

        try {
          localStorage.setItem('authToken', String(res && res.token ? res.token : ''))
          localStorage.setItem('authUser', JSON.stringify(res && res.user ? res.user : { username: user }))
        } catch {
          // ignore (Storage pode estar bloqueado)
        }

        const displayName = (res && res.user && res.user.displayName) || user

        App.renderApp( /* html */`
          <section>
            <h1>Login</h1>
            <p>Bem-vindo, ${App.escapeHtml(displayName)}!</p>
            <p><a href="../home/">Ir para Home</a></p>
          </section>
        `)
      } catch (err) {
        const message = App.normalizeErrorMessage(err)
        setError(message)
      } finally {
        if (submitBtn) submitBtn.disabled = false
      }
    }

    run()
  })
})

