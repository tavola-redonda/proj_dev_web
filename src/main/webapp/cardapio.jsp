<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Cardápio - Loja de Comida</title>
</head>
<body>
    <h1>Nosso Cardápio</h1>
    
    <div class="container">
        <c:forEach var="p" items="${produtos}">
            <div class="card">
                <h3>${p.nome}</h3>
                <p>${p.descricao}</p>
                <span>R$ ${p.preco}</span>
                <a href="carrinho?acao=add&id=${p.id}">
               		<button type="button">Adicionar ao Carrinho</button>
				</a>
            </div>
        </c:forEach>
        
        <p>Quantidade de produtos encontrados: ${produtos.size()}</p>

<c:if test="${empty produtos}">
    <p style="color:red;">Atenção: A lista de produtos está vazia!</p>
</c:if>
    </div>
</body>
</html>