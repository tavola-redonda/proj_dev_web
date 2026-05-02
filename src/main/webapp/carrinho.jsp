<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Seu Carrinho</title>
</head>
<body>
    <h1>Itens no Pedido</h1>
    <table border="1">
        <tr>
            <th>Produto</th>
            <th>Preço Unit.</th>
            <th>Qtd</th>
            <th>Subtotal</th>
        </tr>
        <c:forEach var="item" items="${sessionScope.carrinho}">
            <tr>
                <td>${item.produto.nome}</td>
                <td><fmt:formatNumber value="${item.produto.preco}" type="currency"/></td>
                <td>${item.quantidade}</td>
                <td><fmt:formatNumber value="${item.subtotal}" type="currency"/></td>
            </tr>
        </c:forEach>
    </table>
    <br>
    <a href="cardapio">Continuar Comprando</a>
</body>
</html>