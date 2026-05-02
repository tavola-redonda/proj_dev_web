package dao;

import java.sql.Connection;
import dao.ConnectionFactory;
import java.sql.DriverManager;
import model.Produto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

public class ProdutoDAO {

	public List<Produto> listarProdutos() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produtos";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Produto p = new Produto();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setDescricao(rs.getString("descricao"));
                p.setPreco(rs.getDouble("preco"));
                p.setImagem_url(rs.getString("imagem_url"));
                
                
                System.out.println("Produto carregado do banco: " + p.getNome());
                produtos.add(p);
            }
        } catch (Exception e) {
            System.err.println("Erro ao listar produtos: " + e.getMessage());
        }
        return produtos;
    }
	
	public Produto buscarPorId(int id) {
	    String sql = "SELECT * FROM produtos WHERE id = ?";
	    try (Connection con = ConnectionFactory.getConnection();
	         PreparedStatement pst = con.prepareStatement(sql)) {
	        pst.setInt(1, id);
	        ResultSet rs = pst.executeQuery();
	        if (rs.next()) {
	            Produto p = new Produto();
	            p.setId(rs.getInt("id"));
	            p.setNome(rs.getString("nome"));
	            p.setPreco(rs.getDouble("preco"));
                p.setDescricao(rs.getString("descricao"));
                p.setPreco(rs.getDouble("preco"));
                p.setImagem_url(rs.getString("imagem_url"));
	            return p;
	        }
	    } catch (Exception e) { e.printStackTrace(); }
	    return null;
	}
}
