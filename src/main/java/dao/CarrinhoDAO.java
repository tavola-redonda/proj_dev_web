package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.ItemCarrinho;


public class CarrinhoDAO {

	/**
	 * Add item to cart or update quantity if already exists
	 * @param itemCarrinho ItemCarrinho with usuarioId, itemCardapioId, quantidade
	 * @return true if successful, false otherwise
	 */
	public boolean addItem(ItemCarrinho itemCarrinho) {
	    String sqlCheck = "SELECT id FROM carrinhos WHERE usuario_id = ? AND item_cardapio_id = ?";
	    String sqlInsert = "INSERT INTO carrinhos (usuario_id, item_cardapio_id, quantidade) VALUES (?, ?, ?)";
	    String sqlUpdate = "UPDATE carrinhos SET quantidade = quantidade + ? WHERE usuario_id = ? AND item_cardapio_id = ?";
	    
	    try (Connection con = ConnectionFactory.getConnection()) {
	        
	        // Consultar se o item já existe no carrinho do usuário
	        try (PreparedStatement pstCheck = con.prepareStatement(sqlCheck)) {
	            pstCheck.setInt(1, itemCarrinho.getUsuarioId());
	            pstCheck.setInt(2, itemCarrinho.getItemCardapioId());
	            ResultSet rs = pstCheck.executeQuery();
	            
	            if (rs.next()) {
	                // Se item já existe, atualizar a quantidade
	                try (PreparedStatement pstUpdate = con.prepareStatement(sqlUpdate)) {
	                    pstUpdate.setInt(1, itemCarrinho.getQuantidade());
	                    pstUpdate.setInt(2, itemCarrinho.getUsuarioId());
	                    pstUpdate.setInt(3, itemCarrinho.getItemCardapioId());
	                    
	                    int rowsAffected = pstUpdate.executeUpdate();
	                    if (rowsAffected > 0) {
	                        System.out.println("✓ Quantidade do item atualizada no carrinho");
	                        return true;
	                    }
	                }
	            } else {
	                // Se item não existe, inserir novo registro
	                try (PreparedStatement pstInsert = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
	                    pstInsert.setInt(1, itemCarrinho.getUsuarioId());
	                    pstInsert.setInt(2, itemCarrinho.getItemCardapioId());
	                    pstInsert.setInt(3, itemCarrinho.getQuantidade());
	                    
	                    int rowsAffected = pstInsert.executeUpdate();
	                    if (rowsAffected > 0) {
	                        ResultSet rsKeys = pstInsert.getGeneratedKeys();
	                        if (rsKeys.next()) {
	                            itemCarrinho.setId(rsKeys.getInt(1));
	                            System.out.println("✓ Item adicionado ao carrinho. ID: " + itemCarrinho.getId());
	                            return true;
	                        }
	                    }
	                }
	            }
	        }
	    } catch (Exception e) {
	        System.err.println("✗ Erro ao adicionar item ao carrinho: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return false;
	}
	
	/**
	 * Remover item do carrinho
	 * @param usuarioId User ID
	 * @param itemCardapioId Menu item ID
	 * @return true if successful, false otherwise
	 */
	public boolean removeItem(int usuarioId, int itemCardapioId) {
	    String sql = "DELETE FROM carrinhos WHERE usuario_id = ? AND item_cardapio_id = ?";
	    
	    try (Connection con = ConnectionFactory.getConnection();
	         PreparedStatement pst = con.prepareStatement(sql)) {
	        
	        pst.setInt(1, usuarioId);
	        pst.setInt(2, itemCardapioId);
	        
	        int rowsAffected = pst.executeUpdate();
	        
	        if (rowsAffected > 0) {
	            System.out.println("✓ Item removido do carrinho");
	            return true;
	        }
	    } catch (Exception e) {
	        System.err.println("✗ Erro ao remover item do carrinho: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return false;
	}
	
	/**
	 * Listar todos itens no carrinho do usuário
	 * @param usuarioId User ID
	 * @return List of ItemCarrinho objects in the cart
	 */
	public List<ItemCarrinho> findByUsuarioId(int usuarioId) {
	    List<ItemCarrinho> carrinho = new ArrayList<>();
	    String sql = "SELECT * FROM carrinhos WHERE usuario_id = ?";
	    
	    try (Connection con = ConnectionFactory.getConnection();
	         PreparedStatement pst = con.prepareStatement(sql)) {
	        
	        pst.setInt(1, usuarioId);
	        ResultSet rs = pst.executeQuery();
	        
	        while (rs.next()) {
	            carrinho.add(mapRowToItemCarrinho(rs));
	        }
	        System.out.println("✓ " + carrinho.size() + " itens recuperados do carrinho do usuário " + usuarioId);
	    } catch (Exception e) {
	        System.err.println("✗ Erro ao listar carrinho: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return carrinho;
	}
	
	/**
	 * Listar todos itens de todos os carrinhos de todos usuários
	 * @return List of all ItemCarrinho objects
	 */
	public List<ItemCarrinho> findAll() {
	    List<ItemCarrinho> itens = new ArrayList<>();
	    String sql = "SELECT * FROM carrinhos";
	    
	    try (Connection con = ConnectionFactory.getConnection();
	         PreparedStatement pst = con.prepareStatement(sql);
	         ResultSet rs = pst.executeQuery()) {
	        
	        while (rs.next()) {
	            itens.add(mapRowToItemCarrinho(rs));
	        }
	        System.out.println("✓ " + itens.size() + " itens de carrinho recuperados");
	    } catch (Exception e) {
	        System.err.println("✗ Erro ao listar todos os carrinhos: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return itens;
	}
	
	/**
	 * Esvaziar carrinho inteiro de um usuário
	 * @param usuarioId User ID
	 * @return true if successful, false otherwise
	 */
	public boolean clearCart(int usuarioId) {
	    String sql = "DELETE FROM carrinhos WHERE usuario_id = ?";
	    
	    try (Connection con = ConnectionFactory.getConnection();
	         PreparedStatement pst = con.prepareStatement(sql)) {
	        
	        pst.setInt(1, usuarioId);
	        int rowsAffected = pst.executeUpdate();
	        
	        if (rowsAffected > 0) {
	            System.out.println("✓ Carrinho esvaziado para usuário: " + usuarioId);
	        }
	        return true;
	    } catch (Exception e) {
	        System.err.println("✗ Erro ao limpar carrinho: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return false;
	}
	
	/**
	 * Calcular total do carrinho para um usuário
	 * @param usuarioId User ID
	 * @param precoMap Map with item ID and price
	 * @return Total price
	 */
	public double getTotal(int usuarioId) {
	    String sql = "SELECT SUM(c.quantidade * ic.preco) as total FROM carrinhos c " +
	                  "JOIN itens_cardapio ic ON c.item_cardapio_id = ic.id WHERE c.usuario_id = ?";
	    
	    try (Connection con = ConnectionFactory.getConnection();
	         PreparedStatement pst = con.prepareStatement(sql)) {
	        
	        pst.setInt(1, usuarioId);
	        ResultSet rs = pst.executeQuery();
	        
	        if (rs.next()) {
	            double total = rs.getDouble("total");
	            System.out.println("✓ Total do carrinho: R$ " + String.format("%.2f", total));
	            return total;
	        }
	    } catch (Exception e) {
	        System.err.println("✗ Erro ao calcular total do carrinho: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return 0.0;
	}
	
	/**
	 * Atualizar quantidade de um item no carrinho
	 * @param usuarioId User ID
	 * @param itemCardapioId Menu item ID
	 * @param novaQuantidade New quantity
	 * @return true if successful, false otherwise
	 */
	public boolean updateQuantidade(int usuarioId, int itemCardapioId, int novaQuantidade) {
	    if (novaQuantidade <= 0) {
	        return removeItem(usuarioId, itemCardapioId);
	    }
	    
	    String sql = "UPDATE carrinhos SET quantidade = ? WHERE usuario_id = ? AND item_cardapio_id = ?";
	    
	    try (Connection con = ConnectionFactory.getConnection();
	         PreparedStatement pst = con.prepareStatement(sql)) {
	        
	        pst.setInt(1, novaQuantidade);
	        pst.setInt(2, usuarioId);
	        pst.setInt(3, itemCardapioId);
	        
	        int rowsAffected = pst.executeUpdate();
	        
	        if (rowsAffected > 0) {
	            System.out.println("✓ Quantidade atualizada no carrinho");
	            return true;
	        }
	    } catch (Exception e) {
	        System.err.println("✗ Erro ao atualizar quantidade: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return false;
	}
	
	/**
	 * Método auxiliar para mapear uma linha do ResultSet para um objeto ItemCarrinho
	 */
	private ItemCarrinho mapRowToItemCarrinho(ResultSet rs) throws Exception {
	    ItemCarrinho ic = new ItemCarrinho();
	    ic.setId(rs.getInt("id"));
	    ic.setUsuarioId(rs.getInt("usuario_id"));
	    ic.setItemCardapioId(rs.getInt("item_cardapio_id"));
	    ic.setQuantidade(rs.getInt("quantidade"));
	    return ic;
	}
}
