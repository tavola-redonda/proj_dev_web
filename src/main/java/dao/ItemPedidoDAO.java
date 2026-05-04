package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.ItemPedido;


public class ItemPedidoDAO {

	/**
	 * Criar um item do pedido
	 * @param itemPedido ItemPedido object with pedidoId, itemCardapioId, quantidade, precoUnitario
	 * @return true if successful, false otherwise
	 */
	public boolean create(ItemPedido itemPedido) {
	    String sql = "INSERT INTO itens_pedidos (pedido_id, item_cardapio_id, quantidade, preco_unitario) VALUES (?, ?, ?, ?)";
	    
	    try (Connection con = ConnectionFactory.getConnection();
	         PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	        
	        pst.setInt(1, itemPedido.getPedidoId());
	        pst.setInt(2, itemPedido.getItemCardapioId());
	        pst.setInt(3, itemPedido.getQuantidade());
	        pst.setDouble(4, itemPedido.getPrecoUnitario());
	        
	        int rowsAffected = pst.executeUpdate();
	        
	        if (rowsAffected > 0) {
	            ResultSet rs = pst.getGeneratedKeys();
	            if (rs.next()) {
	                itemPedido.setId(rs.getInt(1));
	                System.out.println("✓ Item adicionado ao pedido. Item ID: " + itemPedido.getId());
	                return true;
	            }
	        }
	    } catch (Exception e) {
	        System.err.println("✗ Erro ao criar item do pedido: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return false;
	}
	
	/**
	 * Procurar item do pedido por ID
	 * @param id Item Pedido ID
	 * @return ItemPedido object or null if not found
	 */
	public ItemPedido findById(int id) {
	    String sql = "SELECT * FROM itens_pedidos WHERE id = ?";
	    
	    try (Connection con = ConnectionFactory.getConnection();
	         PreparedStatement pst = con.prepareStatement(sql)) {
	        
	        pst.setInt(1, id);
	        ResultSet rs = pst.executeQuery();
	        
	        if (rs.next()) {
	            return mapRowToItemPedido(rs);
	        }
	    } catch (Exception e) {
	        System.err.println("✗ Erro ao buscar item do pedido por ID: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return null;
	}
	
	/**
	 * Listar todos itens de um pedido específico
	 * @param pedidoId Order ID
	 * @return List of ItemPedido objects for the order
	 */
	public List<ItemPedido> findByPedidoId(int pedidoId) {
	    List<ItemPedido> itens = new ArrayList<>();
	    String sql = "SELECT * FROM itens_pedidos WHERE pedido_id = ?";
	    
	    try (Connection con = ConnectionFactory.getConnection();
	         PreparedStatement pst = con.prepareStatement(sql)) {
	        
	        pst.setInt(1, pedidoId);
	        ResultSet rs = pst.executeQuery();
	        
	        while (rs.next()) {
	            itens.add(mapRowToItemPedido(rs));
	        }
	        System.out.println("✓ " + itens.size() + " itens encontrados para pedido: " + pedidoId);
	    } catch (Exception e) {
	        System.err.println("✗ Erro ao listar itens do pedido: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return itens;
	}
	
	/**
	 * Listar todos itens_pedidos
	 * @return List of all ItemPedido objects
	 */
	public List<ItemPedido> findAll() {
	    List<ItemPedido> itens = new ArrayList<>();
	    String sql = "SELECT * FROM itens_pedidos";
	    
	    try (Connection con = ConnectionFactory.getConnection();
	         PreparedStatement pst = con.prepareStatement(sql);
	         ResultSet rs = pst.executeQuery()) {
	        
	        while (rs.next()) {
	            itens.add(mapRowToItemPedido(rs));
	        }
	        System.out.println("✓ " + itens.size() + " itens de pedidos recuperados");
	    } catch (Exception e) {
	        System.err.println("✗ Erro ao listar todos os itens de pedidos: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return itens;
	}
	
	/**
	 * Atualizar item do pedido
	 * @param itemPedido ItemPedido object with updated data
	 * @return true if successful, false otherwise
	 */
	public boolean update(ItemPedido itemPedido) {
	    String sql = "UPDATE itens_pedidos SET quantidade = ?, preco_unitario = ? WHERE id = ?";
	    
	    try (Connection con = ConnectionFactory.getConnection();
	         PreparedStatement pst = con.prepareStatement(sql)) {
	        
	        pst.setInt(1, itemPedido.getQuantidade());
	        pst.setDouble(2, itemPedido.getPrecoUnitario());
	        pst.setInt(3, itemPedido.getId());
	        
	        int rowsAffected = pst.executeUpdate();
	        
	        if (rowsAffected > 0) {
	            System.out.println("✓ Item do pedido atualizado com sucesso. ID: " + itemPedido.getId());
	            return true;
	        } else {
	            System.out.println("✗ Nenhum item encontrado com ID: " + itemPedido.getId());
	        }
	    } catch (Exception e) {
	        System.err.println("✗ Erro ao atualizar item do pedido: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return false;
	}
	
	/**
	 * Deletar item do pedido por ID
	 * @param id ItemPedido ID
	 * @return true if successful, false otherwise
	 */
	public boolean delete(int id) {
	    String sql = "DELETE FROM itens_pedidos WHERE id = ?";
	    
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
	        System.err.println("✗ Erro ao deletar item do pedido: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return false;
	}
	
	/**
	 * Método auxiliar para mapear uma linha do ResultSet para um objeto ItemPedido
	 */
	private ItemPedido mapRowToItemPedido(ResultSet rs) throws Exception {
	    ItemPedido ip = new ItemPedido();
	    ip.setId(rs.getInt("id"));
	    ip.setPedidoId(rs.getInt("pedido_id"));
	    ip.setItemCardapioId(rs.getInt("item_cardapio_id"));
	    ip.setQuantidade(rs.getInt("quantidade"));
	    ip.setPrecoUnitario(rs.getDouble("preco_unitario"));
	    return ip;
	}
}
