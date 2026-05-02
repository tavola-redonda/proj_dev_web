App.onReady(() => {
  App.renderApp( /* html */`
    <section>
      <h1>Login</h1>
      <form id="login-form">
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
  form.addEventListener('submit', (e) => {
    e.preventDefault()

    const data = new FormData(form)
    const user = String(data.get('user') || '')

    // Placeholder (sem backend): só confirma na tela
    App.renderApp( /* html */`
      <section>
        <h1>Login</h1>
        <p>Bem-vindo, ${App.escapeHtml(user)}!</p>
        <p><a href="../home/">Ir para Home</a></p>
      </section>
    `)
  })
})

