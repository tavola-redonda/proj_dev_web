<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Cardapio - Casa do Frango</title>
<link rel="stylesheet" href="styles.css">
</head>
<body>
    <%
        User usuarioLogado = (User) session.getAttribute("usuarioLogado");
        String nomeUsuario = usuarioLogado != null ? usuarioLogado.getNome() : null;
    %>
    <div class="page">
        <header class="topbar">
            <div class="brand">Casa do Frango</div>
            <div class="nav-actions">
                <% if (nomeUsuario != null) { %>
                    <div class="user-chip">
                        <span>Pedido de</span>
                        <strong><%= nomeUsuario %></strong>
                    </div>
                    <a class="button secondary" href="logout">Sair</a>
                <% } else { %>
                    <a class="button secondary" href="login.jsp">Login</a>
                    <a class="button ghost" href="cadastro">Sign in</a>
                <% } %>
                <a class="button ghost" href="Carrinho">Ver carrinho</a>
                <a class="button secondary" href="index.jsp">Inicio</a>
            </div>
        </header>

        <section class="fade-in">
            <div class="badge">${produtos.size()} itens disponiveis</div>
            <div class="grid">
                <c:forEach var="p" items="${produtos}">
                    <div class="card">
                        <h3>${p.nome}</h3>
                        <p>${p.descricao}</p>
                        <span class="price">R$ ${p.preco}</span>
                        <form action="cardapio" method="POST">
                            <input type="hidden" name="id" value="${p.id}">
                            <button class="button" type="submit">Adicionar ao carrinho</button>
                        </form>
                    </div>
                </c:forEach>
            </div>

            <c:if test="${empty produtos}">
                <div class="error" style="margin-top: 16px;">A lista de produtos esta vazia.</div>
            </c:if>
        </section>
    </div>
</body>
</html>