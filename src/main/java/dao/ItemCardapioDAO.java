package dao;

import java.sql.Connection;
import dao.ConnectionFactory;
import java.sql.DriverManager;
import model.ItemCardapio;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

public class ItemCardapioDAO {

	public List<ItemCardapio> listarProdutos() {
        List<ItemCardapio> itemCardapios = new ArrayList<>();
        String sql = "SELECT * FROM produtos";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                ItemCardapio p = new ItemCardapio();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setDescricao(rs.getString("descricao"));
                p.setPreco(rs.getDouble("preco"));
                p.setAtivo(rs.getBoolean("ativo"));
                
                
                System.out.println("Produto carregado do banco: " + p.getNome());
                itemCardapios.add(p);
            }
        } catch (Exception e) {
            System.err.println("Erro ao listar produtos: " + e.getMessage());
        }
        return itemCardapios;
    }
	
	public ItemCardapio buscarPorId(int id) {
	    String sql = "SELECT * FROM produtos WHERE id = ?";
	    try (Connection con = ConnectionFactory.getConnection();
	         PreparedStatement pst = con.prepareStatement(sql)) {
	        pst.setInt(1, id);
	        ResultSet rs = pst.executeQuery();
	        if (rs.next()) {
	            ItemCardapio p = new ItemCardapio();
	            p.setId(rs.getInt("id"));
	            p.setNome(rs.getString("nome"));
	            p.setPreco(rs.getDouble("preco"));
                p.setDescricao(rs.getString("descricao"));
                p.setPreco(rs.getDouble("preco"));
                p.setAtivo(rs.getBoolean("ativo"));

	            return p;
	        }
	    } catch (Exception e) { e.printStackTrace(); }
	    return null;
	}
}
