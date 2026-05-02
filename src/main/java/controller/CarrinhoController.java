package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import dao.ProdutoDAO;
import model.Produto;
import model.ItemCarrinho;

/**
 * Servlet implementation class CarrinhoController
 */
@WebServlet("/carrinho")
public class CarrinhoController extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acao = request.getParameter("acao");
        HttpSession sessao = request.getSession();
        
        // Recupera o carrinho da sessão ou cria um novo se não existir
        List<ItemCarrinho> carrinho = (List<ItemCarrinho>) sessao.getAttribute("carrinho");
        if (carrinho == null) {
            carrinho = new ArrayList<>();
            sessao.setAttribute("carrinho", carrinho);
        }

        if ("add".equals(acao)) {
            int id = Integer.parseInt(request.getParameter("id"));
            
            // Aqui você deve ter um método no seu DAO para buscar UM produto pelo ID
            ProdutoDAO dao = new ProdutoDAO();
            Produto p = dao.buscarPorId(id); 

            if (p != null) {
                // Lógica simples: adiciona como novo item (pode ser melhorado para somar quantidade)
                carrinho.add(new ItemCarrinho(p, 1));
            }
        }

        // Após adicionar, redireciona para a página do carrinho
        response.sendRedirect("carrinho.jsp");
    }
}