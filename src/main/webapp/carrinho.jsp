<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
    <title>Carrinho - Casa do Frango</title>
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
                <a class="button secondary" href="cardapio">Continuar comprando</a>
                <a class="button ghost" href="checkout.jsp">Fechar compra</a>
            </div>
        </header>

        <section class="fade-in">
            <table class="table">
                <tr>
                    <th>Produto</th>
                    <th>Preco unit.</th>
                    <th>Qtd</th>
                    <th>Subtotal</th>
                    <th>Acao</th>
                </tr>
                <c:forEach var="item" items="${sessionScope.carrinho}">
                    <tr>
                        <td>${item.produto.nome}</td>
                        <td>R$ ${item.produto.preco}</td>
                        <td>${item.quantidade}</td>
                        <td>${item.subtotalFormatado}</td>
                        <td>
                            <form action="Carrinho" method="post">
                                <input type="hidden" name="acao" value="remove">
                                <input type="hidden" name="id" value="${item.produto.id}">
                                <button class="button secondary" type="submit">Remover</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>

<%
    List<model.ItemCarrinho> listaItens = (List<model.ItemCarrinho>) session.getAttribute("carrinho");
    double somaTotal = 0;
    if (listaItens != null) {
        for (model.ItemCarrinho i : listaItens) {
            somaTotal += i.getSubtotal();
        }
    }
    pageContext.setAttribute("totalCalculadoNoJsp", somaTotal);
%>

            <div class="summary">
                <strong>Total do pedido</strong>
                <span class="price">R$ ${String.format("%.2f", totalCalculadoNoJsp)}</span>
            </div>
        </section>
    </div>

</body>
</html>