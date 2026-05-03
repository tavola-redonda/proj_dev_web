<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login - Loja de Comida</title>
</head>
<body>
    <h2>Identifique-se</h2>
    <p style="color:red;">${erro}</p>
    
    <form action="login" method="post">
        Email: <input type="email" name="email" required><br>
        Senha: <input type="password" name="senha" required><br>
        <button type="submit">Entrar</button>
    </form>
</body>
</html>