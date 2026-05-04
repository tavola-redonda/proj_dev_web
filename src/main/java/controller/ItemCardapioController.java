package controller;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import jakarta.servlet.RequestDispatcher;
import dao.ItemCardapioDAO;
import model.ItemCardapio;
import model.ItemCarrinho;
import java.util.List;
import java.util.ArrayList;



@WebServlet(urlPatterns = {"/cardapio","/main"})
public class ItemCardapioController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    ItemCardapioDAO dao = new ItemCardapioDAO();   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ItemCardapioController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	if (request.getSession().getAttribute("usuarioLogado") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Configuração de Cache HTTP exigida pelo projeto (Adição - Caio Bastos)
        response.setHeader("Cache-Control", "max-age=3600, public");
        response.setHeader("Pragma", "cache");
        response.setDateHeader("Expires", System.currentTimeMillis() + 3600000);
    	
        ItemCardapioDAO dao = new ItemCardapioDAO();
        List<ItemCardapio> lista = dao.listarProdutos();
        
        request.setAttribute("produtos", lista);
        System.out.println("DEBUG: Itens encontrados no banco: " + (lista != null ? lista.size() : "null"));

        RequestDispatcher rd = request.getRequestDispatcher("cardapio.jsp");
        rd.forward(request, response);
    }
    
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        if (session.getAttribute("usuarioLogado") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        List<ItemCarrinho> carrinho = (List<ItemCarrinho>) session.getAttribute("carrinho");
        if (carrinho == null) {
            carrinho = new ArrayList<>();
        }

        int idProduto = Integer.parseInt(request.getParameter("id"));
        boolean produtoJaExiste = false;

        for (ItemCarrinho item : carrinho) {
            if (item.getProduto().getId() == idProduto) {
                item.setQuantidade(item.getQuantidade() + 1);
                produtoJaExiste = true;
                break;
            }
        }

        if (!produtoJaExiste) {
            ItemCardapio p = dao.buscarPorId(idProduto);
            if (p != null) {
                carrinho.add(new ItemCarrinho(p, 1));
            }
        }

        session.setAttribute("carrinho", carrinho);
        
        
        double valorTotal = ItemCarrinho.calcularTotal(carrinho);
        session.setAttribute("totalPedido", valorTotal);
        response.sendRedirect("carrinho.jsp");
    }

}
