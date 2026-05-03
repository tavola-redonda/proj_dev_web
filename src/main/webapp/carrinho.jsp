<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
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
				<td>R$ ${item.produto.preco}</td>
				<td>${item.quantidade}</td>
				<td>${item.subtotalFormatado}</td>
            </tr>
        </c:forEach>
    </table>
    <br>
    
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

<h3>Total do Pedido: R$ ${String.format("%.2f", totalCalculadoNoJsp)}</h3>
    
    
    <a href="cardapio">Continuar Comprando</a>
    <a href="index.jsp">Fechar compra</a>

</body>
</html>