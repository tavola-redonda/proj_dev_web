package controller;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import jakarta.servlet.RequestDispatcher;
import dao.ItemCardapioDAO;
import model.ItemCardapio;
import java.util.List;



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
    	
    	
        ItemCardapioDAO dao = new ItemCardapioDAO();
        List<ItemCardapio> lista = dao.listarProdutos();
        
        request.setAttribute("produtos", lista);
        System.out.println("DEBUG: Itens encontrados no banco: " + (lista != null ? lista.size() : "null"));

        RequestDispatcher rd = request.getRequestDispatcher("cardapio.jsp");
        rd.forward(request, response);
    }

}
