<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login - Loja de Comida</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="page">
        <header class="topbar">
            <div class="brand">Loja de Comida</div>
            <div class="nav-actions">
                <a class="button secondary" href="index.jsp">Voltar</a>
            </div>
        </header>

        <section class="hero fade-in" style="justify-items: start;">
            <div>
                <span class="badge">Acesso seguro</span>
                <h1>Identifique-se</h1>
                <p>Entre para ver o cardapio e montar seu pedido.</p>
            </div>
            <div class="form-card">
                <h2>Login</h2>
                <c:if test="${not empty erro}">
                    <div class="error">${erro}</div>
                </c:if>
                <form action="login" method="post">
                    <div class="field">
                        <label for="email">Email</label>
                        <input id="email" type="email" name="email" required>
                    </div>
                    <div class="field">
                        <label for="senha">Senha</label>
                        <input id="senha" type="password" name="senha" required>
                    </div>
                    <button class="button" type="submit">Entrar</button>
                </form>
            </div>
        </section>
    </div>
</body>
</html>