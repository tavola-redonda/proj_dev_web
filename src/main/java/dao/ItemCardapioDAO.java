package dao;

import java.sql.Connection;
import java.sql.Statement;
import model.ItemCardapio;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

public class ItemCardapioDAO {

	/**
	 * Criar novo item no cardápio
	 * @param item ItemCardapio object with nome, descricao, preco, ativo
	 * @return true if successful, false otherwise
	 */
	public boolean create(ItemCardapio item) {
	    String sql = "INSERT INTO itens_cardapio (nome, descricao, preco, ativo) VALUES (?, ?, ?, ?)";
	    
	    try (Connection con = ConnectionFactory.getConnection();
	         PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	        
	        pst.setString(1, item.getNome());
	        pst.setString(2, item.getDescricao());
	        pst.setDouble(3, item.getPreco());
	        pst.setBoolean(4, item.getAtivo() != null ? item.getAtivo() : true);
	        
	        int rowsAffected = pst.executeUpdate();
	        
	        if (rowsAffected > 0) {
	            ResultSet rs = pst.getGeneratedKeys();
	            if (rs.next()) {
	                item.setId(rs.getInt(1));
	                System.out.println("✓ Item criado com sucesso. ID: " + item.getId() + " - " + item.getNome());
	                return true;
	            }
	        }
	    } catch (Exception e) {
	        System.err.println("✗ Erro ao criar item: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return false;
	}
	
	/**
	 * Atualizar item do cardápio
	 * @param item ItemCardapio object with updated data
	 * @return true if successful, false otherwise
	 */
	public boolean update(ItemCardapio item) {
	    String sql = "UPDATE itens_cardapio SET nome = ?, descricao = ?, preco = ?, ativo = ? WHERE id = ?";
	    
	    try (Connection con = ConnectionFactory.getConnection();
	         PreparedStatement pst = con.prepareStatement(sql)) {
	        
	        pst.setString(1, item.getNome());
	        pst.setString(2, item.getDescricao());
	        pst.setDouble(3, item.getPreco());
	        pst.setBoolean(4, item.getAtivo() != null ? item.getAtivo() : true);
	        pst.setInt(5, item.getId());
	        
	        int rowsAffected = pst.executeUpdate();
	        
	        if (rowsAffected > 0) {
	            System.out.println("✓ Item atualizado com sucesso. ID: " + item.getId());
	            return true;
	        } else {
	            System.out.println("✗ Nenhum item encontrado com ID: " + item.getId());
	        }
	    } catch (Exception e) {
	        System.err.println("✗ Erro ao atualizar item: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return false;
	}
	
	/**
	 * Deletar item do cardápio por ID
	 * @param id Item ID
	 * @return true if successful, false otherwise
	 */
	public boolean delete(int id) {
	    String sql = "DELETE FROM itens_cardapio WHERE id = ?";
	    
	    try (Connection con = ConnectionFactory.getConnection();
	         PreparedStatement pst = con.prepareStatement(sql)) {
	        
	        pst.setInt(1, id);
	        int rowsAffected = pst.executeUpdate();
	        
	        if (rowsAffected > 0) {
	            System.out.println("✓ Item deletado com sucesso. ID: " + id);
	            return true;
	        } else {
	            System.out.println("✗ Nenhum item encontrado com ID: " + id);
	        }
	    } catch (Exception e) {
	        System.err.println("✗ Erro ao deletar item: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return false;
	}

	/**
	 * Listar todos itens ativos do cardápio
	 * @return List of active ItemCardapio
	 */
	public List<ItemCardapio> listarProdutos() {
        List<ItemCardapio> itemCardapios = new ArrayList<>();
        String sql = "SELECT * FROM itens_cardapio WHERE ativo = true";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                itemCardapios.add(mapRowToItem(rs));
            }
            System.out.println("✓ " + itemCardapios.size() + " produtos ativos carregados");
        } catch (Exception e) {
            System.err.println("✗ Erro ao listar produtos: " + e.getMessage());
        }
        return itemCardapios;
    }
	
	/**
	 * Procurar item do cardápio por ID
	 * @param id Item ID
	 * @return ItemCardapio object or null if not found
	 */
	public ItemCardapio buscarPorId(int id) {
	    String sql = "SELECT * FROM itens_cardapio WHERE id = ?";
	    try (Connection con = ConnectionFactory.getConnection();
	         PreparedStatement pst = con.prepareStatement(sql)) {
	        pst.setInt(1, id);
	        ResultSet rs = pst.executeQuery();
	        if (rs.next()) {
	            return mapRowToItem(rs);
	        }
	    } catch (Exception e) {
	        System.err.println("✗ Erro ao buscar item por ID: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return null;
	}
	
	/**
	 * Retornar todos itens do cardápio (ativos e inativos)
	 * @return List of all ItemCardapio
	 */
	public List<ItemCardapio> findAll() {
	    List<ItemCardapio> items = new ArrayList<>();
	    String sql = "SELECT * FROM itens_cardapio";
	    
	    try (Connection con = ConnectionFactory.getConnection();
	         PreparedStatement pst = con.prepareStatement(sql);
	         ResultSet rs = pst.executeQuery()) {
	        
	        while (rs.next()) {
	            items.add(mapRowToItem(rs));
	        }
	        System.out.println("✓ " + items.size() + " itens recuperados do cardápio");
	    } catch (Exception e) {
	        System.err.println("✗ Erro ao listar todos os itens: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return items;
	}
	
	/**
	 * Método auxiliar para mapear uma linha do ResultSet para um objeto ItemCardapio
	 */
	private ItemCardapio mapRowToItem(ResultSet rs) throws Exception {
	    ItemCardapio p = new ItemCardapio();
	    p.setId(rs.getInt("id"));
	    p.setNome(rs.getString("nome"));
	    p.setDescricao(rs.getString("descricao"));
	    p.setPreco(rs.getDouble("preco"));
	    p.setAtivo(rs.getBoolean("ativo"));
	    return p;
	}
}
