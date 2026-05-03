<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Bem-vindo à Loja de Comida</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="page">
        <header class="topbar">
            <div class="brand">Loja de Comida</div>
            <div class="nav-actions">
                <a class="button secondary" href="logout">Sair</a>
            </div>
        </header>

        <section class="hero fade-in">
            <div>
                <h1>Bem-vindo a sua mesa digital</h1>
                <p>Escolha pratos frescos, monte seu pedido e finalize com poucos cliques.</p>
                <div class="nav-actions" style="margin-top: 16px;">
                    <a class="button" href="cardapio">Criar pedido</a>
                    <a class="button ghost" href="login.jsp">Trocar usuario</a>
                </div>
            </div>
            <div>
                <span class="badge">Pedidos rapidos</span>
                <span class="badge" style="margin-left: 8px;">Entrega simples</span>
            </div>
        </section>
    </div>
</body>
</html>