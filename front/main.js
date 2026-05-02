const app = document.querySelector('#app')
if (!app) {
  throw new Error('Elemento #app não encontrado no HTML')
}

app.innerHTML = `
<section>
  <div>Front pronto galera</div>
</section>
`
